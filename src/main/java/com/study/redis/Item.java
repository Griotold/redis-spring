package com.study.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Entity 대신 RedisHash
@RedisHash("item") // "item" 이라는 key
public class Item implements Serializable {

    @Id
    // Id String 으로 하면 UUID 자동으로 배정된다.
    private String id;
    private String name;
    private String description;
    private Integer price;
}
