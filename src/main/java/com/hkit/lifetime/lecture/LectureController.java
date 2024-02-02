package com.hkit.lifetime.lecture;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LectureController {
    private final LectureService lectureService;


    @PostMapping("/api/lecture/create")
    public String lectureRegister(LectureDto lectureDto) {

        lectureService.save(lectureDto);
        return "home";


    }

    @PostMapping("/api/lecture/delete/{id}")
    public String lectureDelete(@PathVariable Integer id){
        lectureService.delete(id);
        return "home";
    }

    @GetMapping("/lecture/{lectureName}")
    public String viewLecture(@PathVariable("lectureName") String lectureName, Model model){
        LectureDto lecturedto = lectureService.findlecture(lectureName);
        model.addAttribute(lecturedto);
        return "home";
    }

    @GetMapping("/lecture/{lectureName}/view")
    public String viewLecturecontent(@PathVariable("lectureName") String lectureName, Model model) {
        LectureDto lecturedto = lectureService.findlecture(lectureName);
        model.addAttribute(lecturedto);
        return "home";
    }








}
