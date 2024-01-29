package com.hkit.lifetime.lecture;

import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "lecture_id", nullable = false)
    private Integer lectureId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "closed_at")
    private LocalDate closedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_name", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private SubCategory category;

}