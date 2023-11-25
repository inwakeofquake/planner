package com.planner.planner.entity;

import com.planner.planner.user.User;
import jakarta.persistence.*;

import javax.xml.stream.Location;
import java.time.LocalDateTime;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private User publisher;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    private LocalDateTime publishedOn;
    private LocalDateTime eventDate;
    private String title;
}
