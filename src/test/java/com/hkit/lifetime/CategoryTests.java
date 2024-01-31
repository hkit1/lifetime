package com.hkit.lifetime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hkit.lifetime.category.CategoryRepository;
import com.hkit.lifetime.category.SubCategory;
import com.hkit.lifetime.category.SubCategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTests {
  @Autowired MockMvc mockMvc;

  @Autowired CategoryRepository repository;
  @Autowired SubCategoryRepository subRepository;

  @BeforeEach
  void setUp() {
    subRepository.deleteAll();
    repository.deleteAll();
  }

  @Test
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void createCategory() throws Exception {
    // "메인보드" 라는 상위 카테고리가 추가 되는지 확인
    // 웹에서 상위 카테고리를 추가하면 하위 카테고리를 추가할 때 까지 추가 못하도록 해야 함.
    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf()))
        .andExpect(status().isOk());

    assertTrue(repository.findByName("메인보드").isPresent());
    assertTrue(subRepository.findByName("amd").isPresent());

    // 하위 카테고리를 추가할 경우, 상위 카테고리는 1개 값이, 하위 카테고리에는 2개 값이 있는지 확인
    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "intel").with(csrf()))
        .andExpect(status().isOk());

    assertEquals(1, repository.count());
    assertEquals(2, subRepository.count());

    // 웹에서 스크립트 오류 등으로 인해 상위 카테고리만 추가되거나 하위 카테고리만 추가 될 경우 서버에서 오류를 내는지 확인
    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "").with(csrf()))
        .andExpect(status().isBadRequest());

    //        // 모든 정보를 가져왔을 때, JSON 형식으로 반환되는지 확인
    //        // 웹에서 Javascript 으로 JSON 데이터를 활용할 것
    //        mockMvc.perform(get("/api/category/list").with(csrf()))
    //                .andExpect(status().isOk())
    //                .andExpect(content().string("{\"메인보드\":[\"amd\",\"intel\"]"));
  }

  @Test
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void deleteCategory() throws Exception {
    // 데이터 추가 (create 테스트가 먼저 완료되어야 함)
    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf()))
        .andExpect(status().isOk());

    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "intel").with(csrf()))
        .andExpect(status().isOk());

    // 하위 카테고리 값이 있을 경우, 값을 삭제하고 상위 카테고리가 남아있는지 확인
    mockMvc
        .perform(
            post("/api/category/delete").param("main", "메인보드").param("sub", "amd").with(csrf()))
        .andExpect(status().isOk());

    assertTrue(repository.findByName("메인보드").isPresent());

    // 하위 카테고리 값이 없을 경우, 하위 카테고리와 함께 상위 카테고리를 삭제하는지 확인
    mockMvc
        .perform(post("/api/category/delete").param("main", "메인보드").param("sub", "").with(csrf()))
        .andExpect(status().isOk());

    assertFalse(subRepository.findByName("intel").isPresent());
    assertFalse(repository.findByName("메인보드").isPresent());
  }

  @Test
  @WithMockUser(
      username = "테스트_최고관리자",
      roles = {"OWNER"})
  void updateCategory() throws Exception {
    // 데이터 추가 (create 테스트가 먼저 완료되어야 함)
    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "amd").with(csrf()))
        .andExpect(status().isOk());

    mockMvc
        .perform(post("/api/category/add").param("main", "메인보드").param("sub", "intel").with(csrf()))
        .andExpect(status().isOk());

    // 하위 카테고리의 이름이 변경 되었는지 확인
    mockMvc
        .perform(
            post("/api/category/update")
                .param("category", "메인보드")
                .param("oldName", "amd")
                .param("newName", "ami")
                .with(csrf()))
        .andExpect(status().isOk());

    assertTrue(subRepository.findByName("ami").isPresent());

    // 상위 카테고리의 이름이 변경되면서, 하위 카테고리를 유지하는지 확인
    mockMvc
        .perform(
            post("/api/category/update")
                .param("category", "")
                .param("oldName", "메인보드")
                .param("newName", "board")
                .with(csrf()))
        .andExpect(status().isOk());

    Optional<SubCategory> sub = subRepository.findByCategoryNameAndName("board", "ami");
    assertTrue(sub.isPresent());
  }
}
