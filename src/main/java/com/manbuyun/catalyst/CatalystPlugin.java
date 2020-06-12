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
package com.manbuyun.catalyst;

import com.google.common.collect.ImmutableSet;
import com.manbuyun.catalyst.scalar.ExtCollectionFunctions;
import com.manbuyun.catalyst.scalar.ExtDateTimeFunctions;
import com.manbuyun.catalyst.scalar.ExtMathFunctions;
import com.manbuyun.catalyst.scalar.ExtStringFunctions;
import com.manbuyun.catalyst.scalar.ExtUrlFunctions;
import io.prestosql.spi.Plugin;

import java.util.Set;

/**
 * @author jinhai
 * @date 2019/05/19
 */
public class CatalystPlugin
        implements Plugin
{
    @Override
    public Set<Class<?>> getFunctions()
    {
        return ImmutableSet.<Class<?>>builder()
                .add(ExtDateTimeFunctions.class)
                .add(ExtUrlFunctions.class)
                .add(ExtStringFunctions.class)
                .add(ExtMathFunctions.class)
                .add(ExtCollectionFunctions.class)
                .build();
    }
}
