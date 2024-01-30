package com.hkit.lifetime.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/category/add")
    public String createCategory(@RequestParam(name = "main") String main,
                                 @RequestParam(name = "sub") String sub){

        //공백을 제외한 실제 문자를 포함 하는 지 검사
        if (!StringUtils.hasText(main) || !StringUtils.hasText(sub)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input Param null");
        }

        CategoryDto categoryDto = new CategoryDto(null, main);
        SubCategoryDto subCategoryDto = new SubCategoryDto(null, null, sub);

        categoryService.register(categoryDto, subCategoryDto);

        return "home";

    }

    @PostMapping("/api/category/update")
    public String updateCategory(@RequestParam(name = "category") String main,
                                 @RequestParam(name = "oldName") String oldName,
                                 @RequestParam(name = "newName") String newName){

        log.info("category :" + main);
        log.info("oldName :" + oldName);
        log.info("newName :" + newName);

        if (!StringUtils.hasText(main)){
            CategoryDto oldCategoryDto = new CategoryDto(null, oldName);
            CategoryDto newCategoryDto = new CategoryDto(null, newName);

            categoryService.update(oldCategoryDto, newCategoryDto);

            return "home";
        } else {
            CategoryDto categoryDto = new CategoryDto(null, main);
            SubCategoryDto oldSubCategoryDto = new SubCategoryDto(null, null, oldName);
            SubCategoryDto newSubCategoryDto = new SubCategoryDto(null, null, newName);

            categoryService.update(categoryDto, oldSubCategoryDto, newSubCategoryDto);
            return "home";
        }
    }

    @PostMapping("/api/category/delete")
    public String deleteCategory(@RequestParam(name = "main") String main,
                                 @RequestParam(name = "sub") String sub){

        if (!StringUtils.hasText(sub)){
            CategoryDto categoryDto = new CategoryDto(null, main);
            categoryService.delete(categoryDto);

            return "home";
        } else {
            CategoryDto categoryDto = new CategoryDto(null, main);
            SubCategoryDto subCategoryDto = new SubCategoryDto(null, null, sub);
            categoryService.delete(categoryDto, subCategoryDto);

            return "home";
        }
    }

}
