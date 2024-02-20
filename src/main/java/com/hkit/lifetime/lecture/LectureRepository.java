package com.hkit.lifetime.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

  Optional<Lecture> findByName(String name);

    long countByCreatedAtAfter(LocalDate createdAt);
}