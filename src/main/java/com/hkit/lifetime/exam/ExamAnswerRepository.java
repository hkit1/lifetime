package com.hkit.lifetime.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, String> {
    Optional<ExamAnswer> findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(Integer id, String examId, String id1);

    Optional<ExamAnswer> findByExam_Lecture_IdAndExam_ExamId(Integer id, String examId);
}
