package com.hkit.lifetime.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Integer> {
    Optional<ExamAnswer> findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(Integer id, Integer examId, String id1);

    Optional<ExamAnswer> findByExam_Lecture_IdAndExam_ExamId(Integer id, Integer examId);

    Optional<List<ExamAnswer>> findAllByExam_ExamId(Integer examId);

    @Modifying
    @Query("delete from ExamAnswer ea where ea.id in :ids")
    void deleteAllByIds(@Param("ids") List<Integer> ids);
}
