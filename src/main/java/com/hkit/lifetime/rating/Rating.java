package com.hkit.lifetime.rating;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue
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

    public Rating(Lecture lecture, Account account, Integer star, String text) {
        this.lecture = lecture;
        this.account = account;
        this.star = star;
        this.text = text;
    }
}