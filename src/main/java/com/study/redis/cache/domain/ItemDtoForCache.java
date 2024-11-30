package com.study.redis.cache.domain;

import lombok.*;

import javax.swing.*;
import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoForCache implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer price;

    public static ItemDtoForCache from(ItemForCache item) {
        return ItemDtoForCache.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }
}
