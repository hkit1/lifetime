package com.hkit.lifetime;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.Category;
import com.hkit.lifetime.category.CategoryRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyRepository;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureRepository;
import com.hkit.lifetime.teacher.Teacher;
import com.hkit.lifetime.teacher.TeacherRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    LectureRepository repository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    MultiValueMap<String, String> randomLecture(Company company, Teacher teacher, Category mainCategory, SubCategory subCategory) {
        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", faker.starCraft().building());
        info.add("created_at", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("closed_at", LocalDate.now().plusDays(faker.random().nextInt(3,30)).format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("company_name", company.getName());
        info.add("teacher", teacher.getName());
        info.add("main_category", mainCategory.getName());
        info.add("sub_category", subCategory.getName());

        return info;
    }

//    @Test
//    void createLecture() throws Exception {
//        // 강사 계정 생성
//        // AccountTests 에서 createAndLogin, TeacherTests 에서 setTeacher 가 먼저 완료되어야 함
//        MultiValueMap<String, String> accountInfo = createAccountInfo();
//        mockMvc.perform(post("/api/account/register").params(accountInfo).with(csrf()))
//                .andExpect(status().isOk());
//
//        Teacher teacher = teacherRepository.findByAccount_Id(accountInfo.getFirst("id")).get();
//
//        // 회사 생성 (CompanyTests 에서 createCompany 가 먼저 완료되어야 함)
//        Faker faker = new Faker(Locale.KOREAN);
//
//        mockMvc.perform(post("/api/company/create")
//                        .param("name", faker.company().name())
//                        .with(csrf()))
//                .andExpect(status().isOk());
//
//        Company company = companyRepository.findByName(faker.company().name()).get();
//
//        // 카테고리 생성 (CategoryTests 에서 createCategory 가 먼저 완료되어야 함
//        mockMvc.perform(post("/api/category/add")
//                .param("main", "메인보드")
//                .param("sub", "amd")
//                .with(csrf())
//        ).andExpect(status().isOk());
//
//        SubCategory category = subCategoryRepository.findByName("amd").get();
//
//        // 오류 없이 강좌 생성이 되는지 확인
//        MultiValueMap<String, String> info = randomLecture(company, teacher, category.getMainCategory(), category);
//        mockMvc.perform(post("/api/lecture/create").params(info))
//                .andExpect(status().isOk());
//
//        Optional<Lecture> lecture = repository.findByName(info.getFirst("name"));
//
//        assertTrue(lecture.isPresent());
//        Integer lectureId = lecture.get().getLectureId();
//
//        // 데이터를 가져 올 수 있는지 확인
//        mockMvc.perform(post("/api/lecture/" + lectureId + "/info").params(info))
//                .andExpect(status().isOk())
//                        .andExpect(content().json(""));
//    }

    @Test
    void deleteLecture() throws Exception {
        mockMvc.perform(post("/api/lecture/delete"))
                .andExpect(status().isOk());
    }
}
