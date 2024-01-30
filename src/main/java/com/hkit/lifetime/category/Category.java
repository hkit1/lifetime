package com.hkit.lifetime.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Category(String name) {
        this.name = name;
    }

}