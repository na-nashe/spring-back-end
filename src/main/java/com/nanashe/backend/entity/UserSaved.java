package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_saved")
@Getter
@Setter
public class UserSaved {

    @EmbeddedId
    private UserSavedId id;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @MapsId("alternativeId")
    @JoinColumn(name = "alternative_id")
    private Alternative alternative;

    @Column(name = "saved_at")
    private OffsetDateTime savedAt;
}
