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

import com.manbuyun.catalyst.CatalystPlugin;
import io.prestosql.operator.scalar.AbstractTestFunctions;
import io.prestosql.spi.type.TimeZoneKey;
import org.testng.annotations.BeforeClass;

import java.util.TimeZone;

import static io.prestosql.testing.TestingSession.testSessionBuilder;

/**
 * @author jinhai
 * @date 2020/06/08
 */
public class TestFunctionsBase
        extends AbstractTestFunctions
{
    protected TestFunctionsBase()
    {
        super(testSessionBuilder()
                .setTimeZoneKey(TimeZoneKey.getTimeZoneKey(TimeZone.getDefault().getID()))
                .build());
    }

    @BeforeClass
    public void setup()
    {
        CatalystPlugin plugin = new CatalystPlugin();
        registerFunctions(plugin);
    }
}
