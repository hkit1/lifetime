package com.hkit.lifetime.rating;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uuid", nullable = false)
    private Account account;

    @Column(name = "star", nullable = false)
    private Integer star;

    @Column(name = "text", nullable = false)
    private String text;
}