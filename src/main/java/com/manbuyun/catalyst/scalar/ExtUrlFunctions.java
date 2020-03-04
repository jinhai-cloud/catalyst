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

import io.airlift.slice.Slice;
import io.prestosql.spi.function.Description;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;

import javax.annotation.Nullable;

import java.net.URL;

import static io.airlift.slice.Slices.utf8Slice;

/**
 * @author jinhai
 * @date 2019/05/20
 */
public class ExtUrlFunctions
{
    private ExtUrlFunctions() {}

    @SqlNullable
    @ScalarFunction("parse_url")
    @Description("Extracts a part from a URL")
    @LiteralParameters({"x", "y"})
    @SqlType("varchar(x)")
    public static Slice parseUrl(@SqlType("varchar(x)") Slice urlStr, @SqlType("varchar(y)") Slice partToExtract)
    {
        URL url = parseUrl(urlStr);
        if (url == null || partToExtract.length() == 0) {
            return null;
        }

        String part = partToExtract.toStringUtf8();
        String result = null;
        switch (part) {
            case "HOST":
                result = url.getHost();
                break;
            case "PATH":
                result = url.getPath();
                break;
            case "QUERY":
                result = url.getQuery();
                break;
            case "REF":
                result = url.getRef();
                break;
            case "PROTOCOL":
                result = url.getProtocol();
                break;
            case "FILE":
                result = url.getFile();
                break;
            case "AUTHORITY":
                result = url.getAuthority();
                break;
            case "USERINFO":
                result = url.getUserInfo();
                break;
            default:
                break;
        }

        return result != null ? utf8Slice(result) : null;
    }

    @Nullable
    private static URL parseUrl(Slice url)
    {
        try {
            return new URL(url.toStringUtf8());
        }
        catch (Exception e) {
            return null;
        }
    }
}
