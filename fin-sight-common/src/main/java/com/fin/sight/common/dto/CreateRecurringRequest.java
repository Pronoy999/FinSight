package com.fin.sight.common.dto;

import java.math.BigDecimal;

public record CreateRecurringRequest(long accountId, String nature, String type, String frequency, String transferType,
                                     BigDecimal estimatedAmount) {
}
