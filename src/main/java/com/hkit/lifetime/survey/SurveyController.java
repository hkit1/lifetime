package com.hkit.lifetime.survey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/api/survey/{id}/create")
    public String createSurvey(@PathVariable(name = "id") Integer id,
                               SurveyDto surveyDto){
        surveyService.register(id, surveyDto);

        return "home";
    }

    @PostMapping("/api/survey/{id}/update")
    public String updateSurvey(@PathVariable(name = "id") Integer id,
                               SurveyDto surveyDto){
        surveyService.updateSurvey(id, surveyDto);
        return "home";
    }

}
