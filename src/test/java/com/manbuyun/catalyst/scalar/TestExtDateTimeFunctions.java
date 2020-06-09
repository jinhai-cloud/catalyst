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

import java.time.LocalDate;

import static io.prestosql.spi.type.VarcharType.createVarcharType;
import static java.lang.String.format;

/**
 * @author jinhai
 * @date 2020/06/08
 */
public class TestExtDateTimeFunctions
        extends TestFunctionsBase
{
    private static final String dt1 = "2020-06-08";
    private static final String dt2 = "2020-06-08 10";
    private static final String dt3 = "2020-06-08 10:30";
    private static final String dt4 = "2020-06-08 10:30:15";
    private static final String dt5 = "2020-06-08T10:30:15";
    private static final String dt6 = "2020-06-08X10:30:15";
    private static final String dt7 = "2020-06-08 X10:30:15";
    private static final String dt8 = "2020-06-08 10:30:15.258";
    private static final String dt9 = "2020-06-08T10:30:15.258";
    private static final String dt10 = "2020-06-08T10:30:15258";
    private static final String dt11 = "2020-06-08T10:30:15.258+02:00";

    private static final String dt20 = "TIMESTAMP '2020-06-08 10:30:15'";
    private static final String dt21 = "DATE '2020-06-08'";

    @Test
    public void testDateAdd()
    {
        assertFunction(format("date_add('%s', 3)", dt3), createVarcharType(10), "2020-06-11");
        assertFunction(format("date_add('%s', 3)", dt5), createVarcharType(10), "2020-06-11");
        assertFunction(format("date_add('%s', 3)", dt6), createVarcharType(10), "2020-06-11");
        assertFunction(format("date_add('%s', 3)", dt7), createVarcharType(10), "2020-06-11");

        assertFunction("date_add(current_timestamp, 3)", createVarcharType(10), LocalDate.now().plusDays(3).toString());
        assertFunction("date_add(current_date, 3)", createVarcharType(10), LocalDate.now().plusDays(3).toString());
        assertFunction(format("date_add(%s, 3)", dt20), createVarcharType(10), "2020-06-11");
        assertFunction(format("date_add(%s, 3)", dt21), createVarcharType(10), "2020-06-11");
    }

    @Test
    public void testDateFormat()
    {
        assertFunction("date_format('10:30:15', 'yyyy-MM-dd HH:mm:ss')", createVarcharType(19), null);
        assertFunction("date_format('2020-06-18 10:30', 'yyyy-MM-dd HH')", createVarcharType(13), "2020-06-18 10");
        assertFunction("date_format('2020-06-18 10:30', 'yyyy-MM-dd HH:mm')", createVarcharType(16), "2020-06-18 10:30");
        assertFunction("date_format('2020-06-18 10:30:15', 'yyyy-MM-dd HH:mm')", createVarcharType(16), "2020-06-18 10:30");
        assertFunction("date_format('2020-06-18 10:30:15', 'yyyy-MM-dd HH:mm:00')", createVarcharType(19), "2020-06-18 10:30:00");
        assertFunction("date_format('2020-06-18 10:30:15', 'yyyy-MM-dd HH:mm:ss')", createVarcharType(19), "2020-06-18 10:30:15");
        assertFunction("date_format('2020-06-18 10:30:15.345', 'yyyy-MM-dd HH:mm:ss')", createVarcharType(19), "2020-06-18 10:30:15");

        assertFunction("date_format('2020-06-18T10:30:15.345+03:00', 'yyyy-MM-dd HH:mm:00')", createVarcharType(19), "2020-06-18 15:30:00");
        assertFunction("date_format('2020-06-18T10:30:15', 'yyyy-MM-dd HH:mm')", createVarcharType(16), "2020-06-18 10:30");
        assertFunction("date_format('2020-06-18X10:30:15', 'yyyy-MM-dd HH:mm')", createVarcharType(16), null);
        assertFunction("date_format('2020-06-18T10:30:1534', 'yyyy-MM-dd HH:mm')", createVarcharType(16), null);
        assertFunction("date_format('2020-06-18T10:30:15.345', 'yyyy-MM-dd HH:mm:ss')", createVarcharType(19), "2020-06-18 10:30:15");
    }
}
