package third_party;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;

import static helper.PairWiseHelper.DeleteByPairWise;
import static org.junit.Assert.assertEquals;

class LunarUtilTest_PairWise
{
//  因为getDayNumTest
// @ParameterizedTest
//    @ValueSource(ints = {})
//    void getDayNumTest(int value)
//    {
//        assertEquals(100, LunarUtil.getDayNum(value));
//        //输出重合在哪?
//    }


    @ParameterizedTest
    @MethodSource("dataProvider")
    void GetLunarDateInfoTest(JsonNode testCase) throws IOException
    {
        System.out.println(testCase.toString());
        LunarUtil lunarUtil = new LunarUtil();
        assertEquals(new ObjectMapper().readValue(testCase.get("out").toString(), new TypeReference<String[]>() {}),
                lunarUtil.getLunarDateInfo(
                        testCase.get("in").get(0).asInt(), testCase.get("in").get(1).asInt(), testCase.get("in").get(2).asInt()));
    }

    static Iterator<JsonNode> dataProvider()
    {
        JsonNode primaryTestEqCases = LunarUtilTest.IniTestData_GetLunarDateInfoTest();
        JsonNode primaryTestBoundaryCases = LunarUtilTest.IniTestData_GetLunarDateInfoBoundaryTest();


        List<JsonNode> primaryTestCases = new ArrayList<>();
        for (JsonNode ec : primaryTestEqCases)
        {
            primaryTestCases.add(ec);
        }

        for (JsonNode bc : primaryTestBoundaryCases)
        {
            primaryTestCases.add(bc);
        }

        return DeleteByPairWise(primaryTestCases, 3).iterator();
    }





}