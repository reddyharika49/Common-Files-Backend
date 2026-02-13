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

    // Changed to 3 minutes as requested
    private static final Duration STABLE_EXPIRATION = Duration.ofMinutes(3);

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PermissionOutputDto {
        @JsonProperty("screen_name")
        private String screenName;

        @JsonProperty("permission_name")
        private String permissionName;
    }

    public Map<String, Object> getPermissions(String jwt) {
        List<String> roleNames = jwtService.extractRoles(jwt);
        String userName = jwtService.extractUsername(jwt);
        List<String> designations = jwtService.extractDesignations(jwt);

        log.info(">>> Permission Check Initiated <<<");
        log.info("User: {}, Designations: {}, Roles: {}", userName, designations, roleNames);

        if (designations == null || designations.isEmpty()) {
            log.error("User has no designations in JWT");
            throw new IllegalStateException("User has no designations in JWT");
        }

        String designation = designations.get(0);
        return getPermissionsForRoles(roleNames, userName, designation);
    }

    private Map<String, Object> getPermissionsForRoles(List<String> roleNames, String userName, String designation) {
        if (roleNames == null || roleNames.isEmpty())
            return Collections.emptyMap();

        Map<String, Object> finalPermissions = new HashMap<>();
        List<String> redisKeys = roleNames.stream()
                .map(name -> "role_v2::" + name)
                .collect(Collectors.toList());

        // 1. ATTEMPT CACHE RETRIEVAL
        List<Object> cachedResults = redisTemplate.opsForValue().multiGet(redisKeys);
        List<String> missedRoleNames = new ArrayList<>();

        for (int i = 0; i < roleNames.size(); i++) {
            String roleName = roleNames.get(i);
            Object cachedData = (cachedResults != null) ? cachedResults.get(i) : null;

            if (cachedData != null) {
                log.info("CACHE HIT for role: {}", roleName);
                // log.info("Redis Data for role {}: {}", roleName, cachedData);
                finalPermissions.put(roleName, cachedData);
                // Sliding expiration
                redisTemplate.expire("role_v2::" + roleName, STABLE_EXPIRATION);
                log.info("Refreshed expiration for role: {} to 3 mins", roleName);
            } else {
                log.info("CACHE MISS for role: {}", roleName);
                missedRoleNames.add(roleName);
            }
        }

        // 2. HANDLE CACHE MISSES (DB FETCH)
        if (!missedRoleNames.isEmpty()) {
            log.info("Fetching from DB for roles: {}", missedRoleNames);
            List<EmployeeView> dbResults = employeeViewRepository.findByUserDesignationAndRoles(userName, designation,
                    missedRoleNames);
            log.info("DB returned {} records", dbResults.size());

            // SYNC STEP: Aggregate permissions by Role Name first to avoid overwriting
            // Use Map<ScreenName, Dto> to DEDUPLICATE screens
            Map<String, Map<String, PermissionOutputDto>> aggregatedPermissions = new HashMap<>();

            dbResults.forEach(view -> {
                String roleName = view.getRoleName();
                try {
                    String rawJson = view.getScreenPermission();
                    if (rawJson != null) {
                        log.info("Processed DB Data chunk for role: {}, UserRoleId: {}, JSON Length: {}", roleName,
                                view.getId().getUserRoleId(), rawJson.length());
                    }

                    List<FullPermissionDto> permissionsWithUrl = objectMapper.readValue(
                            rawJson,
                            new TypeReference<List<FullPermissionDto>>() {
                            });

                    // TRANSFORM & SANITIZE
                    List<PermissionOutputDto> cleanOutput = permissionsWithUrl.stream()
                            .filter(p -> p.getScreenName() != null)
                            .map(p -> new PermissionOutputDto(
                                    p.getScreenName().trim(),
                                    p.getPermissionName()))
                            .collect(Collectors.toList());

                    // Merge into Map to Remove Duplicates
                    aggregatedPermissions.computeIfAbsent(roleName, k -> new HashMap<>());
                    Map<String, PermissionOutputDto> roleScreens = aggregatedPermissions.get(roleName);

                    cleanOutput.forEach(dto -> {
                        roleScreens.put(dto.getScreenName(), dto);
                    });

                } catch (JsonProcessingException e) {
                    log.error("Failed to parse DB JSON for role: {}", roleName, e);
                }
            });

            // Add aggregated results to final map (Convert back to List)
            aggregatedPermissions.forEach((role, screenMap) -> {
                List<PermissionOutputDto> uniqueSortedList = new ArrayList<>(screenMap.values());
                // Sort for consistent debugging
                uniqueSortedList.sort((p1, p2) -> p1.getScreenName().compareTo(p2.getScreenName()));

                finalPermissions.put(role, uniqueSortedList);
            });

            // ASYNC PIPELINE: Background update to Redis with AGGREGATED data
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @SuppressWarnings("unchecked")
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    aggregatedPermissions.forEach((roleName, screenMap) -> {
                        String redisKey = "role_v2::" + roleName;
                        List<PermissionOutputDto> uniqueList = new ArrayList<>(screenMap.values());
                        // Sort for consistent debugging
                        uniqueList.sort((p1, p2) -> p1.getScreenName().compareTo(p2.getScreenName()));

                        log.info("CACHE POPULATING (Pipeline) for role: {}, Total Unique Screens: {}", roleName,
                                uniqueList.size());
                        operations.opsForValue().set(
                                (K) redisKey,
                                (V) uniqueList,
                                STABLE_EXPIRATION);
                        log.info("Stored in Redis: {} with 3 min expiration", redisKey);
                    });
                    return null;
                }
            });
        }

        // 3. RETURN FULL DATA
        // Because we populated the map BEFORE the pipeline, this will never be
        // "half-loaded".
        return finalPermissions;
    }
}