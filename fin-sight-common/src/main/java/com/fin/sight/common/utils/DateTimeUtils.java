package com.fin.sight.common.utils;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {

    /**
     * Method to parse the date String in YYYY-MM-DD format to an array of integers [year, month, day].
     *
     * @param date: The date string in YYYY-MM-DD format.
     * @return An array of integers where index 0 is year, index 1 is month, and index 2 is day.
     */
    public static int[] parseDate(@NotNull String date) {
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format");
        }

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        return new int[]{year, month, day};
    }
}
