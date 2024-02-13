package com.hkit.lifetime;

import com.hkit.lifetime.account.AccountService;
import com.hkit.lifetime.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MainController {
    public final LectureService lectureService;
    public final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("lectureList", lectureService.findLectureByTop20());
        return "home";
    }

    @GetMapping("/login")
    public String mypage(){
        return "login";
    }

    @GetMapping("/register/complete")
    public String reg_complete() {
        return "register_complete";
    }

    @GetMapping("/admin/lecture/create")
    public String lecture_create() {
        return "lecture_create";
    }

    @GetMapping("/b")
    public String asd(){
        return "course_registration_enrollment_institution";
    }

    // Test site mapping
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/content")
    public String content() {
        return "contents";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    @GetMapping("/satisfaction")
    public String satisfaction() {
        return "satisfaction.html";
    }

    @GetMapping("/c")
    public String zxc(Model model){
        MyLecture myLecture = new MyLecture("1", "15");
        TakeLectutreTime takeLectutreTime = new TakeLectutreTime("15h", "15h", "15h", "15h", "15h", "15h", "15h");

        ArrayList<NextLecture> nextLectures = new ArrayList<>();
        NextLecture nextLecture1 = new NextLecture("2월", "10일", "ㅁㄴㅇ", "ㅁㄴㅇ");
        NextLecture nextLecture2 = new NextLecture("2월", "10일", "ㅁㄴㅇ", "ㅁㄴㅇ");
        NextLecture nextLecture3 = new NextLecture("2월", "10일", "ㅁㄴㅇ", "ㅁㄴㅇ");
        nextLectures.add(nextLecture1);
        nextLectures.add(nextLecture2);
        nextLectures.add(nextLecture3);

        ArrayList<LectureExam> lectureExams = new ArrayList<>();
        LectureExam lectureExam1 = new LectureExam("asd", "asd", "1월 20일");
        LectureExam lectureExam2 = new LectureExam("asd", "asd", "1월 20일");
        lectureExams.add(lectureExam1);
        lectureExams.add(lectureExam2);

        model.addAttribute("my_lecture", myLecture);
        model.addAttribute("take_lecture_time", takeLectutreTime);
        model.addAttribute("next_lecture", nextLectures);
        model.addAttribute("lectureExams", lectureExams);

        return "mypage";
    }

    static class MyLecture{
        public String take;
        public String end;

        public MyLecture() {
        }

        public MyLecture(String take, String end) {
            this.take = take;
            this.end = end;
        }
    }

    static class TakeLectutreTime{
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

    static class NextLecture{
        public String month;
        public String day;
        public String lecture_name;
        public String lecture_info;

        public NextLecture() {
        }

        public NextLecture(String month, String day, String lecture_name, String lecture_info) {
            this.month = month;
            this.day = day;
            this.lecture_name = lecture_name;
            this.lecture_info = lecture_info;
        }
    }

    static class LectureExam{
        public String name;
        public String exam;
        public String deadline;

        public LectureExam() {
        }

        public LectureExam(String name, String exam, String deadline) {
            this.name = name;
            this.exam = exam;
            this.deadline = deadline;
        }
    }
}

