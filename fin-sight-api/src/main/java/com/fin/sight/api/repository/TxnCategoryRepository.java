package com.fin.sight.api.repository;

import com.fin.sight.api.entities.TxnCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxnCategoryRepository extends JpaRepository<TxnCategory, Long> {
    @Query("SELECT c FROM TxnCategory c")
    List<TxnCategory> findAll();
}