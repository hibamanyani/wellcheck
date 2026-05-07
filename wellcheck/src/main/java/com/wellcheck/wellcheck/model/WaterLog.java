package com.wellcheck.wellcheck.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "water_logs")
public class WaterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int glasses;
    private int goalGlasses = 8;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}