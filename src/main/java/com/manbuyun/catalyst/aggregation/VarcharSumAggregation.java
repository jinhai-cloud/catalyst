package com.manbuyun.catalyst.aggregation;

import com.manbuyun.catalyst.utils.SliceUtils;
import io.airlift.slice.Slice;
import io.prestosql.operator.aggregation.state.NullableDoubleState;
import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.function.AggregationFunction;
import io.prestosql.spi.function.AggregationState;
import io.prestosql.spi.function.CombineFunction;
import io.prestosql.spi.function.InputFunction;
import io.prestosql.spi.function.OutputFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.DoubleType;
import io.prestosql.spi.type.StandardTypes;

/**
 * @author jinhai
 * @date 2019/05/19
 */
@AggregationFunction("sum")
public final class VarcharSumAggregation {

    private VarcharSumAggregation() {
    }

    @InputFunction
    public static void sum(@AggregationState NullableDoubleState state, @SqlType(StandardTypes.VARCHAR) Slice slice) {
        state.setNull(false);
        state.setDouble(state.getDouble() + SliceUtils.toDouble(slice));
    }

    @CombineFunction
    public static void combine(@AggregationState NullableDoubleState state, @AggregationState NullableDoubleState otherState) {
        if (state.isNull()) {
            if (otherState.isNull()) {
                return;
            }
            state.setNull(false);
            state.setDouble(otherState.getDouble());
            return;
        }

        if (!otherState.isNull()) {
            state.setDouble(state.getDouble() + otherState.getDouble());
        }
    }

    @OutputFunction(StandardTypes.DOUBLE)
    public static void output(@AggregationState NullableDoubleState state, BlockBuilder out) {
        NullableDoubleState.write(DoubleType.DOUBLE, state, out);
    }
}