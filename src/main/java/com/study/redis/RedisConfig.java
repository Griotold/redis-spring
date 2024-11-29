package com.study.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    // Key는 자바의 문자열, Value는 ItemDto
    @Bean
    public RedisTemplate<String, ItemDto> itemRedisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, ItemDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Key는 어떻게 직렬화, 역직렬화 할 것인지
        template.setKeySerializer(RedisSerializer.string());
        // Value는 어떻게 직렬화, 역직렬화 할 것인지
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }
}
