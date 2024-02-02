package com.hkit.lifetime;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyRepository;
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

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static com.hkit.lifetime.CompanyTests.createRandomCompany;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExamTests {
    @Autowired
    MockMvc mockMvc;
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
        createLecture();
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void updateExam() {
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteExam() {
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void createExamAnswer() {
    }

    @Test
    @Transactional
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteExamAnswer() {
    }

    Lecture createLecture() throws Exception {
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/api/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        Account account = accountRepository.findAccountById(accountInfo.getFirst("uuid")).get();

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


        mockMvc.perform(post("/api/lecture/create").params(info)).andExpect(status().isOk());

        return lectureRepository.findByName(info.getFirst("name")).get();
    }
}
