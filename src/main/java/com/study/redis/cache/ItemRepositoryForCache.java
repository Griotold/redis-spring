package com.study.redis.cache;

import com.study.redis.cache.domain.ItemForCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryForCache extends JpaRepository<ItemForCache, Long> {

    Page<ItemForCache> findAllByNameContains(String name, Pageable pageable);
}
