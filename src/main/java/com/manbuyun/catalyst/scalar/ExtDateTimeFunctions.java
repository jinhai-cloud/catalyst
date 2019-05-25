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
import io.prestosql.spi.function.Description;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import org.joda.time.DateTime;
import org.joda.time.Days;

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

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    @Description("add the specified amount of time to the given time")
    @LiteralParameters("x")
    @ScalarFunction("date_add")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateAddStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt1 = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        DateTime dt2 = dt1.plusDays(toIntExact(value));
        return utf8Slice(dt2.toString(YYYY_MM_DD));
    }

    @Description("add the specified amount of time to the given time")
    @ScalarFunction("date_add")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateAdd(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt = new DateTime(DAYS.toMillis(date)).plusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @Description("add the specified amount of time to the given time")
    @ScalarFunction("date_add")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateAddUnix(@SqlType(StandardTypes.TIMESTAMP) long timestamp, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt = new DateTime(timestamp).plusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @Description("sub the specified amount of time to the given time")
    @LiteralParameters("x")
    @ScalarFunction("date_sub")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateSubStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt1 = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        DateTime dt2 = dt1.minusDays(toIntExact(value));
        return utf8Slice(dt2.toString(YYYY_MM_DD));
    }

    @Description("sub the specified amount of time to the given time")
    @ScalarFunction("date_sub")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateSub(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt = new DateTime(DAYS.toMillis(date)).minusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @Description("sub the specified amount of time to the given time")
    @ScalarFunction("date_sub")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateSubUnix(@SqlType(StandardTypes.TIMESTAMP) long timestamp, @SqlType(StandardTypes.BIGINT) long value)
    {
        DateTime dt = new DateTime(timestamp).minusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @ScalarFunction("to_date")
    @LiteralParameters("x")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDateStr(@SqlType("varchar(x)") Slice slice)
    {
        DateTime dt = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @ScalarFunction("to_date")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDate(@SqlType(StandardTypes.DATE) long date)
    {
        DateTime dt = new DateTime(DAYS.toMillis(date));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @ScalarFunction("to_date")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDateUnix(@SqlType(StandardTypes.TIMESTAMP) long timestamp)
    {
        DateTime dt = new DateTime(timestamp);
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff1(@SqlType("varchar(x)") Slice left, @SqlType("varchar(x)") Slice right)
    {
        DateTime dt1 = DateTimeUtils.parseDateTime(left.toStringUtf8());
        DateTime dt2 = DateTimeUtils.parseDateTime(right.toStringUtf8());
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff2(@SqlType(StandardTypes.DATE) long left, @SqlType(StandardTypes.DATE) long right)
    {
        DateTime dt1 = new DateTime(DAYS.toMillis(left));
        DateTime dt2 = new DateTime(DAYS.toMillis(right));
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff3(@SqlType(StandardTypes.TIMESTAMP) long left, @SqlType(StandardTypes.TIMESTAMP) long right)
    {
        DateTime dt1 = new DateTime(left);
        DateTime dt2 = new DateTime(right);
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff4(@SqlType("varchar(x)") Slice left, @SqlType(StandardTypes.DATE) long right)
    {
        DateTime dt1 = DateTimeUtils.parseDateTime(left.toStringUtf8());
        DateTime dt2 = new DateTime(DAYS.toMillis(right));
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff5(@SqlType(StandardTypes.DATE) long left, @SqlType("varchar(x)") Slice right)
    {
        DateTime dt1 = new DateTime(DAYS.toMillis(left));
        DateTime dt2 = DateTimeUtils.parseDateTime(right.toStringUtf8());
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff6(@SqlType("varchar(x)") Slice left, @SqlType(StandardTypes.TIMESTAMP) long right)
    {
        DateTime dt1 = DateTimeUtils.parseDateTime(left.toStringUtf8());
        DateTime dt2 = new DateTime(right);
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }

    @Description("difference of the given dates in the given unit")
    @ScalarFunction("datediff")
    @LiteralParameters("x")
    @SqlType(StandardTypes.BIGINT)
    public static long dateDiff7(@SqlType(StandardTypes.TIMESTAMP) long left, @SqlType("varchar(x)") Slice right)
    {
        DateTime dt1 = new DateTime(left);
        DateTime dt2 = DateTimeUtils.parseDateTime(right.toStringUtf8());
        return Math.abs(Days.daysBetween(dt1, dt2).getDays());
    }
}
