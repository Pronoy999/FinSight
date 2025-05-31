package com.fin.sight.common.dto;

import java.math.BigDecimal;
import java.util.List;

public record GetTxnRequest(List<Filters> filters) {
}
