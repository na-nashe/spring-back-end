package com.nanashe.backend.entity;

import com.nanashe.backend.entity.enums.PricingModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "alternatives")
@Getter
@Setter
public class Alternative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_id", nullable = false)
    private Country origin;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_model", columnDefinition = "pricing_model_enum")
    private PricingModel pricingModel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String url;

    @Column(name = "ai_generated")
    private Boolean aiGenerated;

    @ManyToMany(mappedBy = "alternatives")
    private List<Product> products;
}
