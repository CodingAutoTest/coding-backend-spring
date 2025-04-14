package com.coding.backend.userstreak.entity;

import com.coding.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_streak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "current_streak", nullable = false)
    private Integer currentStreak;

    @Column(name = "max_streak", nullable = false)
    private Integer maxStreak;

    @Column(name = "last_solved_date", nullable = false)
    private LocalDate lastSolvedDate;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
