package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", columnDefinition = "bpchar", length = 2)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "is_friendly", nullable = false)
    private boolean isFriendly;
}
