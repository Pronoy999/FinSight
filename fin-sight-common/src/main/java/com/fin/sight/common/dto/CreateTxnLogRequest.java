package com.fin.sight.common.dto;

import java.math.BigDecimal;

public record CreateTxnLogRequest(
        String userGuid,
        int year,
        int month,
        int date,
        int accountId,
        int txnCategoryId,
        Integer txnSubCategoryId,
        String txnFrequency,
        int recurringId,
        String transferType,
        BigDecimal txnAmount
) {
}