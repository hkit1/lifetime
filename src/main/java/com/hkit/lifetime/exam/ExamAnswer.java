package com.hkit.lifetime.exam;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exam_answer")
public class ExamAnswer {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exam_id", nullable = false)
  private Exam exam;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account", nullable = false)
  private Account account;

  @Lob
  @Column(name = "json", nullable = false)
  private String json;

  public ExamAnswer(Exam exam, Account account, String json) {
    this.exam = exam;
    this.account = account;
    this.json = json;
  }
}
