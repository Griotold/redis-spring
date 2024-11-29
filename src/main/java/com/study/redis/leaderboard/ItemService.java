package com.study.redis.leaderboard;

import com.study.redis.leaderboard.domain.Item;
import com.study.redis.leaderboard.domain.ItemOrder;
import com.study.redis.leaderboard.repository.ItemRepository;
import com.study.redis.leaderboard.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.redis.connection.RedisInvalidSubscriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public ItemService(
            ItemRepository itemRepository,
            OrderRepository orderRepository
    ) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    public void purchase(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepository.save(ItemOrder.builder()
                .item(item)
                .build());
    }
}
