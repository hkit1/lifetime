package com.hkit.lifetime.survey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SurveyAnswerController {

    private final SurveyAnswerService surveyAnswerService;

    @PostMapping("/api/survey/{id}/answer")
    public String createAnswer(@PathVariable(name = "id") Integer lectureId,
                               @RequestParam(name = "accountId") String accountId,
                               SurveyAnswerDto surveyAnswerDto){

        log.info(surveyAnswerDto.json());
        surveyAnswerService.register(lectureId, accountId, surveyAnswerDto);
        return "home";
    }

}
