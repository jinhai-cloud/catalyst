package com.manbuyun.catalyst.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimePrinter;

import static java.lang.String.format;

/**
 * @author jinhai
 * @date 2019/05/20
 */
public class DateTimeUtils {

    private DateTimeUtils() {
    }

    private static final DateTimeFormatter STANDARD_TIME_FORMATTER;

    static {
        DateTimeParser[] parsers = {
                DateTimeFormat.forPattern("yyyy-MM-dd").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd HH").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getParser()};
        DateTimePrinter printer = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getPrinter();
        STANDARD_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .append(printer, parsers)
                .toFormatter()
                .withOffsetParsed();
    }

    public static DateTime parseDateTime(String value) {
        try {
            return STANDARD_TIME_FORMATTER.parseDateTime(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(format("Invalid time '%s'", value));
        }
    }
}