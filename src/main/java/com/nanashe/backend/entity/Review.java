package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alternative_id", nullable = false)
    private Alternative alternative;

    @Column(nullable = false)
    private Short rating;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "pros", columnDefinition = "text[]")
    private List<String> pros;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "cons", columnDefinition = "text[]")
    private List<String> cons;

    private Boolean helpful;

    @Column(name = "timestamp", nullable = false)
    private OffsetDateTime timestamp;
}
