package com.study.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void stringOpsTest() {
        // 지금 RedisTemplate에 설정된 타입을 바탕으로
        // Redis 문자열 조작을 할거다.
        ValueOperations<String, String> ops
                = stringRedisTemplate.opsForValue();

        // SET simplekey simplevalue
        // GET simplekey
        ops.set("simplekey", "simplevalue");
        System.out.println(ops.get("simplekey"));
    }

    @Test
    void setOpsTest() {
        SetOperations<String, String> ops
                = stringRedisTemplate.opsForSet();
        // SADD hobbies games
        ops.add("hobbies", "games");
        // SADD hobbies coding alcohol games
        ops.add("hobbies", "coding", "alcohol", "games");
        System.out.println(ops.size("hobbies"));

        // EXPIRE 메서드
        stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS);
        // DEL 메서드
        stringRedisTemplate.delete("hobbies");
    }

}
