package com.nanashe.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", nullable = false)
    private List<Alias> aliases;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_alternatives",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "alternative_id")
    )
    private List<Alternative> alternatives;

    public boolean hasAlternatives() {
        return alternatives != null && !alternatives.isEmpty();
    }

    public void addAlternatives(List<Alternative> alternatives) {
        if (this.alternatives == null) {
            this.alternatives = new ArrayList<>();
        }
        this.alternatives.addAll(alternatives);
    }
}
