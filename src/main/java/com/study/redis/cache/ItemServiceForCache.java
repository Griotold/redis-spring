package com.study.redis.cache;

import com.study.redis.cache.domain.ItemDtoForCache;
import com.study.redis.cache.domain.ItemForCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 이 메서드의 결과는 캐싱이 가능하다.
     * cacheNames: 적용할 캐시 규칙을 지정하기 위한 이름
     * key = "args[0]" : 0번째 파라미터를 key(캐시 데이터를 구분하기 위해 활용하는 값)로 쓰겠다.
     * Cache-Aside 방식
     */
    @Cacheable(cacheNames = "itemCache", key = "args[0]")
    public ItemDtoForCache readOne(Long id) {
        log.info("Read One: {}", id);
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
