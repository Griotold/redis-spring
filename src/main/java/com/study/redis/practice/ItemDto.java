package com.study.redis.practice;

import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto implements Serializable {
    private String name;
    private String description;
    private Integer price;
}
