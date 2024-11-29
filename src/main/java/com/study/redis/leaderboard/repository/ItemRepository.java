package com.study.redis.leaderboard.repository;

import com.study.redis.leaderboard.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
