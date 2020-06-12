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

/**
 * @author jinhai
 * @date 2020/06/12
 */
public class TestExtCollectionFunctions
        extends TestFunctionsBase
{
    @Test
    public void testArraySize()
    {
        assertFunction("size(array[1,2,3])", INTEGER, 3);
        assertFunction("size(array[])", INTEGER, 0);
        assertFunction("size(null)", INTEGER, -1);
    }

    @Test
    public void testMapSize()
    {
        assertFunction("size(map(array['a','b','c'], array[1,2,3]))", INTEGER, 3);
    }
}
