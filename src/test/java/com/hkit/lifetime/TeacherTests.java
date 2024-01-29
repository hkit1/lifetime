package com.hkit.lifetime;

import com.hkit.lifetime.account.AccountRepository;
import com.hkit.lifetime.teacher.Teacher;
import com.hkit.lifetime.teacher.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TeacherRepository repository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void setTeacher() throws Exception {
        // 강사로 등록할 계정 생성 (AccountTests 에서 createAndLogin 가 먼저 완료되어야 함)
        MultiValueMap<String, String> accountInfo = createAccountInfo();
        mockMvc.perform(post("/api/account/register").params(accountInfo))
                .andExpect(status().isOk());

        Optional<Teacher> teacher = repository.findByAccount_Id(accountInfo.getFirst("id"));

        // DB에 생성된 계정이 강사로 등록 되었는지 확인
        assertTrue(teacher.isPresent());

        // 계정을 만들 때 입력한 company 이름과, Teacher 저장소에서 강사를 조회했을 때 같은 회사인지 확인
        assertEquals(accountInfo.getFirst("company"), teacher.get().getAccount().getCompany());
    }
}
