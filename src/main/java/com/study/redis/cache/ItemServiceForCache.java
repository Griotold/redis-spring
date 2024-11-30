package com.study.redis.cache;

import com.study.redis.cache.domain.ItemDtoForCache;
import com.study.redis.cache.domain.ItemForCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ItemServiceForCache {

    private final ItemRepositoryForCache itemRepository;

    public ItemServiceForCache(ItemRepositoryForCache itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDtoForCache create(ItemDtoForCache dto) {
        return ItemDtoForCache.from(itemRepository.save(ItemForCache.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .price(dto.getPrice())
                .build()));
    }

    public List<ItemDtoForCache> readAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDtoForCache::from)
                .toList();
    }

    public ItemDtoForCache readOne(Long id) {
        return itemRepository.findById(id)
                .map(ItemDtoForCache::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ItemDtoForCache update(Long id, ItemDtoForCache dto) {
        ItemForCache item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        return ItemDtoForCache.from(itemRepository.save(item));
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
