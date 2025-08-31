package com.fin.sight.common.dto;

import java.math.BigDecimal;

public record CreateTxnLogRequest(
        String userGuid,
        String date,
        String txnTime,
        long accountId,
        int txnCategoryId,
        String txnFrequency,
        long recurringId,
        String transferType,
        BigDecimal txnAmount,
        BigDecimal userShare,
        boolean isSharedExpense
) {
}