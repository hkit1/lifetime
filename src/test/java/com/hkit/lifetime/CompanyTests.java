package com.hkit.lifetime;

import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.company.CompanyAccountList;
import com.hkit.lifetime.company.CompanyAccountListRepository;
import com.hkit.lifetime.company.CompanyRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;
import java.util.Optional;

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
    CompanyAccountListRepository listRepository;
    @Autowired
    AccountRepository accountRepository;

    public static MultiValueMap<String, String> createRandomCompany() {
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        Faker faker = new Faker(Locale.KOREAN);
        // id 값은 사업자 등록번호
        info.add("id", String.valueOf(faker.random().nextInt(900000000) + 100000000));
        info.add("name", faker.company().name());
        info.add("admin", faker.name().fullName());
        info.add("email", faker.internet().emailAddress());
        info.add("address1", faker.address().streetAddress());
        info.add("address2", faker.address().secondaryAddress());
        info.add("tel", faker.phoneNumber().cellPhone());

        return info;
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    @Test
    void createCompany() throws Exception {
        MultiValueMap<String, String> info = createRandomCompany();

        // 회사 등록 요청이 되는지 확인
        mockMvc.perform(post("/api/company/create").params(info).with(csrf())).andExpect(status().isOk());

        assertTrue(repository.findByName(info.getFirst("name")).isPresent());
    }

    @Test
    void acceptCompany() throws Exception {
        // 등록할 회사 데이터 추가 (createCompany 가 먼저 완료되어야 함)
        MultiValueMap<String, String> company1 = createRandomCompany();
        MultiValueMap<String, String> company2 = createRandomCompany();

        // 회사 등록 요청이 되는지 확인
        mockMvc.perform(post("/api/company/create").params(company1).with(csrf())).andExpect(status().isOk());

        mockMvc.perform(post("/api/company/create").params(company2).with(csrf())).andExpect(status().isOk());

        // 관리자가 회사 등록 요청 들어온 것을 승인 할 수 있는지 확인
        mockMvc.perform(post("/api/company/accept").param("id", company1.getFirst("id")).with(csrf())).andExpect(status().isOk());

        assertTrue(repository.findByName(company1.getFirst("name")).get().getAuthorized());

        // 관리자가 회사 등록 요청 들어온 것을 거절 할 수 있는지 확인
        mockMvc.perform(post("/api/company/denied").param("id", company2.getFirst("id")).with(csrf())).andExpect(status().isOk());

        assertFalse(repository.findByName(company2.getFirst("name")).get().getAuthorized());
    }

    // 강사로 계정 등록
    @Test
    void connectCompany() throws Exception {
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        MultiValueMap<String, String> companyInfo = createRandomCompany();

        // 강사로 등록할 계정 생성 (AccountTest 에서 createAndLogin 테스트가 완료되어야 함)
        mockMvc.perform(post("/api/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        // 회사 등록 요청이 되는지 확인
        mockMvc.perform(post("/api/company/create").params(companyInfo).with(csrf())).andExpect(status().isOk());

        // 강사로 등록할 때 /api/account/register/company 으로 회사 이름과 사업자 등록번호를 전송
        mockMvc.perform(post("/api/company/register/account").param("name", companyInfo.getFirst("name")).param("id", accountInfo.getFirst("id")).with(csrf())).andExpect(status().isOk());

        Optional<CompanyAccountList> list = listRepository.findByAccount_Id(accountInfo.getFirst("id"));

        assertTrue(list.isPresent());
        assertEquals(accountRepository.findAccountById(accountInfo.getFirst("id")).get().getUuid(), list.get().getAccount().getUuid());
    }

    @Test
    void deleteCompany() throws Exception {
        // 삭제할 데이터 생성 (createCompany 테스트가 먼저 완료 되어야 함)
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        MultiValueMap<String, String> companyInfo = createRandomCompany();

        mockMvc.perform(post("/api/company/create").params(companyInfo).with(csrf())).andExpect(status().isOk());

        // 등록된 회사에 강사 계정이 등록되어 있을 경우 삭제되지 않는지 확인
        // 바로 위에 있는 connectCompany 테스트가 완료되어야 함
        mockMvc.perform(post("/api/account/register").params(accountInfo).with(csrf())).andExpect(status().isOk());

        mockMvc.perform(post("/api/company/register/account")
                        .param("name", companyInfo.getFirst("name"))
                        .param("id", accountInfo.getFirst("id")).with(csrf())).andExpect(status().isOk());

        mockMvc.perform(post("/api/company/delete").param("id", companyInfo.getFirst("id")).with(csrf())).andExpect(status().isBadRequest());

        assertTrue(repository.findByName(companyInfo.getFirst("name")).isPresent());

        // 등록된 계정이 없을 때 회사 이름이 삭제되는지 확인
        // id 값은 사업자등록번호
        listRepository.deleteAll();

        mockMvc.perform(post("/api/company/delete").param("id", companyInfo.getFirst("id")).with(csrf())).andExpect(status().isOk());

        assertFalse(repository.findByName(companyInfo.getFirst("name")).isPresent());
    }
}
