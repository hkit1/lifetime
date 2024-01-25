package com.hkit.lifetime.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

}