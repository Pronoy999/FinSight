package com.fin.sight.common.dto;

import java.math.BigDecimal;

public record CreateTxnLogRequest(
        String userGuid,
        int year,
        int month,
        int date,
        long accountId,
        int txnCategoryId,
        Integer txnSubCategoryId,
        String txnFrequency,
        long recurringId,
        String transferType,
        BigDecimal txnAmount
) {
}