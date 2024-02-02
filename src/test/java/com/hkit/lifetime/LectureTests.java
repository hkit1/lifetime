package com.hkit.lifetime;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.CategoryRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyAccountListRepository;
import com.hkit.lifetime.company.CompanyRepository;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureContent;
import com.hkit.lifetime.lecture.LectureContentRepository;
import com.hkit.lifetime.lecture.LectureRepository;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static com.hkit.lifetime.CompanyTests.createRandomCompany;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LectureTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    LectureRepository repository;
    @Autowired
    LectureContentRepository contentRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CompanyAccountListRepository companyAccountListRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    Lecture static_lecture = null;

    public static MultiValueMap<String, String> randomLectureContent(Lecture lecture) {
        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", faker.text().text());
        info.add("url", "https://exampleurl.com");
        info.add("description", "normal_description");
        info.add("lecture_id", String.valueOf(lecture.getId()));

        return info;
    }

    public MultiValueMap<String, String> randomLecture(Company company, Account account, SubCategory category) {
        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", faker.starCraft().building());
        info.add("created_at", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("closed_at", LocalDate.now().plusDays(faker.random().nextInt(3, 30)).format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("company_name", company.getName());
        info.add("teacher", account.getName());
        info.add("main_category", category.getMainCategory().getName());
        info.add("sub_category", category.getName());

        return info;
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void createLecture() throws Exception {
        // 강사 계정 생성
        // AccountTests 에서 createAndLogin, TeacherTests 에서 setTeacher 가 먼저 완료되어야 함
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/api/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Account account = accountRepository.findAccountById(accountInfo.getFirst("id")).get();

        // 회사 생성 (CompanyTests 에서 createCompany 가 먼저 완료되어야 함)
        MultiValueMap<String, String> companyInfo = createRandomCompany();

        mockMvc.perform(post("/api/company/create").params(companyInfo).with(csrf())).andExpect(status().isOk());

        Company company = companyRepository.findByName(companyInfo.getFirst("name")).get();

        // 카테고리 생성 (CategoryTests 에서 createCategory 가 먼저 완료되어야 함
        mockMvc.perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf())).andExpect(status().isOk());

        SubCategory category = subCategoryRepository.findByName("amd").get();

        // 오류 없이 강좌 생성이 되는지 확인
        MultiValueMap<String, String> info = randomLecture(company, account, category);
        mockMvc.perform(post("/api/lecture/create").with(csrf()).params(info)).andExpect(status().isOk());

        Optional<Lecture> lecture = repository.findByName(info.getFirst("name"));

        assertTrue(lecture.isPresent());
        // todo assert 추가

        static_lecture = lecture.get();
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void deleteLecture() throws Exception {
        // 삭제할 강좌 데이터 생성 (createLecture 테스트가 먼저 완료되어야 함)
        createLecture();
        Integer lectureId = static_lecture.getId();

        // 강좌가 삭제되는지 확인
        mockMvc.perform(post("/api/lecture/delete/" + lectureId).with(csrf())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void createLectureContent() throws Exception {
        // 수정할 회차 데이터 생성 (createLecture 테스트가 먼저 완료되어야 함)
        createLecture();

        // 강좌 내 회차 생성 확인
        // 주소는 /api/lecture/{lecture_id}/create 으로 됨.
        MultiValueMap<String, String> lecture_content_info = randomLectureContent(static_lecture);
        mockMvc.perform(post("/api/lecture/" + static_lecture.getId() + "/create").params(lecture_content_info).with(csrf())).andExpect(status().isOk());

        assertTrue(contentRepository.findByLecture_Id(static_lecture.getId()).isPresent());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void updateLectureContent() throws Exception {
        // 수정할 회차 데이터 생성 (createLecture 테스트가 먼저 완료되어야 함)
        createLecture();

        MultiValueMap<String, String> info = randomLectureContent(static_lecture);
        mockMvc.perform(post("/api/lecture/" + static_lecture.getId() + "/create").params(info).with(csrf())).andExpect(status().isOk());

        Optional<LectureContent> content = contentRepository.findByLecture_IdAndName(static_lecture.getId(), info.getFirst("name"));
        assertTrue(content.isPresent());

        // 회차 이름을 변경 했을 경우 적용이 되는지 확인
        String changedText = new Faker().text().text();
        info.set("name", changedText);

        mockMvc.perform(post("/api/lecture/" + static_lecture.getId() + "/" + content.get().getId() + "/update").with(csrf()).params(info)).andExpect(status().isOk());

        assertEquals(changedText, contentRepository.findByLecture_IdAndName(static_lecture.getId(), info.getFirst("name")).get().getName());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void deleteLectureContent() throws Exception {
        // 수정할 회차 데이터 생성 (createLecture 테스트가 먼저 완료되어야 함)
        createLecture();

        MultiValueMap<String, String> info = randomLectureContent(static_lecture);
        mockMvc.perform(post("/api/lecture/" + static_lecture.getId() + "/create").params(info).with(csrf())).andExpect(status().isOk());

        Optional<LectureContent> content = contentRepository.findByLecture_IdAndName(static_lecture.getId(), info.getFirst("name"));
        assertTrue(content.isPresent());

        // 회차가 삭제되는지 확인
        mockMvc.perform(post("/api/lecture/" + static_lecture.getId() + "/" + content.get().getId() + "/delete").params(info).with(csrf())).andExpect(status().isOk());

        assertFalse(contentRepository.findByLecture_IdAndName(static_lecture.getId(), info.getFirst("name")).isPresent());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void viewLecture() throws Exception {
        createLecture();

        // 강좌에서 회차를 볼 수 있는지 확인
        Integer lectureId = static_lecture.getId();

        // todo 회차 보는 화면 디자인 필요
        // 데이터를 가져 올 수 있는지 확인
        // thymeleaf 으로 구현 (직접 화면으로 보고 구현해야 됨)
        mockMvc.perform(get("/lecture/" + static_lecture.getName()).with(csrf())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    @Transactional
    void viewLectureContent() throws Exception {
        createLecture();

        // 이것도 thymeleaf 으로 구현
        // 영상 스트리밍이 될 것
        mockMvc.perform(get("/lecture/" + static_lecture.getName() + "/view").with(csrf())).andExpect(status().isOk());
    }
}
