package com.common.redisconfig;



//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@Configuration
//@EnableCaching
//public class RedisConfig {
//
//  @Bean
//  @Primary
//  public ObjectMapper objectMapper() {
//      ObjectMapper objectMapper = new ObjectMapper();
//      objectMapper.registerModule(new JavaTimeModule());
//      return objectMapper;
//  }
//
//  @Bean
//  public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
//      return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
//  }
//
//  @Bean
//  @Primary
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
//          Jackson2JsonRedisSerializer<Object> jacksonSerializer) {
//      RedisTemplate<String, Object> template = new RedisTemplate<>();
//      template.setConnectionFactory(connectionFactory);
//
//      StringRedisSerializer stringSerializer = new StringRedisSerializer();
//
//      template.setKeySerializer(stringSerializer);
//      template.setValueSerializer(jacksonSerializer);
//      template.setHashKeySerializer(stringSerializer);
//      template.setHashValueSerializer(jacksonSerializer);
//
//      template.afterPropertiesSet();
//      return template;
//  }
//
//  @Bean
//  public RedisConnectionFactory redisConnectionFactory() {
//      RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//      configuration.setHostName("192.168.20.58");
//      configuration.setPort(6379);
//      configuration.setPassword(RedisPassword.of("Welcome@123"));
//      return new LettuceConnectionFactory(configuration);
//  }
//}




//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import java.time.Duration;
//
//@Configuration
//
//public class RedisConfig {
//
//  @Bean
//  @Primary
//  public ObjectMapper objectMapper() {
//      ObjectMapper objectMapper = new ObjectMapper();
//      objectMapper.registerModule(new JavaTimeModule());
//      // Configure for better serialization
//      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//      objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
//      objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//      return objectMapper;
//  }
//
//  @Bean
//  @Primary
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
//          ObjectMapper objectMapper) {
//      RedisTemplate<String, Object> template = new RedisTemplate<>();
//      template.setConnectionFactory(connectionFactory);
//
//      // Use GenericJackson2JsonRedisSerializer - handles type information automatically
//      GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
//
//      StringRedisSerializer stringSerializer = new StringRedisSerializer();
//
//      template.setKeySerializer(stringSerializer);
//      template.setValueSerializer(serializer);
//      template.setHashKeySerializer(stringSerializer);
//      template.setHashValueSerializer(serializer);
//
//      template.afterPropertiesSet();
//      return template;
//  }
//
//  @Bean
//  public RedisConnectionFactory redisConnectionFactory() {
//      RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//      configuration.setHostName("192.168.20.58");
//      configuration.setPort(6379);
//      configuration.setPassword(RedisPassword.of("Welcome@123"));
//      return new LettuceConnectionFactory(configuration);
//  }
//
//  /**
//   * Configure RedisCacheManager for @Cacheable annotations
//   * Uses GenericJackson2JsonRedisSerializer which automatically handles type information
//   */
//  @Bean
//  @Primary
//  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
//          ObjectMapper objectMapper) {
//      
//      // Use GenericJackson2JsonRedisSerializer - it handles type information automatically
//      GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
//      
//      // Define how the cache should look
//      RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//              .entryTtl(Duration.ofMinutes(2))
//              .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
//              .disableCachingNullValues();
//
//      return RedisCacheManager.builder(connectionFactory)
//              .cacheDefaults(cacheConfig)
//              .transactionAware()
//              .build();
//  }
//}

//File: com/common/redisconfig/RedisConfig.java


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

 @Bean
 public ObjectMapper redisObjectMapper() {
     ObjectMapper mapper = new ObjectMapper();
     mapper.registerModule(new JavaTimeModule());
     mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
     return mapper;
 }

 @Bean
 public LettuceConnectionFactory redisConnectionFactory() {
     RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
     config.setHostName("192.168.20.58");
     config.setPort(6379);
     config.setPassword(RedisPassword.of("Welcome@123"));
     return new LettuceConnectionFactory(config);
 }

 @Bean
 public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper redisObjectMapper) {
     RedisTemplate<String, Object> template = new RedisTemplate<>();
     template.setConnectionFactory(factory);
     GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper);
     template.setKeySerializer(new StringRedisSerializer());
     template.setValueSerializer(serializer);
     template.setHashKeySerializer(new StringRedisSerializer());
     template.setHashValueSerializer(serializer);
     template.afterPropertiesSet();
     return template;
 }

 // THIS IS THE ONLY BEAN THAT MATTERS FOR @Cacheable
 @Bean
 public RedisCacheConfiguration cacheConfiguration(ObjectMapper redisObjectMapper) {
     return RedisCacheConfiguration.defaultCacheConfig()
             .entryTtl(Duration.ofMinutes(2))   // adjust as needed
             .disableCachingNullValues()
             .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
             .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                     new GenericJackson2JsonRedisSerializer(redisObjectMapper)));
 }
 
}





