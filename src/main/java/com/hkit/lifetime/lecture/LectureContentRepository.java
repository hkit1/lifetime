package com.hkit.lifetime.lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureContentRepository extends JpaRepository<LectureContent, Long> {
    List<LectureContent> findByLecture_Id(Integer id);

    List<LectureContent> findByLecture_IdAndName(Integer id, String name);

    Optional<LectureContent> findByLecture_IdAndId(Integer id, Integer id1);
}