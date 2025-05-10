package com.fin.sight.api.controller;

import com.fin.sight.api.service.TxnCategoryService;
import com.fin.sight.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Constants.TXN_CATEGORY_PATH)
@RequiredArgsConstructor
@Slf4j
public class TxnCategoryController {
    private final TxnCategoryService txnCategoryService;

    @GetMapping
    public ResponseEntity<?> getTxnCategories() {
        log.info("Fetching transaction categories");
        return ResponseEntity.ok(txnCategoryService.getTxnCategories());
    }

    @GetMapping(path = "/sub-categories/{categoryId}")
    public ResponseEntity<?> getTxnSubCategories(@PathVariable(name = "categoryId") int categoryId) {
        log.info("Fetching transaction sub-categories");
        return ResponseEntity.ok(txnCategoryService.getSubCategories(categoryId));
    }
}
