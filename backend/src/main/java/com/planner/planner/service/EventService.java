package com.planner.planner.service;

import com.planner.planner.entity.Event;
import com.planner.planner.enums.Location;
import com.planner.planner.exception.ResourceNotFoundException;
import com.planner.planner.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        logger.info("Fetching all events");
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String id) {
        logger.info("Fetching event with id: {}", id);
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        logger.info("Creating new event: {}", event);
        event.setPublishedOn(LocalDateTime.now());
        Event savedEvent = eventRepository.save(event);
        logger.info("Created new event with id: {}", savedEvent.getId());
        return savedEvent;
    }

    public Event updateEvent(String id, Event eventDetails) {
        logger.info("Updating event with id: {}", id);
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    existingEvent.setTitle(eventDetails.getTitle());
                    existingEvent.setDescription(eventDetails.getDescription());
                    existingEvent.setEventDate(eventDetails.getEventDate());
                    existingEvent.setSpeaker(eventDetails.getSpeaker());
                    existingEvent.setSignificance(eventDetails.getSignificance());
                    existingEvent.setCoverage(eventDetails.getCoverage());
                    existingEvent.setLocation(eventDetails.getLocation());
                    Event updatedEvent = eventRepository.save(existingEvent);
                    logger.info("Updated event with id: {}", updatedEvent.getId());
                    return updatedEvent;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    public void deleteEvent(String id) {
        logger.info("Deleting event with id: {}", id);
        eventRepository.deleteById(id);
        logger.info("Deleted event with id: {}", id);
    }

    public List<Event> getEventsBetweenDates(LocalDateTime start, LocalDateTime end) {
        logger.info("Fetching events between {} and {}", start, end);
        return eventRepository.findByEventDateBetween(start, end);
    }

    public List<Event> getEventsByPublisher(String publisherId) {
        logger.info("Fetching events for publisher: {}", publisherId);
        return eventRepository.findByPublisherId(publisherId);
    }

    public List<Event> getEventsByLocation(Location location) {
        logger.info("Fetching events for location: {}", location);
        return eventRepository.findByLocation(location);
    }

    public List<Event> getUpcomingEvents() {
        logger.info("Fetching upcoming events");
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findByEventDateAfterOrderByEventDateAsc(now);
    }

    public List<Event> getPastEvents() {
        logger.info("Fetching past events");
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findByEventDateBeforeOrderByEventDateDesc(now);
    }
}