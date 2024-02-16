package com.hkit.lifetime.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LectureContentRepository extends JpaRepository<LectureContent, Long> {
    List<LectureContent> findByLecture_Id(Integer id);

    List<LectureContent> findByLecture_IdAndName(Integer id, String name);

    Optional<LectureContent> findByLecture_IdAndContent(Integer lecture_id, Integer contentid);

    Optional<LectureContent> findTopByLecture_IdOrderByContentDesc(Integer id);


    @Transactional
    @Modifying
    @Query("update LectureContent l set l.url = ?1 where l.id = ?2 and l.lecture = ?3")
    void updateUrlByIdAndLecture(String url, Integer id, Lecture lecture);
}