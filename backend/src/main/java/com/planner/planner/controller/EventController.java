package com.planner.planner.controller;

import com.planner.planner.entity.Event;
import com.planner.planner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @GetMapping("/current-week")
    public ResponseEntity<List<Event>> getEventsForCurrentWeek() {
        return ResponseEntity.ok(eventService.getEventsForCurrentWeek());
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadEvents(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws IOException {

        byte[] pdfContent = eventService.getEventsPdf(startDate, endDate);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=\"events.pdf\"")
                .body(pdfContent);
    }
}
