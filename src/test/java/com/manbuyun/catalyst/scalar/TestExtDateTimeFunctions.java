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

import static io.prestosql.spi.type.VarcharType.createVarcharType;

/**
 * @author jinhai
 * @date 2020/06/08
 */
public class TestExtDateTimeFunctions
        extends TestFunctionsBase
{
    @Test
    public void testDateTime()
    {
        assertFunction("date_sub('2019-05-20 10:01', 3)", createVarcharType(10), "2019-05-17");
    }
}
