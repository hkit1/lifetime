package com.hkit.lifetime.survey;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
  Optional<Survey> findByLecture_Id(Integer id);
}