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

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Doubles;
import io.airlift.slice.Slice;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;

import java.math.RoundingMode;

/**
 * @author jinhai
 * @date 2019/05/30
 */
public class ExtMathFunctions
{
    private ExtMathFunctions() {}

    @ScalarFunction("int")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long toInt(@SqlType(StandardTypes.DOUBLE) double num)
    {
        try {
            return (long) DoubleMath.roundToInt(num, RoundingMode.DOWN);
        }
        catch (ArithmeticException e) {
            return null;
        }
    }

    @ScalarFunction("int")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.INTEGER)
    public static Long toInt(@SqlType("varchar(x)") Slice slice)
    {
        Double num = Doubles.tryParse(slice.toStringUtf8());
        if (num != null) {
            try {
                return (long) DoubleMath.roundToInt(num, RoundingMode.DOWN);
            }
            catch (ArithmeticException e) {
                // ignore
            }
        }
        return null;
    }
}
