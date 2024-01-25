package com.hkit.lifetime.lecture;

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
    @Column(name = "lecture_id", nullable = false)
    private Integer lectureId;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "closed_at")
    private LocalDate closedAt;

    @Lob
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Lob
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "category", nullable = false)
    private Integer category;

}