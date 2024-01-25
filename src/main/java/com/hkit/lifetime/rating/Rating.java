package com.hkit.lifetime.rating;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lecture_id", nullable = false)
    private Integer lectureId;

    @Column(name = "uuid", length = 36)
    private String uuid;

}