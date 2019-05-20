package com.manbuyun.catalyst.scalar;

import com.manbuyun.catalyst.utils.SliceUtils;
import io.airlift.slice.Slice;
import io.prestosql.spi.function.ScalarOperator;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;

import static io.prestosql.spi.function.OperatorType.*;
import static io.prestosql.spi.function.OperatorType.GREATER_THAN_OR_EQUAL;

/**
 * @author jinhai
 * @date 2019/05/20
 */
public class ExtIntegerOperators {

    private ExtIntegerOperators() {
    }

    @ScalarOperator(EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    @SqlNullable
    public static Boolean equal(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) == right;
    }

    @ScalarOperator(EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    @SqlNullable
    public static Boolean equal(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left == SliceUtils.toLong(right);
    }

    @ScalarOperator(NOT_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    @SqlNullable
    public static Boolean notEqual(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) != right;
    }

    @ScalarOperator(NOT_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    @SqlNullable
    public static Boolean notEqual(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left != SliceUtils.toLong(right);
    }

    @ScalarOperator(LESS_THAN)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean lessThan(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) < right;
    }

    @ScalarOperator(LESS_THAN)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean lessThan(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left < SliceUtils.toLong(right);
    }

    @ScalarOperator(LESS_THAN_OR_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean lessThanOrEqual(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) <= right;
    }

    @ScalarOperator(LESS_THAN_OR_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean lessThanOrEqual(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left <= SliceUtils.toLong(right);
    }

    @ScalarOperator(GREATER_THAN)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean greaterThan(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) > right;
    }

    @ScalarOperator(GREATER_THAN)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean greaterThan(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left > SliceUtils.toLong(right);
    }

    @ScalarOperator(GREATER_THAN_OR_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean greaterThanOrEqual(@SqlType(StandardTypes.VARCHAR) Slice left, @SqlType(StandardTypes.INTEGER) long right) {
        return SliceUtils.toLong(left) >= right;
    }

    @ScalarOperator(GREATER_THAN_OR_EQUAL)
    @SqlType(StandardTypes.BOOLEAN)
    public static boolean greaterThanOrEqual(@SqlType(StandardTypes.INTEGER) long left, @SqlType(StandardTypes.VARCHAR) Slice right) {
        return left >= SliceUtils.toLong(right);
    }
}