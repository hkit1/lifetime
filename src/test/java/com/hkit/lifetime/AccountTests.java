package com.hkit.lifetime;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.oauth.OauthRepository;
import com.hkit.lifetime.security.SecurityRole;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests extends LifetimeApplicationTests {
    @Autowired
    MockMvc mockmvc;

    @Autowired
    AccountRepository repository;
    @Autowired
    OauthRepository oauthRepository;

    // 매 테스트 마다 DB 초기화
    @BeforeEach
    void setUp() {
        oauthRepository.deleteAll();
        repository.deleteAll();
    }

    static MultiValueMap<String, String> createAccountInfo() {
        Faker faker = new Faker();
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("id", faker.internet().username());
        info.add("pw", faker.internet().password());
        info.add("name", faker.name().fullName());
        info.add("birth", faker.date().birthday().toLocalDateTime().format(DateTimeFormatter.BASIC_ISO_DATE));
        info.add("email", faker.internet().emailAddress());
        info.add("tel", faker.phoneNumber().phoneNumber());
        info.add("gender", String.valueOf(Math.round(Math.random())));
        info.add("address1", faker.address().streetAddress());
        info.add("address2", faker.address().secondaryAddress());
        info.add("role", SecurityRole.USER.name());
        info.add("company", SecurityRole.ADMIN.name());

        return info;
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void createAndLogin() throws Exception {
        // 계정 정보 무작위 생성
        MultiValueMap<String, String> info = createAccountInfo();

        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 계정 등록이 잘 되었는지 확인
        assertTrue(repository.findAccountById(info.getFirst("id")).isPresent());

        // 계정 정보 무작위 생성 그러나 상세 주소는 제외
        info = createAccountInfo();
        info.set("address2", "");

        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 상세 주소값이 없을 경우 계정 등록이 되는지 확인
        assertTrue(repository.findAccountById(info.getFirst("id")).isPresent());

        // 회사 정보가 없을 경우 계정 등록이 되는지 확인
        info = createAccountInfo();
        info.set("company", "");

        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 비밀번호가 평문으로 저장되어 있지 않는지 확인
        assertNotEquals(info.getFirst("pw"), repository.findAccountById(info.getFirst("id")).get().getPw());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void loginAccount() throws Exception {
        // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
        MultiValueMap<String, String> info = createAccountInfo();
        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 잘못된 비밀번호 테스트 - 401
        mockmvc.perform(post("/api/account/login")
                .param("id", info.getFirst("id"))
                .param("pw", info.getFirst("pw")+"wrong")
                .with(csrf())
        ).andExpect(status().isUnauthorized());

        // 정상 로그인 테스트 - 200
        mockmvc.perform(post("/api/account/login")
                .param("id", info.getFirst("id"))
                .param("pw", info.getFirst("pw"))
                .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void deleteAccount() throws Exception {
    //Spring security 권한 문제로 추가
        // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
        MultiValueMap<String, String> info = createAccountInfo();
        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 계정 삭제 테스트 - 200
        mockmvc.perform(post("/api/account/delete")
                .param("sessionId", info.getFirst("id"))
                .with(csrf())
        ).andExpect(status().isOk());

        // 검색된 계정이 없어야 함
        assertFalse(repository.findAccountById(info.getFirst("id")).isPresent());
    }

    @Test
    //Spring security 권한 문제로 추가
    @WithMockUser(username = "테스트_최고관리자", roles = {"OWNER"})
    void updateAccount() throws Exception {
        // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
        MultiValueMap<String, String> info = createAccountInfo();
        mockmvc.perform(post("/api/account/register").params(info).with(csrf()))
                .andExpect(status().isOk());

        // 정상 회원가입이 되었는지 확인
        Optional<Account> account = repository.findAccountById(info.getFirst("id"));
        assertTrue(account.isPresent());

        String oldAddress = info.getFirst("address1");
        info.set("address1", new Faker().address().streetAddress());

        mockmvc.perform(post("/api/account/update").params(info).with(csrf()))
                .andExpect(status().isOk());

        Optional<Account> account2 = repository.findAccountById(info.getFirst("id"));
        assertTrue(account2.isPresent());

        // 수정이 되었는지 확인
        assertNotEquals(account2.get().getAddress1(), oldAddress);
    }
}
