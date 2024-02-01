package com.hkit.lifetime.exam;

import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exam")
public class Exam {
  @Id
  @GeneratedValue
  @Column(name = "exam_id", nullable = false, length = 36)
  private String examId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "lecture_id", nullable = false)
  private Lecture lecture;

  @Column(name = "created_at", nullable = false)
  private LocalDate createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDate updatedAt;

  @Lob
  @Column(name = "json", nullable = false)
  private String json;
}