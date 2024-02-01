package com.hkit.lifetime.survey;

import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "json", nullable = false)
    private String json;
}