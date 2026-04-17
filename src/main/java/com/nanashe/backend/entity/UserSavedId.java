package com.nanashe.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class UserSavedId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "alternative_id")
    private Integer alternativeId;
}
