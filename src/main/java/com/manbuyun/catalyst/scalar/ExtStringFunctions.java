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
import com.google.common.primitives.Ints;
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
    @SqlType("varchar(32)")
    public static Slice md5(@SqlType("varchar(x)") Slice slice)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(slice.getBytes()).toString());
    }

    @Description("compute md5 hash")
    @ScalarFunction
    @SqlType("varchar(32)")
    public static Slice md5(@SqlType(StandardTypes.DOUBLE) double num)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(Double.toString(num).getBytes()).toString());
    }

    @Description("compute md5 hash")
    @ScalarFunction
    @SqlType("varchar(32)")
    public static Slice md5(@SqlType(StandardTypes.BIGINT) long num)
    {
        return Slices.utf8Slice(Hashing.md5().hashBytes(Long.toString(num).getBytes()).toString());
    }

    @Description("returns index of first occurrence of a substring (or 0 if not found)")
    @ScalarFunction
    @LiteralParameters({"x", "y"})
    @SqlType(StandardTypes.BIGINT)
    public static long locate(@SqlType("varchar(x)") Slice substring, @SqlType("varchar(y)") Slice string)
    {
        return findString(string.toStringUtf8(), substring.toStringUtf8(), 0) + 1;
    }

    @Description("returns index of first occurrence of a substring (or 0 if not found)")
    @ScalarFunction
    @LiteralParameters({"x", "y"})
    @SqlType(StandardTypes.BIGINT)
    public static long locate(@SqlType("varchar(x)") Slice substring, @SqlType("varchar(y)") Slice string, @SqlType(StandardTypes.BIGINT) long start)
    {
        return findString(string.toStringUtf8(), substring.toStringUtf8(), Ints.saturatedCast(start) - 1) + 1;
    }

    public static int findString(String string, String substring, int start)
    {
        int length = string.length() - start;
        if (start < 0 || length <= 0 || length < substring.length()) {
            return -1;
        }
        if (substring.length() == 0) {
            return 0;
        }

        int index = string.indexOf(substring, start);
        if (index == -1) {
            return index;
        }
        else {
            return string.codePointCount(0, index);
        }
    }
}
