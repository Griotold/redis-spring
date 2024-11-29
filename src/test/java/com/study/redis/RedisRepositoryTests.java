package com.study.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void createTest() {
        // 객체를 만들고
        Item item = Item.builder()
                .name("keyboard")
                .description("Very Expensive Keyboard")
                .price(100_000)
                .build();
        // save를 호출한다.
        itemRepository.save(item);
    }

    @Test
    void redOneTest() {
        Item item = itemRepository.findById("")
                .orElseThrow();

        System.out.println(item.getDescription());
    }

    @Test
    void updateTest() {
        Item item = itemRepository.findById("")
                .orElseThrow();
        item.setDescription("On Sale!!");
        item = itemRepository.save(item);
        System.out.println(item.getDescription());
    }
}
