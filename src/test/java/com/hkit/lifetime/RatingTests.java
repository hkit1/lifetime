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
import com.hkit.lifetime.lecture.LectureContentRepository;
import com.hkit.lifetime.lecture.LectureRepository;
import com.hkit.lifetime.rating.Rating;
import com.hkit.lifetime.rating.RatingRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static com.hkit.lifetime.CompanyTests.createRandomCompany;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RatingRepository repository;
    @Autowired
    LectureRepository lectureRepository;
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

    @Test
    void createRating() throws Exception {
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/account/register").params(accountInfo).with(csrf())).andExpect(status().is3xxRedirection());
        Account account = accountRepository.findAccountById(accountInfo.getFirst("id")).get();
        MultiValueMap<String, String> companyInfo = createRandomCompany();
        mockMvc.perform(post("/api/company/create").params(companyInfo).with(csrf())).andExpect(status().isOk());
        Company company = companyRepository.findByName(companyInfo.getFirst("name")).get();
        mockMvc.perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf())).andExpect(status().isOk());
        SubCategory category = subCategoryRepository.findByName("amd").get();

        MultiValueMap<String, String> info = randomLecture(company, account, category);
        InputStream file = new URL("https://picsum.photos/600/400").openStream();
        MockMultipartFile mockFile = new MockMultipartFile("file", "random.png", "image/png", file);
        mockMvc.perform(multipart("/api/lecture/create").file(mockFile).with(csrf()).params(info)).andExpect(status().isOk());
        Lecture lecture = lectureRepository.findByName(info.getFirst("name")).get();

        for (int i = 0; i < 5; i++) {
            MultiValueMap<String, String> ratingInfo = randomRating(lecture, account);
            mockMvc.perform(post("/api/lecture/1/rate/create").params(ratingInfo).with(csrf())).andExpect(status().isOk());

            List<Rating> rating = repository.findByLecture_Id(lecture.getId());
            assertFalse(rating.isEmpty());
            boolean isExists = false;
            for (int j = 0; j < rating.size(); j++) {
                if (ratingInfo.getFirst("text").equals(rating.get(i).getText())) {
                    isExists = true;
                }
            }
            assertTrue(isExists);
        }
    }

    public MultiValueMap<String, String> randomLecture(Company company, Account account, SubCategory category) {
        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", faker.starCraft().building());
        info.add("description", faker.weather().description());
        info.add("created_at", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("closed_at", LocalDate.now().plusDays(faker.random().nextInt(3, 30)).format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("company_name", company.getName());
        info.add("teacher", account.getName());
        info.add("main_category", category.getMainCategory().getName());
        info.add("sub_category", category.getName());

        return info;
    }

    public MultiValueMap<String, String> randomRating(Lecture lecture, Account account) {
        Faker faker = new Faker(Locale.KOREAN);
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("id", lecture.getId().toString());
        info.add("name", account.getName());
        info.add("star", faker.random().nextInt(0, 5).toString());
        info.add("text", faker.lorem().characters(10, 30));

        return info;
    }
}
