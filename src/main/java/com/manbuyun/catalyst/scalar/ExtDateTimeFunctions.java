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
package com.manbuyun.catalyst.scalar;

import com.manbuyun.catalyst.utils.DateTimeUtils;
import io.airlift.slice.Slice;
import io.prestosql.spi.connector.ConnectorSession;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

import static io.airlift.slice.Slices.utf8Slice;
import static java.lang.Math.toIntExact;
import static java.util.concurrent.TimeUnit.DAYS;

/**
 * @author jinhai
 * @date 2019/05/19
 */
public class ExtDateTimeFunctions
{
    private ExtDateTimeFunctions() {}

    @ScalarFunction("date_add")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType("varchar(10)")
    public static Slice dateAddStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value)
    {
        String extract = DateTimeUtils.extractDay(slice.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = LocalDate.parse(extract);
            LocalDate dt2 = dt1.plusDays(toIntExact(value));
            return utf8Slice(dt2.toString());
        }
        return null;
    }

    @ScalarFunction("date_add")
    @SqlType("varchar(10)")
    public static Slice dateAdd(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(DAYS.toMillis(date)).plusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("date_add")
    @SqlType("varchar(10)")
    public static Slice dateAddTimestamp(@SqlType(StandardTypes.TIMESTAMP) long timestamp, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(timestamp).plusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("date_add")
    @SqlType("varchar(10)")
    public static Slice dateAddTimeZone(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long timestampWithTimeZone, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDateZone(timestampWithTimeZone).plusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("date_sub")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType("varchar(10)")
    public static Slice dateSubStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value)
    {
        String extract = DateTimeUtils.extractDay(slice.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = LocalDate.parse(extract);
            LocalDate dt2 = dt1.minusDays(toIntExact(value));
            return utf8Slice(dt2.toString());
        }
        return null;
    }

    @ScalarFunction("date_sub")
    @SqlType("varchar(10)")
    public static Slice dateSub(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(DAYS.toMillis(date)).minusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("date_sub")
    @SqlType("varchar(10)")
    public static Slice dateSubTimestamp(@SqlType(StandardTypes.TIMESTAMP) long timestamp, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(timestamp).minusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("date_sub")
    @SqlType("varchar(10)")
    public static Slice dateSubTimeZone(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long timestampWithTimeZone, @SqlType(StandardTypes.BIGINT) long value)
    {
        LocalDate dt = DateTimeUtils.parseLocalDateZone(timestampWithTimeZone).minusDays(toIntExact(value));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("to_date")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType("varchar(10)")
    public static Slice toDateStr(@SqlType("varchar(x)") Slice slice)
    {
        String extract = DateTimeUtils.extractDay(slice.toStringUtf8());
        if (extract != null) {
            return utf8Slice(extract);
        }
        return null;
    }

    @ScalarFunction("to_date")
    @SqlType("varchar(10)")
    public static Slice toDate(@SqlType(StandardTypes.DATE) long date)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(DAYS.toMillis(date));
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("to_date")
    @SqlType("varchar(10)")
    public static Slice toDateTimestamp(@SqlType(StandardTypes.TIMESTAMP) long timestamp)
    {
        LocalDate dt = DateTimeUtils.parseLocalDate(timestamp);
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("to_date")
    @SqlType("varchar(10)")
    public static Slice toDateTimeZone(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long timestampWithTimeZone)
    {
        LocalDate dt = DateTimeUtils.parseLocalDateZone(timestampWithTimeZone);
        return utf8Slice(dt.toString());
    }

    @ScalarFunction("datediff")
    @LiteralParameters({"x", "y"})
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff1(@SqlType("varchar(x)") Slice left, @SqlType("varchar(y)") Slice right)
    {
        String dt1 = DateTimeUtils.extractDay(left.toStringUtf8());
        String dt2 = DateTimeUtils.extractDay(right.toStringUtf8());

        if (dt1 != null && dt2 != null) {
            return (long) Period.between(LocalDate.parse(dt2), LocalDate.parse(dt1)).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @SqlType(StandardTypes.INTEGER)
    public static long dateDiff2(@SqlType(StandardTypes.DATE) long left, @SqlType(StandardTypes.DATE) long right)
    {
        LocalDate dt1 = DateTimeUtils.parseLocalDate(DAYS.toMillis(left));
        LocalDate dt2 = DateTimeUtils.parseLocalDate(DAYS.toMillis(right));
        return Period.between(dt2, dt1).getDays();
    }

    @ScalarFunction("datediff")
    @SqlType(StandardTypes.INTEGER)
    public static long dateDiff3(@SqlType(StandardTypes.TIMESTAMP) long left, @SqlType(StandardTypes.TIMESTAMP) long right)
    {
        LocalDate dt1 = DateTimeUtils.parseLocalDate(left);
        LocalDate dt2 = DateTimeUtils.parseLocalDate(right);
        return Period.between(dt2, dt1).getDays();
    }

    @ScalarFunction("datediff")
    @SqlType(StandardTypes.INTEGER)
    public static long dateDiff4(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long left, @SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long right)
    {
        LocalDate dt1 = DateTimeUtils.parseLocalDateZone(left);
        LocalDate dt2 = DateTimeUtils.parseLocalDateZone(right);
        return Period.between(dt2, dt1).getDays();
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff5(@SqlType("varchar(x)") Slice left, @SqlType(StandardTypes.DATE) long right)
    {
        String extract = DateTimeUtils.extractDay(left.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDate(extract);
            LocalDate dt2 = DateTimeUtils.parseLocalDate(DAYS.toMillis(right));
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff6(@SqlType(StandardTypes.DATE) long left, @SqlType("varchar(x)") Slice right)
    {
        String extract = DateTimeUtils.extractDay(right.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDate(DAYS.toMillis(left));
            LocalDate dt2 = DateTimeUtils.parseLocalDate(extract);
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff7(@SqlType("varchar(x)") Slice left, @SqlType(StandardTypes.TIMESTAMP) long right)
    {
        String extract = DateTimeUtils.extractDay(left.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDate(extract);
            LocalDate dt2 = DateTimeUtils.parseLocalDate(right);
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff8(@SqlType(StandardTypes.TIMESTAMP) long left, @SqlType("varchar(x)") Slice right)
    {
        String extract = DateTimeUtils.extractDay(right.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDate(left);
            LocalDate dt2 = DateTimeUtils.parseLocalDate(extract);
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff9(@SqlType("varchar(x)") Slice left, @SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long right)
    {
        String extract = DateTimeUtils.extractDay(left.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDate(extract);
            LocalDate dt2 = DateTimeUtils.parseLocalDateZone(right);
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long dateDiff10(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long left, @SqlType("varchar(x)") Slice right)
    {
        String extract = DateTimeUtils.extractDay(right.toStringUtf8());
        if (extract != null) {
            LocalDate dt1 = DateTimeUtils.parseLocalDateZone(left);
            LocalDate dt2 = DateTimeUtils.parseLocalDate(extract);
            return (long) Period.between(dt2, dt1).getDays();
        }
        return null;
    }

    @ScalarFunction("from_unixtime")
    @SqlType("varchar(19)")
    public static Slice fromUnixTime(@SqlType(StandardTypes.BIGINT) long unixTime)
    {
        return utf8Slice(DateTimeUtils.print(unixTime * 1000));
    }

    @ScalarFunction("from_unixtime")
    @LiteralParameters("x")
    @SqlType("varchar(x)")
    public static Slice fromUnixTime(@SqlType(StandardTypes.BIGINT) long unixTime, @SqlType("varchar(x)") Slice slice)
    {
        DateTime dt = new DateTime(unixTime * 1000);
        return utf8Slice(dt.toString(slice.toStringUtf8()));
    }

    @ScalarFunction("unix_timestamp")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp(ConnectorSession session)
    {
        return session.getStartTime() / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.BIGINT)
    public static Long unixTimestamp(@SqlType("varchar(x)") Slice slice)
    {
        try {
            FastDateFormat instance = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
            return instance.parse(slice.toStringUtf8()).getTime() / 1000;
        }
        catch (ParseException e) {
            return null;
        }
    }

    @ScalarFunction("unix_timestamp")
    @LiteralParameters({"x", "y"})
    @SqlNullable
    @SqlType(StandardTypes.BIGINT)
    public static Long unixTimestamp(@SqlType("varchar(x)") Slice left, @SqlType("varchar(y)") Slice right)
    {
        try {
            FastDateFormat instance = FastDateFormat.getInstance(right.toStringUtf8());
            return instance.parse(left.toStringUtf8()).getTime() / 1000;
        }
        catch (ParseException e) {
            return null;
        }
    }

    @ScalarFunction("unix_timestamp")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp1(@SqlType(StandardTypes.DATE) long date)
    {
        DateTime dt = new DateTime(DAYS.toMillis(date)).withTimeAtStartOfDay();
        return dt.getMillis() / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp2(@SqlType(StandardTypes.TIMESTAMP) long timestamp)
    {
        return timestamp / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp3(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long timestampWithTimeZone)
    {
        return DateTimeUtils.unpackMillisUtc(timestampWithTimeZone) / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp11(@SqlType(StandardTypes.DATE) long date, @SqlType("varchar(x)") Slice slice)
    {
        DateTime dt = new DateTime(DAYS.toMillis(date)).withTimeAtStartOfDay();
        return dt.getMillis() / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp22(@SqlType(StandardTypes.TIMESTAMP) long timestamp, @SqlType("varchar(x)") Slice slice)
    {
        return timestamp / 1000;
    }

    @ScalarFunction("unix_timestamp")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long unixTimestamp33(@SqlType(StandardTypes.TIMESTAMP_WITH_TIME_ZONE) long timestampWithTimeZone, @SqlType("varchar(x)") Slice slice)
    {
        return DateTimeUtils.unpackMillisUtc(timestampWithTimeZone) / 1000;
    }

    @ScalarFunction("date_format")
    @LiteralParameters({"x", "y"})
    @SqlNullable
    @SqlType("varchar(y)")
    public static Slice dateFormat(@SqlType("varchar(x)") Slice left, @SqlType("varchar(y)") Slice right)
    {
        DateTime dt = DateTimeUtils.parseDateTime(left.toStringUtf8());
        if (dt != null) {
            return utf8Slice(dt.toString(right.toStringUtf8()));
        }
        return null;
    }

    @ScalarFunction("weekofyear")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long weekOfYear(@SqlType("varchar(x)") Slice slice)
    {
        DateTime dt = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        if (dt != null) {
            return (long) dt.getWeekOfWeekyear();
        }
        return null;
    }
}
