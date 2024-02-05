package com.hkit.lifetime.exam;

import com.hkit.lifetime.exam.dto.ExamDto;
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
  @Column(name = "id", nullable = false)
  private Integer examId;

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

  public Exam(Lecture lecture, String json) {
    this.lecture = lecture;
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
    this.json = json;
  }

  public void updateExam(ExamDto examDto){
    this.updatedAt = LocalDate.now();
    this.json = examDto.json();
  }

}