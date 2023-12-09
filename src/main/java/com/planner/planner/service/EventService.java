package com.planner.planner.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.planner.planner.entity.Event;
import com.planner.planner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        // Ensure the event exists and update it
        return eventRepository.findById(id)
                .map(event -> {
                    event.setTitle(updatedEvent.getTitle());
                    event.setDescription(updatedEvent.getDescription());
                    event.setEventDate(updatedEvent.getEventDate());
                    // set other fields as necessary
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }

    public List<Event> getEventsForCurrentWeek() {
        LocalDateTime startOfWeek = getStartOfWeek();
        LocalDateTime endOfWeek = getEndOfWeek();
        return eventRepository.findByEventDateBetween(startOfWeek, endOfWeek);
    }

    private LocalDateTime getStartOfWeek() {
        LocalDate today = LocalDate.now();
        return today.with(DayOfWeek.MONDAY).atStartOfDay();
    }

    private LocalDateTime getEndOfWeek() {
        LocalDate today = LocalDate.now();
        return today.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
    }

    public byte[] getEventsPdf(LocalDate startDate, LocalDate endDate) throws IOException {
        List<Event> events = eventRepository.findByEventDateBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return generatePdfFromEvents(events);
    }

    private byte[] generatePdfFromEvents(List<Event> events) throws IOException {
        Document document = new Document();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (Event event : events) {
                document.add(new Paragraph("Event Title: " + event.getTitle()));
                document.add(new Paragraph("Description: " + event.getDescription()));
                document.add(new Paragraph("Date: " + event.getEventDate()));
                document.add(new Paragraph("\n")); // Adds a space between events
            }

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Error in PDF generation", e);
        }
    }
}
