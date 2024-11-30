package com.study.redis.cache;

import com.study.redis.cache.domain.ItemDtoForCache;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemControllerForCache {
    private final ItemServiceForCache itemService;

    @PostMapping
    public ItemDtoForCache create(
            @RequestBody
            ItemDtoForCache itemDtoForCache
    ) {
        return itemService.create(itemDtoForCache);
    }

    @GetMapping
    public List<ItemDtoForCache> readAll() {
        return itemService.readAll();
    }

    @GetMapping("/{id}")
    public ItemDtoForCache readOne(@PathVariable("id") Long id) {
        return itemService.readOne(id);
    }

    @PutMapping("{id}")
    public ItemDtoForCache update(
            @PathVariable("id")
            Long id,
            @RequestBody
            ItemDtoForCache itemDtoForCache
    ) {
        return itemService.update(id, itemDtoForCache);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        itemService.delete(id);
    }

}
