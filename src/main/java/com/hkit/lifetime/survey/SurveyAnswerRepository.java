package com.hkit.lifetime.survey;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Integer> {

  Optional<SurveyAnswer> findById_Lecture_Id(Integer id);
}
