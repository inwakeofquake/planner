package com.planner.planner.repository;

import com.planner.planner.entity.Event;
import com.planner.planner.enums.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByPublisherId(String publisherId);
    List<Event> findByLocation(Location location);
    List<Event> findByEventDateAfterOrderByEventDateAsc(LocalDateTime date);
    List<Event> findByEventDateBeforeOrderByEventDateDesc(LocalDateTime date);
}