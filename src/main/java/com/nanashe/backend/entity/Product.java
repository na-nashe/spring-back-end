package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 200)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_id", nullable = false)
    private Country origin;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Alias> aliases;

    @ManyToMany
    @JoinTable(
            name = "product_alternatives",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "alternative_id")
    )
    private List<Alternative> alternatives;
}
