package com.study.redis.leaderboard;

import com.study.redis.leaderboard.domain.ItemDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisLeaderBoardConfig {

    @Bean
    public RedisTemplate<String, ItemDto> rankTemplate(
            RedisConnectionFactory redisConnectionFactory
    ) {
        RedisTemplate<String, ItemDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }
}
