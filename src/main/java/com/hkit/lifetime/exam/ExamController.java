package com.hkit.lifetime.exam;

import com.hkit.lifetime.exam.dto.ExamAnswerDto;
import com.hkit.lifetime.exam.dto.ExamAnswerReqDto;
import com.hkit.lifetime.exam.dto.ExamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/api/lecture/{id}/exam/create")
    public String createExam(@PathVariable(name = "id") Integer id,
                          ExamDto examDto){

        examService.registerExam(id, examDto);
        return "home";

    }

    @PostMapping("/api/lecture/{id}/exam/update")
    public String updateExam(@PathVariable(name = "id") Integer id,
                           ExamDto examDto){

        examService.updateExam(id, examDto);
        return "home";
    }

    @PostMapping("/api/lecture/{lectureId}/exam/{examId}/delete")
    public String deleteExam(@PathVariable(name = "lectureId") Integer lectureId,
                             @PathVariable(name = "examId") Integer examId){
        examService.deleteExam(lectureId, examId);

        return "home";
    }

    @PostMapping("/api/lecture/{lectureId}/exam/{examId}")
    public String examAnswerCreate(@PathVariable(name = "lectureId") Integer lectureId,
                                   @PathVariable(name = "examId") Integer examId,
                                   ExamAnswerDto examAnswerDto){
        ExamAnswerReqDto examAnswerReqDto = new ExamAnswerReqDto(lectureId, examId, null,examAnswerDto.accountId(), examAnswerDto.json());
        examService.registerAnswer(examAnswerReqDto);
        return "home";
    }

    @PostMapping("/api/lecture/{lectureId}/exam/{examId}/{answerId}/delete")
    public String examAnswerDelete(@PathVariable(name = "lectureId") Integer lectureId,
                                   @PathVariable(name = "examId") Integer examId,
                                   @PathVariable(name = "answerId") Integer answerId,
                                   @RequestParam(name = "accountId") String accountId){
        ExamAnswerReqDto examAnswerReqDto = new ExamAnswerReqDto(lectureId, examId, answerId, accountId, null);
        examService.deleteAnswer(examAnswerReqDto);
        return "home";
    }

}
