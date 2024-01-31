package com.hkit.lifetime.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureContentRepository extends JpaRepository<LectureContent, Long> {
    Optional<LectureContent> findByLecture_Id(Integer id);

    Optional<LectureContent> findByLecture_IdAndName(Integer id, String name);
}