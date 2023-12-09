package com.planner.planner.repository;

import com.planner.planner.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {
    // You can define custom query methods if needed
    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
}
