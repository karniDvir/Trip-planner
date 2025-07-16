package com.travelplanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for date-related operations.
 */
public class DateUtils {

    /**
     * Generates a list of LocalDate objects between the given start and end dates,
     * inclusive of both start and end dates.
     *
     * @param startDate The start date of the range (inclusive)
     * @param endDate   The end date of the range (inclusive)
     * @return A list of LocalDate objects representing each date in the range
     */
    public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }
}
