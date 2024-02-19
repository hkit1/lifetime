package com.hkit.lifetime.survey;

import com.hkit.lifetime.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "survey_answer")
@NoArgsConstructor
public class SurveyAnswer {
  @Id
  @GeneratedValue
  @Column(name = "content_id", nullable = false)
  private Integer content_id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id", nullable = false)
  private Survey id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "uuid", nullable = false)
  private Account uuid;

  @Column(name = "json", nullable = false)
  private String json;

  public SurveyAnswer(Survey id, Account uuid, String json) {
    this.id = id;
    this.uuid = uuid;
    this.json = json;
  }

}
