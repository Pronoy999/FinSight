package com.fin.sight.api.controller;

import com.fin.sight.api.service.TxnCategoryService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestHeader Map<String, String> requestHeaders, @RequestBody CreateCategoryRequest request) {
        if (requestHeaders.isEmpty()) {
            return ResponseEntity.status(401).body("Missing X-API-KEY");
        }
        String apiToken = requestHeaders.get(Constants.X_API_KEY);
        return txnCategoryService.createCategory(request, apiToken);
    }
}
