package com.planner.planner.entity;

import com.planner.planner.enums.UserOrigin;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private UserOrigin origin;
}