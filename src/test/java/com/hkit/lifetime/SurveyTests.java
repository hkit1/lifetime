package com.hkit.lifetime;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static com.hkit.lifetime.CompanyTests.createRandomCompany;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.category.Category;
import com.hkit.lifetime.category.CategoryRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import com.hkit.lifetime.company.Company;
import com.hkit.lifetime.company.CompanyAccountListRepository;
import com.hkit.lifetime.company.CompanyRepository;
import com.hkit.lifetime.lecture.Lecture;
import com.hkit.lifetime.lecture.LectureContentRepository;
import com.hkit.lifetime.lecture.LectureRepository;
import com.hkit.lifetime.survey.*;
import jakarta.transaction.Transactional;
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
public class SurveyTests {
  @Autowired MockMvc mockMvc;

  @Autowired SurveyRepository repository;
  @Autowired
  SurveyAnswerRepository answerRepository;
  @Autowired LectureRepository lectureRepository;
  @Autowired LectureContentRepository contentRepository;
  @Autowired AccountRepository accountRepository;
  @Autowired CompanyAccountListRepository companyAccountListRepository;
  @Autowired CompanyRepository companyRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired SubCategoryRepository subCategoryRepository;

  @Transactional
  public MultiValueMap<String, String> randomLecture(
      Company company, Account account, Category mainCategory, SubCategory subCategory) {
    Faker faker = new Faker(Locale.KOREAN);
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

    info.add("name", faker.starCraft().building());
    info.add("created_at", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    info.add(
        "closed_at",
        LocalDate.now()
            .plusDays(faker.random().nextInt(3, 30))
            .format(DateTimeFormatter.BASIC_ISO_DATE));
    info.add("company_name", company.getName());
    info.add("teacher", account.getName());
    info.add("main_category", mainCategory.getName());
    info.add("sub_category", subCategory.getName());

    return info;
  }

  MultiValueMap<String, String> randomSurvey() {
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

    info.add("title", "");
    info.add("text", "");

    return info;
  }

  Lecture createLecture() throws Exception {
    MultiValueMap<String, String> accountInfo = createAccountInfo();
    mockMvc
        .perform(post("/api/account/register").params(accountInfo).with(csrf()))
        .andExpect(status().isOk());

    Account account = accountRepository.findAccountById(accountInfo.getFirst("uuid")).get();

    MultiValueMap<String, String> companyInfo = createRandomCompany();
    mockMvc
        .perform(post("/api/company/create").params(companyInfo).with(csrf()))
        .andExpect(status().isOk());

    Company company = companyRepository.findByName(companyInfo.getFirst("name")).get();

    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf()))
        .andExpect(status().isOk());

    SubCategory category = subCategoryRepository.findByName("amd").get();

    MultiValueMap<String, String> info =
        randomLecture(company, account, category.getMainCategory(), category);
    mockMvc.perform(post("/api/lecture/create").params(info)).andExpect(status().isOk());

    return lectureRepository.findByName(info.getFirst("name")).get();
  }

  MultiValueMap<String, String> createJson(String name) {
      MultiValueMap<String, String> info = createAccountInfo();
      JsonArray json = new JsonArray();

      for(int i = 1; i < 10; i++) {
        json.add(name + " " + i);
      }

      info.add("json", json.toString());

      return info;
  }

  @Test
  void createSurvey() throws Exception {
    // 평가할 강좌 생성 (LectureTest 에서 createLecture 가 먼저 완료되어야 함)
    Lecture lecture = createLecture();
    MultiValueMap<String, String> info = createJson("질문");

    // Json 으로 데이터 사용 (DB 에 Json 데이터로 저장)
    // 질문 1 -> json[0], 질문 2 -> json[1]
    // 이것도 thymeleaf 에서 th:each 를 사용하거나 fetch api 를 사용하게 될 예정
    // ["질문1", "질문2", "질문3", ...] 으로 저장됨
    mockMvc.perform(post("/api/survey/" + lecture.getId() + "/create").params(info)).andExpect(status().isOk());

    Optional<Survey> survey = repository.findByLecture_Id(lecture.getId());

    assertTrue(survey.isPresent());
    assertEquals("질문3", JsonParser.parseString(survey.get().getJson()).getAsJsonArray().get(3).getAsString());
  }

  @Test
  void updateSurvey() throws Exception {
    // 평가 수정할 강좌 생성 (LectureTest 에서 createLecture 가 먼저 완료되어야 함)
    Lecture lecture = createLecture();
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

    // Json 으로 데이터 사용
    // 위와 똑같이 json 형식으로 사용됨
    JsonArray json = new JsonArray();

    for (int i = 1; i < 10; i++) {
      if (i == 3) {
        json.add("질문 100");
      } else {
        json.add("질문 " + i);
      }
    }

    info.add("json", json.toString());

    mockMvc
        .perform(post("/api/survey/" + lecture.getId() + "/update").params(info))
        .andExpect(status().isOk());

    Optional<Survey> survey = repository.findByLecture_Id(lecture.getId());

    assertTrue(survey.isPresent());
    assertEquals(
        "질문100",
        JsonParser.parseString(survey.get().getJson()).getAsJsonArray().get(3).getAsString());
  }

  @Test
  void createAnswer() throws Exception {
    // 평가할 강좌 생성
    // createSurvey 하고 createLecture 가 먼저 완료되어야 함
    Lecture lecture = createLecture();
    MultiValueMap<String, String> info = createJson("답변");

    // Json 으로 데이터 사용
    // 위와 똑같이 json 형식으로 저장됨
    mockMvc.perform(post("/api/survey/" + lecture.getId() + "/answer").params(info)).andExpect(status().isOk());

    Optional<SurveyAnswer> answer = answerRepository.findById_Lecture_Id(lecture.getId());

    assertTrue(answer.isPresent());
    assertEquals("답변3", JsonParser.parseString(answer.get().getJson()).getAsJsonArray().get(3).getAsString());
  }
}
