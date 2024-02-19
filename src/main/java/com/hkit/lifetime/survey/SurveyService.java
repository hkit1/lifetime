package com.hkit.lifetime.survey;

import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final LectureRepository lectureRepository;

    public void register(Integer id, SurveyDto surveyDto) {

        Lecture findLecture = lectureRepository.findById(id)
                                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lecture Not Found"));

        Survey survey = new Survey(findLecture, surveyDto.json());
        surveyRepository.save(survey);
    }

    public void updateSurvey(Integer id, SurveyDto surveyDto) {
        Survey survey = surveyRepository.findByLecture_Id(id)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Survey Not Found"));

        survey.updateSurvey(surveyDto);
        surveyRepository.save(survey);
    }
}
