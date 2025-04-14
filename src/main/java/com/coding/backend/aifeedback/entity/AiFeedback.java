package com.coding.backend.aifeedback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedback;

    private Integer accuracy;
    private Integer efficiency;
    private Integer readability;
}