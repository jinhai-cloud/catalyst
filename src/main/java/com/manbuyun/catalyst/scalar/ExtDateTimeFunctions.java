package com.manbuyun.catalyst.scalar;

import com.manbuyun.catalyst.utils.DateTimeUtils;
import io.airlift.slice.Slice;
import io.prestosql.spi.function.Description;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import org.joda.time.DateTime;

import static io.airlift.slice.Slices.utf8Slice;
import static java.lang.Math.toIntExact;
import static java.util.concurrent.TimeUnit.DAYS;

/**
 * @author jinhai
 * @date 2019/05/19
 */
public class ExtDateTimeFunctions {

    private ExtDateTimeFunctions() {
    }

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    @Description("add the specified amount of time to the given time")
    @LiteralParameters("x")
    @ScalarFunction("date_add")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateAddStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value) {
        DateTime dt1 = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        DateTime dt2 = dt1.plusDays(toIntExact(value));
        return utf8Slice(dt2.toString(YYYY_MM_DD));
    }

    @Description("add the specified amount of time to the given time")
    @ScalarFunction("date_add")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateAdd(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value) {
        DateTime dt = new DateTime(DAYS.toMillis(date)).plusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @Description("sub the specified amount of time to the given time")
    @LiteralParameters("x")
    @ScalarFunction("date_sub")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateSubStr(@SqlType("varchar(x)") Slice slice, @SqlType(StandardTypes.BIGINT) long value) {
        DateTime dt1 = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        DateTime dt2 = dt1.minusDays(toIntExact(value));
        return utf8Slice(dt2.toString(YYYY_MM_DD));
    }

    @Description("sub the specified amount of time to the given time")
    @ScalarFunction("date_sub")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice dateSub(@SqlType(StandardTypes.DATE) long date, @SqlType(StandardTypes.BIGINT) long value) {
        DateTime dt = new DateTime(DAYS.toMillis(date)).minusDays(toIntExact(value));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }

    @ScalarFunction("to_date")
    @LiteralParameters("x")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDateStr(@SqlType("varchar(x)") Slice slice) {
        DateTime dt1 = DateTimeUtils.parseDateTime(slice.toStringUtf8());
        return utf8Slice(dt1.toString(YYYY_MM_DD));
    }

    @ScalarFunction("to_date")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDate(@SqlType(StandardTypes.DATE) long date) {
        DateTime dt = new DateTime(DAYS.toMillis(date));
        return utf8Slice(dt.toString(YYYY_MM_DD));
    }
}