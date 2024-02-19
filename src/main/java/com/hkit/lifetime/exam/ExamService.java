package com.hkit.lifetime.exam;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.exam.dto.ExamAnswerReqDto;
import com.hkit.lifetime.exam.dto.ExamDto;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamAnswerRepository examAnswerRepository;
    private final LectureRepository lectureRepository;
    private final AccountRepository accountRepository;

    /*
        Exam Service
     */
    public void registerExam(Integer id, ExamDto examDto) {

        Lecture findLecture = lectureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lecture Not Found"));

        Exam exam = new Exam(findLecture, examDto.json());
        examRepository.save(exam);
    }

    public void updateExam(Integer id, ExamDto examDto) {

        Lecture findLecture = lectureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lecture Not Found"));

        Exam findExam = examRepository.findByLecture_Id(findLecture.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam Not Found"));

        findExam.updateExam(examDto);
        examRepository.save(findExam);
    }

    public void deleteExam(Integer lectureId, Integer examId) {

        Exam exam = examRepository.findByLecture_IdAndExamId(lectureId, examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam Not Found"));

        List<ExamAnswer> examAnswers = examAnswerRepository.findAllByExam_ExamId(examId).orElseGet(ArrayList::new);
        if (examAnswers.isEmpty()){
            examRepository.delete(exam);
        } else {
            List<Integer> examAnswerIds = new ArrayList<>();
            for (ExamAnswer answer : examAnswers) {
                examAnswerIds.add(answer.getId());
            }
            examAnswerRepository.deleteAllByIds(examAnswerIds);
            examRepository.delete(exam);
        }

    }

    /*
        Exam Answer Service
     */
    public void registerAnswer(ExamAnswerReqDto examAnswerReqDto) {
        Exam findExam = examRepository.findByLecture_IdAndExamId(examAnswerReqDto.lectureId(), examAnswerReqDto.examId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam or Lecture Not Found"));
        Account findAccount = accountRepository.findAccountById(examAnswerReqDto.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));

        ExamAnswer examAnswer = new ExamAnswer(findExam, findAccount, examAnswerReqDto.json());
        examAnswerRepository.save(examAnswer);
    }

    public void deleteAnswer(ExamAnswerReqDto examAnswerReqDto) {

        ExamAnswer findExamAnswer = examAnswerRepository.findById(examAnswerReqDto.answerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer Not Found"));

        examAnswerRepository.delete(findExamAnswer);
    }
}
