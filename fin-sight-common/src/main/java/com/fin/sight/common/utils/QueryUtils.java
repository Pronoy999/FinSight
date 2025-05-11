package com.fin.sight.common.utils;

import com.fin.sight.common.dto.Filters;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryUtils {
    /**
     * Method to generate the filter query for the given filters
     *
     * @param filters : the list of filters to be applied
     * @return the query string with the filters applied
     */
    public static String getTxnFilterQuery(@NotNull final List<Filters> filters, @NotNull final String userGuid) {
        StringBuilder filterQuery = new StringBuilder();
        filterQuery.append("SELECT * FROM tbl_tran_log WHERE user_guid = '").append(userGuid).append("'");
        filters.forEach(filter -> {
            String fieldName = filter.fieldName();
            String value = filter.value();
            if (StringUtils.isNotEmpty(fieldName) || StringUtils.isNotEmpty(value)) {
                filterQuery.append(" AND ").append(fieldName).append(" = '").append(value).append("'");
            }
        });
        return filterQuery.toString();
    }
}
