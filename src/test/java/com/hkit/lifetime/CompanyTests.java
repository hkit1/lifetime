package com.hkit.lifetime;

import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.company.CompanyRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTests {
    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;

    @Autowired
    CompanyRepository repository;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    @Test
    void createCompany() throws Exception {
        Faker faker = new Faker();

        // 회사 이름이 등록되는지 확인
        mockMvc.perform(post("/api/company/create")
                        .param("name", faker.company().name())
                        .with(csrf()))
                .andExpect(status().isOk());

        assertTrue(repository.findByName(faker.company().name()).isPresent());
    }

    @Test
    void deleteCompany() throws Exception {
        // 삭제할 데이터 생성 (createCompany 테스트가 먼저 완료 되어야 함)
        Faker faker = new Faker();
        mockMvc.perform(post("/api/company/create")
                        .param("name", faker.company().name())
                        .with(csrf()))
                .andExpect(status().isOk());

        // 회사 이름이 삭제되는지 확인
        mockMvc.perform(post("/api/company/delete")
                        .param("name", faker.company().name())
                        .with(csrf()))
                .andExpect(status().isOk());

        assertFalse(repository.findByName(faker.company().name()).isPresent());
    }

    @Test
    void registerCompany() throws Exception {
        // 등록될 회사 데이터 생성 (createCompany 테스트가 먼저 완료 되어야 함)
        Faker faker = new Faker();
        mockMvc.perform(post("/api/company/create")
                        .param("name", faker.company().name())
                        .with(csrf()))
                .andExpect(status().isOk());

        // 계정 등록할 때 회사 등록이 되는지 확인 (AccountTests 에서 createAndLogin 테스트가 먼저 완료되어야 함)
        MultiValueMap<String, String> info = createAccountInfo();
        info.set("company", "nothing");

        // 회원가입 창에서 없는 회사를 입력했을 때 Bad request 출력 (아래 코드를 사용)
        // @ResponseStatus(code = HttpStatus.BadRequest, reason = "Company not found")
        // 관리자 계정 (강사 계정)을 생성할 때에는 /api/account/admin/register 사용
        mockMvc.perform(post("/api/account/admin/register").params(info).with(csrf()))
                .andExpect(status().isBadRequest());

        // 있는 회사를 입력했을 때 OK
        info.set("company", faker.company().name());
        mockMvc.perform(post("/api/account/admin/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        assertEquals(faker.company().name(), accountRepository.findAccountById(faker.internet().username()).get().getCompany());
    }
}
