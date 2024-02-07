package com.hkit.lifetime;

import com.hkit.lifetime.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    public final LectureService lectureService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("lectureList", lectureService.findLectureByTop20());
        return "home";
    }

    @GetMapping("/a")
    public String mypage(){
        return "register_input";
    }

    @GetMapping("/b")
    public String asd(){
        return "register_clause";
    }

    @GetMapping("/c")
    public String zxc(Model model){
        MyLecture myLecture = new MyLecture("1", "15");
        model.addAttribute("my_lecture", myLecture);
        return "mypage";
    }

    class MyLecture{
        public String take;
        public String end;

        public MyLecture() {
        }

        public MyLecture(String take, String end) {
            this.take = take;
            this.end = end;
        }
    }

    class TakeLectutreTime{
        public String mon;
        public String tue;
        public String wed;
        public String thu;
        public String fri;
        public String sat;
        public String sun;

        public TakeLectutreTime() {
        }

        public TakeLectutreTime(String mon, String tue, String wed, String thu, String fri, String sat, String sun) {
            this.mon = mon;
            this.tue = tue;
            this.wed = wed;
            this.thu = thu;
            this.fri = fri;
            this.sat = sat;
            this.sun = sun;
        }
    }
}

