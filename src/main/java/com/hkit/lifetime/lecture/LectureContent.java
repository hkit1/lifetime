package com.hkit.lifetime.lecture;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "lecture_content")
public class LectureContent {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "url", nullable = false)
    private String url;

    public LectureContent(Integer id, Lecture lecture, String name, String description, String url) {
        this.id = id;
        this.lecture = lecture;
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public void UpdateLectureContent(LectureContentDto lectureContentDto) {
        if (!lectureContentDto.name().isEmpty()){
            this.name = lectureContentDto.name();
        }
        if (!lectureContentDto.description().isEmpty()){
            this.description = lectureContentDto.description();
        }
        if (lectureContentDto.url() != null &&!lectureContentDto.url().isEmpty()){
            this.url = lectureContentDto.url();
        }



    }
}