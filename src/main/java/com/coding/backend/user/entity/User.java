package com.coding.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String pw;

    @Column(name = "preminum_status", nullable = false)
    private Boolean premiumStatus;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "solved_count")
    private Integer solvedCount;

    private Long rating;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "background_image")
    private String backgroundImage;

    @Enumerated(EnumType.STRING)
    private Role role = Role.user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String updatedBy;

    public enum Role {
        user, admin
    }
}
