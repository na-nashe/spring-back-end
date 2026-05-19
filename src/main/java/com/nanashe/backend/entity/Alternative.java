package com.nanashe.backend.entity;

import com.nanashe.backend.entity.enums.PricingModel;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alternatives")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alternative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_id", nullable = false)
    private Country origin;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "pricing_model", columnDefinition = "pricing_model_enum")
    private PricingModel pricingModel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String url;

    @Column(name = "ai_generated")
    private Boolean aiGenerated;
}
