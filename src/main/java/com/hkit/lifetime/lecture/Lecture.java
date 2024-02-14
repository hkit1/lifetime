package com.hkit.lifetime.lecture;

import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue
    @Column(name = "lecture_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "closed_at", nullable = false)
    private LocalDate closedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private SubCategory category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    public Lecture(Integer id, String name, String description, LocalDate createdAt, LocalDate closedAt, SubCategory category, Company company) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.category = category;
        this.company = company;
    }


}