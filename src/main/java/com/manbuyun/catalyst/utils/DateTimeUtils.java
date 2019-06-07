/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
public class DateTimeUtils
{
    private DateTimeUtils() {}

    private static final DateTimeFormatter STANDARD_TIME_FORMATTER;
    private static final int MILLIS_SHIFT = 12;

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

    public static DateTime parseDateTime(String value)
    {
        try {
            return STANDARD_TIME_FORMATTER.parseDateTime(value);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(format("Invalid time '%s'", value));
        }
    }

    public static String toString(long instant)
    {
        return STANDARD_TIME_FORMATTER.print(instant);
    }

    public static long unpackMillisUtc(long dateTimeWithTimeZone)
    {
        return dateTimeWithTimeZone >> MILLIS_SHIFT;
    }
}
