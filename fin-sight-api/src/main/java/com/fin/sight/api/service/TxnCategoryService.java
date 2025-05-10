package com.fin.sight.api.service;

import com.fin.sight.api.entities.TxnCategory;
import com.fin.sight.api.entities.TxnSubCategory;
import com.fin.sight.api.repository.TxnCategoryRepository;
import com.fin.sight.api.repository.TxnSubCategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class TxnCategoryService {
    private final TxnCategoryRepository txnCategoryRepository;
    private final TxnSubCategoryRepository txnSubCategoryRepository;
    private List<TxnCategory> txnCategories;
    private Map<Long, List<TxnSubCategory>> txnSubCategoryMap;

    @PostConstruct
    private void loadCategories() {
        log.info("Loading transaction categories from the database");
        txnCategories = txnCategoryRepository.findAll();
        log.info("Loaded {} transaction categories", txnCategories.size());
        txnSubCategoryMap = new HashMap<>();
        txnCategories.forEach(category -> {
            List<TxnSubCategory> subCategories = txnSubCategoryRepository.findByCategoryId(category.getId());
            txnSubCategoryMap.put(category.getId(), subCategories);
        });
    }

    public List<TxnSubCategory> getSubCategories(long categoryId) {
        return txnSubCategoryMap.get(categoryId);
    }
}
