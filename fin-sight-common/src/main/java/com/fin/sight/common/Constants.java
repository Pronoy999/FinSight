package com.fin.sight.common;

import java.util.List;

public class Constants {
    public static final String HEALTH_CHECK_API_PATH = "/ping";
    public static final String USER_PATH = "/user";
    public static final String LOGIN_PATH = "/login";
    public static final String ACCOUNT_PATH = "/account";
    public static final String TXN_CATEGORY_PATH = "/txn-category";
    public static final String TXN_LOG_PATH = "/txn-log";
    public static final String RECURRING_PATH = "/recurring";
    public static final String TXN_SEARCH_PATH = "/search";
    public static final String TXN_SEARCH_BY_ID_PATH = "/{id}";

    public static final String GENERIC_ERROR_MESSAGE = "Something went wrong";
    public static final String USER_TOKEN_HEADER = "user-token";
    public static final String X_API_KEY = "x-api-key";
    public static final String USER_GUID_FIELD = "user_guid";

    public static final List<String> TXN_FILTER_FIELDS = List.of("category_id", "year", "date", "month", "account_id", "txn_frequency", "recurring_id", "transfer_type", "txn_amount", "created");
}
