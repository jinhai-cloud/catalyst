package com.manbuyun.catalyst;

import com.google.common.collect.ImmutableSet;
import com.manbuyun.catalyst.aggregation.VarcharSumAggregation;
import com.manbuyun.catalyst.scalar.ExtBigintOperators;
import com.manbuyun.catalyst.scalar.ExtDateTimeFunctions;
import com.manbuyun.catalyst.scalar.ExtIntegerOperators;
import com.manbuyun.catalyst.scalar.ExtJsonFunctions;
import com.manbuyun.catalyst.scalar.ExtUrlFunctions;
import io.prestosql.spi.Plugin;

import java.util.Set;

/**
 * @author jinhai
 * @date 2019/05/19
 */
public class CatalystPlugin implements Plugin {

    @Override
    public Set<Class<?>> getFunctions() {
        return ImmutableSet.<Class<?>>builder()
                .add(VarcharSumAggregation.class)
                .add(ExtDateTimeFunctions.class)
                .add(ExtBigintOperators.class)
                .add(ExtIntegerOperators.class)
                .add(ExtJsonFunctions.class)
                .add(ExtUrlFunctions.class)
                .build();
    }
}