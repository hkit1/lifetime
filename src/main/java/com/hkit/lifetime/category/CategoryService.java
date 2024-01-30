package com.hkit.lifetime.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    /*
        Category Add
     */
    public void register(CategoryDto main, SubCategoryDto sub){
        try {
            Optional<Category> byName = categoryRepository.findByName(main.getName());
            if (byName.isPresent()){
                //상위 카테고리가 등록 되어 있을 때
                Category category = byName.get();

                SubCategory subCategory = new SubCategory(category, sub.getName());
                subCategoryRepository.save(subCategory);
            } else {
                //상위 카테고리가 등록 되어 있지 않을 때
                Category category = new Category(main.getName());
                Category saved = categoryRepository.save(category);

                SubCategory subCategory = new SubCategory(saved, sub.getName());
                subCategoryRepository.save(subCategory);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Main Category is Null");        }
    }

    /*
        Category Update
     */
    //Main Category Update
    public void update(CategoryDto oldCategoryDto, CategoryDto newCategoryDto) {
        Optional<Category> byName = categoryRepository.findByName(oldCategoryDto.getName());
        if (byName.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Main Category is Null");
        }

        Category category = byName.get();
        category.setName(newCategoryDto.getName());
        categoryRepository.save(category);
    }

    //Sub Category Update
    public void update(CategoryDto categoryDto, SubCategoryDto oldSubCategoryDto, SubCategoryDto newSubCategoryDto) {
        Optional<Category> byName = categoryRepository.findByName(categoryDto.getName());
        if (byName.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Main Category is Null");
        }

        Optional<SubCategory> findSubCategory = subCategoryRepository.findByCategoryNameAndName(categoryDto.getName(), oldSubCategoryDto.getName());
        if (findSubCategory.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sub Category is Null");
        }

        SubCategory subCategory = findSubCategory.get();
        subCategory.setName(newSubCategoryDto.getName());
        subCategoryRepository.save(subCategory);
    }

    /*
        Category Delete
     */
    //Category SubCategory delete
    public void delete(CategoryDto categoryDto) {
        //메인 카테고리 검사
        Optional<Category> findCategory = categoryRepository.findByName(categoryDto.getName());

        //메인 카테고리가 없으면 에러
        if (findCategory.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Not Found");
        }

        //서브 카테고리 검사
        Optional<List<SubCategory>> findSubCategoryList = subCategoryRepository.findAllByMainCategoryName(findCategory.get().getName());

        //서브 카테고리가 비어있을 시 메인 카테고리 바로 삭제
        if (findSubCategoryList.isEmpty()){
            categoryRepository.delete(findCategory.get());
        } else {
            //서브 카테고리가 있을 시 전체 삭제 후 메인 삭제
            List<SubCategory> subCategories = findSubCategoryList.get();
            for (SubCategory subCategory : subCategories) {
                subCategoryRepository.delete(subCategory);
            }
            categoryRepository.delete(findCategory.get());
        }
    }

    //Only SubCategory Delete
    public void delete(CategoryDto categoryDto, SubCategoryDto subCategoryDto) {
        //메인 카테고리와 서브 카테고리에 일치 하는 데이터가 있는지 검사
        Optional<SubCategory> findSubCategory = subCategoryRepository.findByCategoryNameAndName(categoryDto.getName(), subCategoryDto.getName());

        //없으면 에러
        if (findSubCategory.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Not Found");
        }

        SubCategory subCategory = findSubCategory.get();
        subCategoryRepository.delete(subCategory);
    }
}
