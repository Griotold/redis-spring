package com.study.redis.leaderboard;

import com.study.redis.leaderboard.domain.Item;
import com.study.redis.leaderboard.domain.ItemDto;
import com.study.redis.leaderboard.domain.ItemOrder;
import com.study.redis.leaderboard.repository.ItemRepository;
import com.study.redis.leaderboard.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ZSetOperations<String, ItemDto> rankOps;

    public ItemService(
            ItemRepository itemRepository,
            OrderRepository orderRepository,
            RedisTemplate<String, ItemDto> rankTemplate
    ) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.rankOps = rankTemplate.opsForZSet();
    }

    public void purchase(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepository.save(ItemOrder.builder()
                .item(item)
                .count(1)
                .build());
        // 없으면 추가해주고, 있으면 ++
        rankOps.incrementScore("soldRanks", ItemDto.from(item), 1);
    }

    public List<ItemDto> getMostSold() {
        // LinkedHashSet 리턴 - 10개 리턴
        Set<ItemDto> ranks = rankOps.reverseRange("soldRanks", 0, 9);
        if (ranks == null) return Collections.emptyList();
        return ranks.stream().toList();
    }
}
