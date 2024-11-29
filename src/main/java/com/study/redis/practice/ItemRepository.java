package com.study.redis.practice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("practiceRepository")
public interface ItemRepository extends CrudRepository<Item, String> {
}
