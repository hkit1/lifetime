package com.hkit.lifetime.lecture;

import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.exam.Exam;
import com.hkit.lifetime.rating.Rating;
import com.hkit.lifetime.survey.Survey;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue
    @Column(name = "lecture_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

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

    @OneToMany(mappedBy = "lecture")
    private Set<Exam> exams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lecture_id")
    private Set<LectureContent> lectureContents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lecture")
    private Set<LectureList> lectureLists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lecture")
    private Set<Rating> ratings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lecture")
    private Set<Survey> surveys = new LinkedHashSet<>();
}