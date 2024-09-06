package com.planner.planner.entity;

import com.planner.planner.enums.Coverage;
import com.planner.planner.enums.Significance;
import jakarta.persistence.*;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.stream.Location;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @NotNull
    private User publisher;

    @NotNull
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private LocalDateTime publishedOn;

    @Past
    private LocalDateTime eventDate;

    @NotNull
    @Size(min = 100, max = 255)
    private String title;

    @NotNull
    @Size(min = 255, max = 2500)
    private String description;

    private String speaker;

    @Enumerated(EnumType.STRING)
    private Significance significance;

    @Enumerated(EnumType.STRING)
    private Coverage coverage;
}
