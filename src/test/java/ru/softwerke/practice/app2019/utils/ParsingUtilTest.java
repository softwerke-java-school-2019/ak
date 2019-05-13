package ru.softwerke.practice.app2019.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class ParsingUtilTest {

    @Test
    void should_convert_local_date() {
        String dateStr = "12.12.2012";
        assertTrue(parseLocalDate(dateStr));

        dateStr = "1.1.2012";
        assertFalse(parseLocalDate(dateStr));
    }

    private static boolean parseLocalDate(String dateStr) {
        try {
            LocalDate date = ParsingUtil.getLocalDate(dateStr);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @Test
    void should_convert_local_date_time() {
        String dateTimeStr = "12.12.2012 12:12:12";
        assertTrue(parseLocalDateTime(dateTimeStr));

        dateTimeStr = "1.1.2012";
        assertFalse(parseLocalDateTime(dateTimeStr));
    }

    @Test

    private static boolean parseLocalDateTime(String dateTimeStr) {
        try {
            LocalDateTime dateTime = ParsingUtil.getLocalDateTime(dateTimeStr);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}