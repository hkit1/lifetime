package com.hkit.lifetime.survey;

import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "survey")
@NoArgsConstructor
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

    public Survey(Lecture lecture, String json) {
        this.lecture = lecture;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.json = json;
    }

    public void updateSurvey(SurveyDto surveyDto){
        this.updatedAt = LocalDate.now();
        this.json = surveyDto.json();
    }
}