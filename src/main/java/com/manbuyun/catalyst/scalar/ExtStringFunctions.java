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

import com.google.common.hash.Hashing;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import io.prestosql.spi.function.Description;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;

/**
 * @author jinhai
 * @date 2019/05/30
 */
public class ExtStringFunctions
{
    private ExtStringFunctions() {}

    @Description("compute md5 hash")
    @ScalarFunction
    @LiteralParameters("x")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice md5(@SqlType("varchar(x)") Slice slice)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(slice.getBytes()).toString());
    }

    @Description("compute md5 hash")
    @ScalarFunction
    @SqlType(StandardTypes.VARCHAR)
    public static Slice md5(@SqlType(StandardTypes.DOUBLE) double num)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(Double.toString(num).getBytes()).toString());
    }

    @Description("compute md5 hash")
    @ScalarFunction
    @SqlType(StandardTypes.VARCHAR)
    public static Slice md5(@SqlType(StandardTypes.INTEGER) long num)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(Long.toString(num).getBytes()).toString());
    }
}
