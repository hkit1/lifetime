package com.hkit.lifetime.lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lecture_list")
public class LectureList {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uuid", length = 36)
    private String uuid;

    @Column(name = "lecture_id", nullable = false)
    private Integer lectureId;

}