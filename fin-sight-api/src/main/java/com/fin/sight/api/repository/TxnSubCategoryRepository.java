package com.fin.sight.api.repository;

import com.fin.sight.api.entities.TxnSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxnSubCategoryRepository extends JpaRepository<TxnSubCategory, Long> {
    List<TxnSubCategory> findByCategoryId(Long categoryId);
}
