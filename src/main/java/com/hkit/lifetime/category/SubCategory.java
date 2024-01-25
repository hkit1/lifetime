package com.hkit.lifetime.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sub_category")
public class SubCategory {
    @Id
    @Column(name = "category", nullable = false)
    private Integer id;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Lob
    @Column(name = "name")
    private String name;

}