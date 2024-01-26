package com.hkit.lifetime;

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

import java.util.Objects;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTests {
    @Autowired
    WebApplicationContext context;
    MockMvc mockmvc;

    @Autowired
    CompanyRepository repository;

    @BeforeEach
    void setUp() {
        this.mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    @Test
    void createCompany() throws Exception {
        // 관리자로 설정할 계정 생성 (AccountTest 에서 createAndLogin 테스트가 선행 완료 되어야 함)
        MultiValueMap<String, String> info = createAccountInfo();
        Faker daesanghyuk = new Faker();

        String sessionId = Objects.requireNonNull(
                mockmvc.perform(put("/api/account/register").params(info))
                .andExpect(status().isOk())
                .andReturn().getRequest().getSession()).getId();

        // 회사 등록이 잘 되는지 확인
        // account 파라메터에는 세션 ID이 입력되고, 이 sessionId 으로 계정을 찾아내서 등록
        mockmvc.perform(put("/api/company/add")
                .param("name", daesanghyuk.company().name())
                .param("account", sessionId)
        ).andExpect(status().isOk());

        assertTrue(repository.findByName(daesanghyuk.company().name()).isPresent());
        assertEquals(daesanghyuk.name().fullName(), repository.findByName(daesanghyuk.company().name()).get().getAccount().getName());
    }

    @Test
    void deleteCompany(){
        mockmvc.perform()
    }

    @Test
    void updateCompany(){

    }
}
