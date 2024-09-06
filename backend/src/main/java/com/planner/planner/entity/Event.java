package com.planner.planner.entity;

import com.planner.planner.enums.Coverage;
import com.planner.planner.enums.Location;
import com.planner.planner.enums.Significance;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {

    @Id
    private String id;

    @NotBlank(message = "Publisher ID is required")
    private String publisherId;

    @NotNull(message = "Location is required")
    private Location location;

    @CreatedDate
    private LocalDateTime publishedOn;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Size(max = 100, message = "Speaker name cannot exceed 100 characters")
    private String speaker;

    @NotNull(message = "Significance is required")
    private Significance significance;

    @NotNull(message = "Coverage is required")
    private Coverage coverage;
}