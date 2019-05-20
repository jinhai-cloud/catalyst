package com.manbuyun.catalyst.scalar;

import io.airlift.slice.Slice;
import io.prestosql.operator.scalar.JsonExtract;
import io.prestosql.operator.scalar.JsonPath;
import io.prestosql.spi.function.LiteralParameters;
import io.prestosql.spi.function.ScalarFunction;
import io.prestosql.spi.function.SqlNullable;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import io.prestosql.type.JsonPathType;

/**
 * @author jinhai
 * @date 2019/05/20
 */
public class ExtJsonFunctions {

    private ExtJsonFunctions() {
    }

    @ScalarFunction("get_json_object")
    @LiteralParameters("x")
    @SqlNullable
    @SqlType(StandardTypes.JSON)
    public static Slice varcharJsonExtract(@SqlType("varchar(x)") Slice json, @SqlType(JsonPathType.NAME) JsonPath jsonPath) {
        return JsonExtract.extract(json, jsonPath.getObjectExtractor());
    }
}