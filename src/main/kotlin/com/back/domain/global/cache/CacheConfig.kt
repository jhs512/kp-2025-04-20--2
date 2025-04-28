package com.back.domain.global.cache

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        val objectMapper = ObjectMapper()

        // 타입 정보 항상 포함하도록 설정
        val ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Any::class.java)
            .build()

        objectMapper.activateDefaultTyping(
            ptv,
            ObjectMapper.DefaultTyping.EVERYTHING, // 🔥 모든 객체에 타입정보 붙이기
            JsonTypeInfo.As.PROPERTY // 🔥 "_type" 같은 속성으로 타입정보 붙임
        )

        val serializer = GenericJackson2JsonRedisSerializer(objectMapper)

        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(serializer)
            )
    }
}