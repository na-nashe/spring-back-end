package com.nanashe.backend.entity;

import com.nanashe.backend.dto.auth.request.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Column(nullable = false)
    private OffsetDateTime joined;

    public User(SignupRequestDto dto, String passwordHash) {
        this.username = dto.username();
        this.email = dto.email();
        this.passwordHash = passwordHash;
        this.joined = OffsetDateTime.now();
    }

    @OneToMany(mappedBy = "user")
    private List<UserSaved> savedAlternatives;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
