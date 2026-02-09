package com.common.service;
 
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.common.config.JwtService;
import com.common.entity.EmployeeView;
import com.common.repository.EmployeeViewRepository;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionDataService {
 
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmployeeViewRepository employeeViewRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
 
    private static final Duration DEFAULT_EXPIRATION = Duration.ofMinutes(3);
 
    // DTO #1: This matches the full data from the database JSON (including 'url')
    @Data
    @NoArgsConstructor
    private static class FullPermissionDto {
        @JsonProperty("screen_name")
        private String screenName;
 
        @JsonProperty("permission_name")
        private String permissionName;
 
        @JsonProperty("url")
        private String url;
    }
    
    // DTO #2: This represents the final, clean output we want to return (WITHOUT 'url')
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PermissionOutputDto {
        @JsonProperty("screen_name")
        private String screenName;
 
        @JsonProperty("permission_name")
        private String permissionName;
    }
 
    // --- getPermissions method is unchanged ---
    public Map<String, Object> getPermissions(String jwt) {
        List<String> roleNames = jwtService.extractRoles(jwt);
        String userName = jwtService.extractUsername(jwt);
        List<String> designations = jwtService.extractDesignations(jwt);
        if (designations == null || designations.isEmpty()) {
            throw new IllegalStateException("User has no designations in JWT");
        }
        String designation = designations.get(0);
        return getPermissionsForRoles(roleNames, userName, designation);
    }
 
    private Map<String, Object> getPermissionsForRoles(List<String> roleNames, String userName, String designation) {
        if (roleNames == null || roleNames.isEmpty()) return Collections.emptyMap();
        log.info("Fetching permissions for user: {} with roles: {}", userName, roleNames);
 
        Map<String, Object> finalPermissions = new HashMap<>();
        List<String> redisKeys = roleNames.stream().map(name -> "role::" + name).collect(Collectors.toList());
        List<Object> cachedResults = redisTemplate.opsForValue().multiGet(redisKeys);
        List<String> missedRoleNames = new ArrayList<>();
        
        for (int i = 0; i < roleNames.size(); i++) {
            String roleName = roleNames.get(i);
            Object cachedData = (cachedResults != null) ? cachedResults.get(i) : null;
            
            if (cachedData != null) {
                log.info("CACHE HIT for role: {}", roleName);
                finalPermissions.put(roleName, cachedData);
                redisTemplate.expire("role::" + roleName, DEFAULT_EXPIRATION);
            } else {
                log.info("CACHE MISS for role: {}", roleName);
                missedRoleNames.add(roleName);
            }
        }
        
        if (!missedRoleNames.isEmpty()) {
            List<EmployeeView> dbResults = employeeViewRepository.findByUserDesignationAndRoles(userName, designation, missedRoleNames);
 
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @SuppressWarnings("unchecked")
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    
                    dbResults.forEach(view -> {
                        String roleName = view.getRoleName();
                        String jsonPermissions = view.getScreenPermission();
                        String redisKey = "role::" + roleName;
 
                        try {
                            // 1. Parse the full JSON from the DB into the DTO that includes the 'url'.
                            List<FullPermissionDto> permissionsWithUrl = objectMapper.readValue(jsonPermissions,
                                new TypeReference<List<FullPermissionDto>>() {});
 
                            // 2. Transform the list, creating a NEW list of DTOs that excludes the 'url'.
                            List<PermissionOutputDto> permissionsWithoutUrl = permissionsWithUrl.stream()
                                .map(p -> new PermissionOutputDto(p.getScreenName(), p.getPermissionName()))
                                .collect(Collectors.toList());
 
                            // 3. Store and return the new, clean list without the 'url'.
                            finalPermissions.put(roleName, permissionsWithoutUrl);
                            operations.opsForValue().set((K) redisKey, (V) permissionsWithoutUrl, DEFAULT_EXPIRATION);
                            log.info("CACHE POPULATING for role: {}", roleName);
 
                        } catch (JsonProcessingException e) {
                            log.error("Failed to parse screen_permission JSON for role: {}", roleName, e);
                        }
                    });
                    return null;
                }
            });
        }
        return finalPermissions;
    }
}
 
 