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

import io.prestosql.spi.block.Block;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.function.TypeParameter;
import io.prestosql.spi.type.StandardTypes;

/**
 * @author jinhai
 * @date 2020/06/12
 */
public class ExtCollectionFunctions
{
    private ExtCollectionFunctions() {}

    @ScalarFunction("size")
    @TypeParameter("E")
    @SqlType(StandardTypes.INTEGER)
    public static long arraySize(@SqlType("array(E)") Block block)
    {
        return block.getPositionCount();
    }

    @ScalarFunction("size")
    @TypeParameter("K")
    @TypeParameter("V")
    @SqlType(StandardTypes.INTEGER)
    public static long mapSize(@SqlType("map(K,V)") Block block)
    {
        return block.getPositionCount() / 2;
    }

    @ScalarFunction("size")
    @SqlType(StandardTypes.INTEGER)
    public static long nullSize(@SqlNullable @SqlType("unknown") Object value)
    {
        return -1;
    }
}
