package com.hkit.lifetime.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
  @Id
  @Column(name = "attendanceId", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "uuid")
  private Account uuid;

  // TODO [JPA Buddy] generate columns from DB
}
