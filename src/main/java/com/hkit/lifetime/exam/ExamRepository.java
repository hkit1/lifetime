package com.hkit.lifetime.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    Optional<Exam> findByLecture_IdAndExamId(Integer id, Integer examId);

    Optional<Exam> findByLecture_Id(Integer id);
}