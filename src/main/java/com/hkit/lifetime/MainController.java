package com.hkit.lifetime;

import com.hkit.lifetime.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    public final LectureService lectureService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("lectureList", lectureService.findLectureByTop20());
        return "home";
    }

    @GetMapping("/mypage")
    public String mypage(){
        return "mypage";
    }
}
