package com.hkit.lifetime.lecture;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

  Optional<Lecture> findByName(String name);
}