package com.planner.planner.controller;

import com.planner.planner.entity.Event;
import com.planner.planner.enums.Location;
import com.planner.planner.exception.ResourceNotFoundException;
import com.planner.planner.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event) {
        logger.info("Attempting to create event: {}", event);
        try {
            Event createdEvent = eventService.createEvent(event);
            logger.info("Event created successfully: {}", createdEvent);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating event", e);
            return new ResponseEntity<>("Error creating event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @Valid @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/between")
    public List<Event> getEventsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return eventService.getEventsBetweenDates(start, end);
    }

    @GetMapping("/publisher/{publisherId}")
    public List<Event> getEventsByPublisher(@PathVariable String publisherId) {
        return eventService.getEventsByPublisher(publisherId);
    }

    @GetMapping("/location/{location}")
    public List<Event> getEventsByLocation(@PathVariable Location location) {
        return eventService.getEventsByLocation(location);
    }
}