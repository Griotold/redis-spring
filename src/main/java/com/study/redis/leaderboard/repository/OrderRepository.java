package com.study.redis.leaderboard.repository;

import com.study.redis.leaderboard.domain.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ItemOrder, Long> {
}
