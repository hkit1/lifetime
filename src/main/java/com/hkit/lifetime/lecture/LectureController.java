package com.hkit.lifetime.lecture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LectureController {
    private final LectureService service;
    private final LectureContentService content;

    @PostMapping("/api/lecture/create")
    public String lectureRegister(LectureDto lectureDto) {
        service.save(lectureDto);
        return "home";
    }

    @PostMapping("/api/lecture/delete/{id}")
    public String lectureDelete(@PathVariable Integer id){
        service.delete(id);
        return "home";
    }

    @GetMapping("/lecture/{lectureName}")
    public String viewLecture(@PathVariable("lectureName") String lectureName, Model model){
        LectureDto lecturedto = service.findlecture(lectureName);
        List<LectureContentDto> contentdto = content.convertDto(content.findByLectureId(lecturedto.id()));
        model.addAttribute(lecturedto);
        model.addAttribute("contentList", contentdto);

        return "view";
    }

    @GetMapping("/lecture/{lectureName}/view")
    public String viewLecturecontent(@PathVariable("lectureName") String lectureName, Model model) {
        LectureDto lecturedto = service.findlecture(lectureName);
        model.addAttribute(lecturedto);
        return "home";
    }
}
