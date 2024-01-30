package com.hkit.lifetime.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    Optional<SubCategory> findByName(String name);

    @Query("select s from SubCategory s where s.mainCategory.name = :mainName and s.name = :name")
    Optional<SubCategory> findByCategoryNameAndName(@Param("mainName") String mainName, @Param("name") String name);

    @Query("select s from SubCategory s where s.mainCategory.name = :mainName")
    Optional<List<SubCategory>> findAllByMainCategoryName(@Param("mainName") String mainName);
}