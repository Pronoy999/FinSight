package com.fin.sight.api.service;

import com.fin.sight.api.entities.TxnCategory;
import com.fin.sight.api.repository.TxnCategoryRepository;
import com.fin.sight.common.dto.CreateCategoryRequest;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.JwtUtils;
import com.fin.sight.common.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class TxnCategoryService {
    private final TxnCategoryRepository txnCategoryRepository;
    private List<TxnCategory> txnCategories;
    private final JwtUtils jwtUtils;

    @PostConstruct
    private void loadCategories() {
        log.info("Loading transaction categories from the database");
        txnCategories = txnCategoryRepository.findAll();
        log.info("Loaded {} transaction categories", txnCategories.size());
    }


    /**
     * Get the Txn Category by ID
     *
     * @param categoryId: the ID of the category
     * @return TxnCategory: the category object
     */
    public TxnCategory getTxnCategoryById(@NotNull final long categoryId) {
        return txnCategories.stream()
                .filter(category -> category.getId() == categoryId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Method to get all transaction categories.
     *
     * @param request: the request object containing the category details
     * @return the list of transaction categories
     */
    public ResponseEntity<?> createCategory(@NotNull final CreateCategoryRequest request, final String apiToken) {
        try {
            jwtUtils.verifyApiToken(apiToken);
            TxnCategory category = new TxnCategory();
            category.setName(request.name());
            category.setNature(request.nature());
            txnCategoryRepository.save(category);
            loadCategories(); //Reload categories to update the cached list
            return ResponseGenerator.generateSuccessResponse(txnCategories, HttpStatus.CREATED);
        } catch (InvalidTokenException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid X-API-KEY");
        } catch (Exception e) {
            log.error("Error occurred while creating category: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}