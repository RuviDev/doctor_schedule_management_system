package com.ruvindu.doctorshiftmanagement.util;

import java.time.LocalDate;
import java.time.LocalTime;

// Method to validate the date
public class ShiftValidator {
    public static boolean isPastTime(String shiftDate, String startTime) {
        LocalDate date = LocalDate.parse(shiftDate);
        LocalTime time = LocalTime.parse(startTime);

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return date.isBefore(today) || (date.isEqual(today) && time.isBefore(now));
    }
}

