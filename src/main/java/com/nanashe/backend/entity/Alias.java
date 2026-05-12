package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aliases")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String name;

    public static Alias fromString(String name) {
        return Alias.builder().name(name).build();
    }
}
