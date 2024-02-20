package com.hkit.lifetime;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountDto;
import com.hkit.lifetime.account.AccountService;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyDto;
import com.hkit.lifetime.company.CompanyService;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureOutputDto;
import com.hkit.lifetime.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    public final LectureService lectureService;
    public final AccountService accountService;
    public final CompanyService companyService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("lectureList", lectureService.findLectureByTop12());
        return "home";
    }

    @GetMapping("/member/login")
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

    @GetMapping("/admin/category/create")
    public String cate_create(){
        return "category_create";
    }

    @GetMapping("/b")
    public String asd(){
        return "admin";
    }

    // Test site mapping
    @GetMapping("/admin")
    public String admin(Model model) {
        LocalDate time = LocalDate.now();
        model.addAttribute("week_created", accountService.countByDate(time.minusWeeks(1)));
        model.addAttribute("week_lecture", lectureService.countByDate(time.minusWeeks(1)));
        model.addAttribute("week_company", companyService.countByDate(time.minusWeeks(1)));
        model.addAttribute("month_created", accountService.countByDate(time.minusMonths(1)));
        model.addAttribute("month_lecture", lectureService.countByDate(time.minusMonths(1)));
        model.addAttribute("month_company", companyService.countByDate(time.minusMonths(1)));
        model.addAttribute("total_created", accountService.countAll());
        model.addAttribute("total_lecture", lectureService.countAll());
        model.addAttribute("total_company", companyService.countAll());

        List<Account> accounts = accountService.getAllByPage(PageRequest.of(0, 10));
        List<AccountDto> accountDto = new ArrayList<>();
        for (Account a : accounts) {
            accountDto.add(new AccountDto(
                    a.getUuid(),
                    a.getName(),
                    a.getId(),
                    a.getPw(),
                    a.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    a.getEmail(),
                    a.getTel(),
                    a.getGender(),
                    a.getAddress1(),
                    a.getAddress2(),
                    a.getCreated_at()
            ));
        }
        model.addAttribute("accountList", accountDto);

        List<Company> companys = companyService.getAllByPage(PageRequest.of(0, 10));
        List<CompanyDto> companyDto = new ArrayList<>();
        for (Company c : companys) {
            companyDto.add(new CompanyDto(
                    c.getCompanyId(),
                    c.getId(),
                    c.getName(),
                    c.getAdmin(),
                    c.getEmail(),
                    c.getAddress1(),
                    c.getAddress2(),
                    c.getTel(),
                    c.getAuthorized(),
                    c.getAuthorizedDate()
            ));
        }
        model.addAttribute("companyList", companyDto);

        List<Lecture> lectures = lectureService.getAllByPage(PageRequest.of(0, 10));
        List<LectureOutputDto> lectureOutputDto = new ArrayList<>();
        for (Lecture l : lectures) {
            lectureOutputDto.add(new LectureOutputDto(
                    l.getId(),
                    l.getName(),
                    l.getDescription(),
                    l.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getClosedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getTeacher().getName(),
                    l.getCategory().getMainCategory().getName() + " / " + l.getCategory().getName(),
                    l.getCompany().getName()
            ));
        }

        model.addAttribute("lectureList", lectureOutputDto);

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
        return "satisfaction";
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

        // todo 기업 계정 등록 유무에 따라 exists 값 바꾸기
        boolean exists = true;

        model.addAttribute("company", exists);

        return "mypage";
    }

    @GetMapping("/c/admin")
    public String zz(Model model) {
        List<Lecture> lectures = lectureService.getAllByPage(PageRequest.of(0, 10));
        List<LectureOutputDto> lectureOutputDto = new ArrayList<>();
        for (Lecture l : lectures) {
            lectureOutputDto.add(new LectureOutputDto(
                    l.getId(),
                    l.getName(),
                    l.getDescription(),
                    l.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getClosedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    l.getTeacher().getName(),
                    l.getCategory().getMainCategory().getName() + " / " + l.getCategory().getName(),
                    l.getCompany().getName()
            ));
        }

        model.addAttribute("lectureList", lectureOutputDto);

        return "mypage_lecture";
    }

    @GetMapping("/c/admin/register")
    public String zzz(Model mode) {
        return "mypage_lecture_create";
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

