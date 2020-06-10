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

import static io.prestosql.spi.type.BigintType.BIGINT;
import static io.prestosql.spi.type.IntegerType.INTEGER;
import static io.prestosql.spi.type.VarcharType.createVarcharType;

/**
 * @author jinhai
 * @date 2020/06/08
 */
public class TestExtDateTimeFunctions
        extends TestFunctionsBase
{
    @Test
    public void testDateAdd()
    {
        assertFunction("date_add('2020-06-18 10:30', 3)", createVarcharType(10), "2020-06-21");
        assertFunction("date_add('2020-06-18T10:30:15', 3)", createVarcharType(10), "2020-06-21");
        assertFunction("date_add('2020-06-18X10:30:15', 3)", createVarcharType(10), "2020-06-21");
        assertFunction("date_add('2020-06-18 X10:30:15', 3)", createVarcharType(10), "2020-06-21");

        assertFunction("date_add(current_timestamp, 3)", createVarcharType(10), LocalDate.now().plusDays(3).toString());
        assertFunction("date_add(current_date, 3)", createVarcharType(10), LocalDate.now().plusDays(3).toString());
        assertFunction("date_add(TIMESTAMP '2020-06-18 10:30:15', 3)", createVarcharType(10), "2020-06-21");
        assertFunction("date_add(DATE '2020-06-18', 3)", createVarcharType(10), "2020-06-21");
    }

    @Test
    public void testDateSub()
    {
        assertFunction("date_sub('2020-06-18 10:30', 3)", createVarcharType(10), "2020-06-15");
        assertFunction("date_sub('2020-06-18T10:30:15', 3)", createVarcharType(10), "2020-06-15");
        assertFunction("date_sub('2020-06-18X10:30:15', 3)", createVarcharType(10), "2020-06-15");
        assertFunction("date_sub('2020-06-18 X10:30:15', 3)", createVarcharType(10), "2020-06-15");

        assertFunction("date_sub(current_timestamp, 3)", createVarcharType(10), LocalDate.now().minusDays(3).toString());
        assertFunction("date_sub(current_date, 3)", createVarcharType(10), LocalDate.now().minusDays(3).toString());
        assertFunction("date_sub(TIMESTAMP '2020-06-18 10:30:15', 3)", createVarcharType(10), "2020-06-15");
        assertFunction("date_sub(DATE '2020-06-18', 3)", createVarcharType(10), "2020-06-15");
    }

    @Test
    public void testToDate()
    {
        assertFunction("to_date('2020-06-18 10:30')", createVarcharType(10), "2020-06-18");
        assertFunction("to_date('2020-06-18T10:30:15')", createVarcharType(10), "2020-06-18");
        assertFunction("to_date('2020-06-18X10:30:15')", createVarcharType(10), "2020-06-18");
        assertFunction("to_date('2020-06-18 X10:30:15')", createVarcharType(10), "2020-06-18");

        assertFunction("to_date(current_timestamp)", createVarcharType(10), LocalDate.now().toString());
        assertFunction("to_date(current_date)", createVarcharType(10), LocalDate.now().toString());
        assertFunction("to_date(TIMESTAMP '2020-06-18 10:30:15')", createVarcharType(10), "2020-06-18");
        assertFunction("to_date(DATE '2020-06-18')", createVarcharType(10), "2020-06-18");
    }

    @Test
    public void testDateDiff()
    {
        assertFunction("datediff('2020-06-18 10:30', '2020-06-15 10:30:15')", INTEGER, 3);
        assertFunction("datediff(DATE '2020-06-18', DATE '2020-06-15')", INTEGER, 3);
        assertFunction("datediff(TIMESTAMP '2020-06-18 10:30', TIMESTAMP '2020-06-15 10:30:15')", INTEGER, 3);

        assertFunction("datediff('2020-06-18 10:30', DATE '2020-06-15')", INTEGER, 3);
        assertFunction("datediff(DATE '2020-06-15', '2020-06-18 10:30')", INTEGER, -3);
        assertFunction("datediff('2020-06-18 10:30', TIMESTAMP '2020-06-15 10:30')", INTEGER, 3);
        assertFunction("datediff(TIMESTAMP '2020-06-15 10:30', '2020-06-18 10:30')", INTEGER, -3);
    }

    @Test
    public void testFromUnixTime()
    {
        assertFunction("from_unixtime(1592447415)", createVarcharType(19), "2020-06-18 10:30:15");
        assertFunction("from_unixtime(1592447415, 'yyyy-MM-dd HH')", createVarcharType(13), "2020-06-18 10");
    }

    @Test
    public void testUnixTimestamp()
    {
        assertFunction("unix_timestamp('2020-06-18 10','yyyy-MM-dd HH:mm')", BIGINT, null);
        assertFunction("unix_timestamp('2020-06-18 10:30','yyyy-MM-dd HH')", BIGINT, 1592445600L);
        assertFunction("unix_timestamp('2020-06-18 10:30','yyyy-MM-dd HH:mm')", BIGINT, 1592447400L);
        assertFunction("unix_timestamp('2020-06-18 10:30')", BIGINT, null);
        assertFunction("unix_timestamp('2020-06-18 10:30:15')", BIGINT, 1592447415L);
        assertFunction("unix_timestamp('2020-06-18 X10:30:15')", BIGINT, null);
        assertFunction("unix_timestamp('2020-06-18T10:30:15')", BIGINT, null);
        assertFunction("unix_timestamp('2020-06-18 10:30:15.345')", BIGINT, 1592447415L);

        assertFunction("unix_timestamp(timestamp '2020-06-18 10:30:15', 'yyyy-MM-dd')", BIGINT, 1592447415L);
        assertFunction("unix_timestamp(DATE '2020-06-18')", BIGINT, 1592409600L);
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

    @Test
    public void testWeekOfYear()
    {
        assertFunction("weekofyear('2020-06-18 10:30:15')", INTEGER, 25);
        assertFunction("weekofyear('2020-06-18T10:30:15')", INTEGER, 25);
        assertFunction("weekofyear('2020-06-18X10:30:15')", INTEGER, null);
        assertFunction("weekofyear('2020-06-18xxx')", INTEGER, null);
    }
}
