package com.hkit.lifetime.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sub_category")
@NoArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue
    @Column(name = "category")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category mainCategory;

    @Column(name = "name")
    private String name;

    public SubCategory(Category mainCategory, String name) {
        this.mainCategory = mainCategory;
        this.name = name;
    }
}