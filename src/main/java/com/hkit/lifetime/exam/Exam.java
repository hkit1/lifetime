package com.hkit.lifetime.exam;

import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

}