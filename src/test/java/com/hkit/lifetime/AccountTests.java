package com.hkit.lifetime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.oauth.OauthRepository;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTests {
  @Autowired MockMvc mockMvc;

  @Autowired AccountRepository repository;

  // https://velog.io/@mardi2020/Spring-Boot-Spring-Security-OAuth2-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%95%B4%EB%B3%B4%EA%B8%B0
  @Autowired
  OauthRepository oauthRepository;

  public static MultiValueMap<String, String> createAccountInfo() {
    Faker faker = new Faker(Locale.KOREAN);
    MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

    info.add("name", faker.name().fullName());
    info.add("id", faker.internet().username());
    info.add("pw", faker.internet().password());
    info.add(
        "birth",
        faker.date().birthday().toLocalDateTime().format(DateTimeFormatter.BASIC_ISO_DATE));
    info.add("email", faker.internet().emailAddress());
    info.add("tel", faker.phoneNumber().phoneNumber());
    info.add("gender", String.valueOf(faker.random().nextBoolean()));
    info.add("address1", faker.address().streetAddress());
    info.add("address2", faker.address().secondaryAddress());

    return info;
  }

  // 매 테스트 마다 DB 초기화
  @BeforeEach
  void setUp() {
    repository.deleteAll();
    oauthRepository.deleteAll();
  }

  @Test
  // Spring security 권한 문제로 추가
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"SUPER"})
  void createAndLogin() throws Exception {
    // 계정 정보 무작위 생성
    MultiValueMap<String, String> info = createAccountInfo();

    mockMvc
        .perform(post("/api/account/register").params(info).with(csrf()))
        .andExpect(status().isOk());

    // 계정 등록이 잘 되었는지 확인
    assertTrue(repository.findAccountById(info.getFirst("id")).isPresent());

    // 같은 ID로 등록할 경우 오류가 생기는지 확인
    mockMvc
        .perform(post("/api/account/check").param("id", info.getFirst("id")).with(csrf()))
        .andExpect(status().isBadRequest());

    // 계정 정보 무작위 생성 그러나 상세 주소는 제외
    info = createAccountInfo();
    info.set("address2", "");

    // 중복 ID 검사에서 통과 되는지 확인
    mockMvc
        .perform(post("/api/account/check").param("id", info.getFirst("id")).with(csrf()))
        .andExpect(status().isOk());

    mockMvc
        .perform(post("/api/account/register").params(info).with(csrf()))
        .andExpect(status().isOk());

    // 상세 주소값이 없을 경우 계정 등록이 되는지 확인
    assertTrue(repository.findAccountById(info.getFirst("id")).isPresent());

    // 비밀번호가 평문으로 저장되어 있지 않는지 확인
    assertNotEquals(
        info.getFirst("pw"), repository.findAccountById(info.getFirst("id")).get().getPw());
  }

  @Test
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void loginAccount() throws Exception {
    // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
    MultiValueMap<String, String> info = createAccountInfo();
    mockMvc
        .perform(post("/api/account/register").params(info).with(csrf()))
        .andExpect(status().isOk());

    // 잘못된 비밀번호 테스트 - 401
    mockMvc
        .perform(
            post("/api/account/login")
                .param("id", info.getFirst("id"))
                .param("pw", info.getFirst("pw") + "wrong")
                .with(csrf()))
        .andExpect(status().isUnauthorized());

    // 정상 로그인 테스트 - 200
    mockMvc
        .perform(
            post("/api/account/login")
                .param("id", info.getFirst("id"))
                .param("pw", info.getFirst("pw"))
                .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void deleteAccount() throws Exception {
    // Spring security 권한 문제로 추가
    // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
    MultiValueMap<String, String> info = createAccountInfo();
    mockMvc
        .perform(post("/api/account/register").params(info).with(csrf()))
        .andExpect(status().isOk());

    // 계정 삭제 테스트 - 200
    mockMvc
        .perform(post("/api/account/delete").param("sessionId", info.getFirst("id")).with(csrf()))
        .andExpect(status().isOk());

    // 검색된 계정이 없어야 함
    assertFalse(repository.findAccountById(info.getFirst("id")).isPresent());
  }

  @Test
  // Spring security 권한 문제로 추가
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void updateAccount() throws Exception {
    // 로그인할 계정 생성 (create 테스트가 먼저 완료 되어야 함)
    MultiValueMap<String, String> info = createAccountInfo();
    mockMvc
        .perform(post("/api/account/register").params(info).with(csrf()))
        .andExpect(status().isOk());

    // 정상 회원가입이 되었는지 확인
    Optional<Account> account = repository.findAccountById(info.getFirst("id"));
    assertTrue(account.isPresent());

    String oldAddress = info.getFirst("address1");
    info.set("address1", new Faker(Locale.KOREAN).address().streetAddress());

    mockMvc
        .perform(post("/api/account/update").params(info).with(csrf()))
        .andExpect(status().isOk());

    Optional<Account> account2 = repository.findAccountById(info.getFirst("id"));
    assertTrue(account2.isPresent());

    // 수정이 되었는지 확인
    assertNotEquals(account2.get().getAddress1(), oldAddress);
  }
}
