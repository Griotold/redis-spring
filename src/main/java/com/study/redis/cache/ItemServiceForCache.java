package com.study.redis.cache;

import com.study.redis.cache.domain.ItemDtoForCache;
import com.study.redis.cache.domain.ItemForCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable(cacheNames = "itemAllCache", key = "methodName")
    public List<ItemDtoForCache> readAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDtoForCache::from)
                .toList();
    }

    /**
     * Write-Through 방식
     * readOne 메서드의 cacheNames와 동일하게 지정해준 것 주목
     */
    @CachePut(cacheNames = "itemCache", key = "#result.id")
    public ItemDtoForCache create(ItemDtoForCache dto) {
        log.info("Create Item: {}", dto);
        return ItemDtoForCache.from(itemRepository.save(ItemForCache.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build()));
    }

    /**
     * update 메서드가 실행되면, readAll의 캐시를 지워주고 싶다
     */
    @CachePut(cacheNames = "itemsCache", key = "args[0]")
    @CacheEvict(cacheNames = "itemAllCache", allEntries = true)
    public ItemDtoForCache update(Long id, ItemDtoForCache dto) {
        ItemForCache item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        return ItemDtoForCache.from(itemRepository.save(item));
    }

    /**
     * delete 메서드가 실행되면, readOne, readAll 의 캐시를 지워줘야 한다.
     * */
    @Caching(evict = {
            @CacheEvict(cacheNames = "itemCache", key = "#id"),
            @CacheEvict(cacheNames = "itemAllCache", allEntries = true)
    })
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
