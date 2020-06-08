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

import org.testng.annotations.Test;

import static io.prestosql.spi.type.IntegerType.INTEGER;
import static io.prestosql.spi.type.VarcharType.createVarcharType;

/**
 * @author jinhai
 * @date 2020/06/08
 */
public class TestExtStringFunctions
        extends TestFunctionsBase
{
    @Test
    public void testString()
    {
        assertFunction("md5(11)", createVarcharType(32), "6512bd43d9caa6e02c990b0a82652dca");
        assertFunction("md5('11')", createVarcharType(32), "6512bd43d9caa6e02c990b0a82652dca");
        assertFunction("md5(11.11)", createVarcharType(32), "7a1f37bc4a622f2fcb2e1c80be0df38e");
        assertFunction("md5('11.11')", createVarcharType(32), "7a1f37bc4a622f2fcb2e1c80be0df38e");

        assertFunction("locate('a', 'abcd')", INTEGER, 1);
        assertFunction("locate('f', 'abcd')", INTEGER, 0);
        assertFunction("locate('a', 'abcdabcd', 5)", INTEGER, 5);
        assertFunction("locate('a', 'abcdabcd', 6)", INTEGER, 0);
    }
}
