package com.hkit.lifetime;

import com.google.gson.JsonParser;
import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyRepository;
import com.hkit.lifetime.exam.Exam;
import com.hkit.lifetime.exam.ExamAnswer;
import com.hkit.lifetime.exam.ExamAnswerRepository;
import com.hkit.lifetime.exam.ExamRepository;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureRepository;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static com.hkit.lifetime.CompanyTests.createRandomCompany;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExamTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ExamRepository repository;
    @Autowired
    ExamAnswerRepository answerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    LectureRepository lectureRepository;

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void createExam() throws Exception {
        // 시험을 등록 할 강좌 생성
        Lecture lecture = createLecture();

        // 시험이 등록되는지 확인
        MultiValueMap<String, String> info = randomExam(null);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(info).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());
        assertTrue(exam.isPresent());
        assertFalse(JsonParser.parseString(exam.get().getJson()).getAsJsonArray().isEmpty());
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void updateExam() throws Exception {
        // 수정할 시험 데이터 생성
        Lecture lecture = createLecture();

        MultiValueMap<String, String> info = randomExam(0);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(info).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());
        assertTrue(exam.isPresent());
        assertEquals(info.getFirst("json"), exam.get().getJson());

        MultiValueMap<String, String> updatedInfo = randomExam(3);

        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/update").params(updatedInfo).with(csrf())).andExpect(status().isOk());

        Optional<Exam> updatedExam = repository.findByLecture_Id(lecture.getId());
        assertTrue(updatedExam.isPresent());
        assertEquals(updatedInfo.getFirst("json"), updatedExam.get().getJson());
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteExam() throws Exception {
        // 삭제할 시험 데이터 생성
        Lecture lecture = createLecture();

        MultiValueMap<String, String> info = randomExam(0);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(info).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());
        assertTrue(exam.isPresent());

        // api/lecture/{lecture_id}/exam/{exam_id}/delete
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + exam.get().getExamId() + "/delete").params(info).with(csrf())).andExpect(status().isOk());
        assertFalse(repository.findByLecture_IdAndExamId(lecture.getId(), exam.get().getExamId()).isPresent());
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void createExamAnswer() throws Exception {
        // 시험을 등록 할 강좌 생성
        Lecture lecture = createLecture();

        // 시험 등록
        MultiValueMap<String, String> examInfo = randomExam(null);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(examInfo).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());

        // 시험을 칠 계정 생성
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Optional<Account> account = accountRepository.findAccountById(accountInfo.getFirst("id"));

        // 답안지 등록 확인
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + exam.get().getExamId())
                        .params(randomAnswer(null))
                        .param("accountId", account.get().getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        assertTrue(answerRepository.findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(lecture.getId(), exam.get().getExamId(), account.get().getId()).isPresent());
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteExamAnswer() throws Exception {
        // 시험을 등록 할 강좌 생성
        Lecture lecture = createLecture();

        // 시험 등록
        MultiValueMap<String, String> examInfo = randomExam(null);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(examInfo).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());

        // 시험을 칠 계정 생성
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Optional<Account> account = accountRepository.findAccountById(accountInfo.getFirst("id"));

        // 답안지 등록
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + exam.get().getExamId())
                        .params(randomAnswer(null))
                        .param("accountId", account.get().getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        Optional<ExamAnswer> answer = answerRepository.findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(lecture.getId(), exam.get().getExamId(), account.get().getId());
        assertTrue(answer.isPresent());

        // api/lecture/{lecture_id}/exam/{exam_id}/{exam_answer_id}/delete
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + exam.get().getExamId() + "/" + answer.get().getId() + "/delete")
                        .params(randomAnswer(null))
                        .param("accountId", account.get().getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        assertFalse(answerRepository.findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(lecture.getId(), exam.get().getExamId(), account.get().getId()).isPresent());
    }

    // todo 시험 삭제하면 답안지도 삭제되는걸 확인하는 코드
    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteExamAndAnswer() throws Exception {
        // 시험을 등록 할 강좌 생성
        Lecture lecture = createLecture();

        // 시험 등록
        MultiValueMap<String, String> examInfo = randomExam(null);
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/create").params(examInfo).with(csrf())).andExpect(status().isOk());

        Optional<Exam> exam = repository.findByLecture_Id(lecture.getId());

        // 시험을 칠 계정 생성
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Optional<Account> account = accountRepository.findAccountById(accountInfo.getFirst("id"));
        Integer examId = exam.get().getExamId();

        // 답안지 등록
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + examId)
                        .params(randomAnswer(null))
                        .param("accountId", account.get().getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        Optional<ExamAnswer> answer = answerRepository.findByExam_Lecture_IdAndExam_ExamIdAndAccount_Id(lecture.getId(), examId, account.get().getId());
        assertTrue(answer.isPresent());

        // 시험 삭제
        mockMvc.perform(post("/api/lecture/" + lecture.getId() + "/exam/" + examId + "/delete").params(examInfo).with(csrf())).andExpect(status().isOk());
        assertFalse(repository.findByLecture_IdAndExamId(lecture.getId(), examId).isPresent());

        // 시험 삭제 후 답안도 삭제되는지 확인
        assertFalse(repository.findByLecture_IdAndExamId(lecture.getId(), examId).isPresent());
        assertFalse(answerRepository.findByExam_Lecture_IdAndExam_ExamId(lecture.getId(), examId).isPresent());
    }

    MultiValueMap<String, String> randomExam(Integer random) {
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        // text - 글자만 있는 문제
        // image - 사진도 있는 문제
        // {"text": "1+1은?"} -> 글자만 있는 문제
        // {"image": "image url"} -> 사진만 있는 문제
        // {"text: "이 사진에 있는 것은?", "image": "image url"} -> 글자와 사진이 모두 있는 문제
        // [{"text": "1+1은?"}, {"text": "2+2는?"}] -> 글자만 있는 문제가 2개 있는 문제
        // [{"image": "image url", "text": 이 사진은 무엇인가?"}, {"text": "아 빨리 집가고 싶다"}] -> 문제 1번에는 사진과 글이 있고, 문제 2번에는 글만 있는 문제

        String[] list = {"[{\"text\": \"1+1은?\"}]", "[{\"image\": \"image url\"}]", "[{\"text\": \"이 사진에 있는 것은?\", \"image\": \"image url\"}]", "[{\"text\": \"1+1은?\"}, {\"text\": \"2+2는?\"}]", "[{\"image\": \"image url\", \"text\": \"이 사진은 무엇인가?\"}, {\"text\": \"아 빨리 집가고 싶다\"}]"};
        info.add("json", list[Objects.requireNonNullElseGet(random, () -> (int) (Math.random() * 5))]);

        return info;
    }

    MultiValueMap<String, String> randomAnswer(Integer random) {
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        String[] list = {"[2]", "[\"사진\"]", "[\"집\"]", "[2, 4]", "[\"null\", \"집으로!\"]"};
        info.add("json", list[Objects.requireNonNullElseGet(random, () -> (int) (Math.random() * 5))]);

        return info;
    }

    Lecture createLecture() throws Exception {
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Account account = accountRepository.findAccountById(accountInfo.getFirst("id")).get();

        MultiValueMap<String, String> companyInfo = createRandomCompany();
        mockMvc.perform(post("/api/company/create").params(companyInfo).with(csrf())).andExpect(status().isOk());

        Company company = companyRepository.findByName(companyInfo.getFirst("name")).get();

        mockMvc.perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf())).andExpect(status().isOk());

        SubCategory category = subCategoryRepository.findByName("amd").get();

        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", faker.starCraft().building());
        info.add("created_at", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("closed_at", LocalDate.now().plusDays(faker.random().nextInt(3, 30)).format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("company_name", company.getName());
        info.add("teacher", account.getName());
        info.add("main_category", category.getMainCategory().getName());
        info.add("sub_category", category.getName());

        mockMvc.perform(post("/api/lecture/create").params(info).with(csrf())).andExpect(status().isOk());

        return lectureRepository.findByName(info.getFirst("name")).get();
    }
}
