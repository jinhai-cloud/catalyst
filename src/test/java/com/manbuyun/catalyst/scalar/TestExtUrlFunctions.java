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
public class TestExtUrlFunctions
        extends TestFunctionsBase
{
    @Test
    public void testUrl()
    {
        assertFunction("parse_url('http://facebook.com/path/hi.js?query=1', 'HOST')", createVarcharType(38), "facebook.com");
        assertFunction("parse_url('http://facebook.com/path/hi.js?query=1', 'PATH')", createVarcharType(38), "/path/hi.js");

        assertFunction("parse_url('http://facebook.com/path/hi.js?k1=v1&k2=v2#Ref', 'QUERY', 'k1')", createVarcharType(46), "v1");
        assertFunction("parse_url('http://facebook.com/path/hi.js?k1=v1&k2=v2#Ref', 'QUERY', 'k3')", createVarcharType(46), null);
        assertFunction("parse_url('http://facebook.com/path/hi.js', 'QUERY', 'k1')", createVarcharType(30), null);
        assertFunction("parse_url('http://facebook.com/path/hi.js?k1=v1&k2=v2#Ref', 'HOST', 'k1')", createVarcharType(46), null);
    }
}
