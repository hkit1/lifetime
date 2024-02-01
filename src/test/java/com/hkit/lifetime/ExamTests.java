package com.hkit.lifetime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ExamTests {
  @Autowired MockMvc mockMvc;

  @Test
  void createExam() {}

  @Test
  void updateExam() {}

  @Test
  void deleteExam() {}

  @Test
  void createExamAnswer() {}

  @Test
  void deleteExamAnswer() {}
}
