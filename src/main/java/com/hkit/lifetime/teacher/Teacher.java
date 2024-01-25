package com.hkit.lifetime.teacher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id @GeneratedValue
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "tel")
    private String tel;

}