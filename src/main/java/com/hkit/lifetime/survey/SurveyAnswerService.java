package com.hkit.lifetime.survey;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;
    private final AccountRepository accountRepository;

    public void register(Integer lectureId, String accountId, SurveyAnswerDto surveyAnswerDto) {
        Survey survey = surveyRepository.findByLecture_Id(lectureId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Survey Not Found"));

        Account account = accountRepository.findAccountById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));

        SurveyAnswer surveyAnswer = new SurveyAnswer(survey, account, surveyAnswerDto.json());
        surveyAnswerRepository.save(surveyAnswer);
    }
}
