package com.hkit.lifetime.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "uuid", length = 36)
    private String uuid;

}