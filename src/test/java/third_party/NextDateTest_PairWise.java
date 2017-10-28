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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NextDateTest_PairWise
{
    @ParameterizedTest
    @MethodSource("ValidDataTestCasesProvider")
    void ValidDateTest(JsonNode testCase) throws IOException
    {

        JsonNode temp = testCase.get("in");
        //System.out.println(testCase.toString());
        int res=NextDate.validDate(temp.get(0).asInt(),temp.get(1).asInt(),temp.get(2).asInt());
        assertEquals(testCase.get("out").asInt(),res);
    }

    static Iterator<JsonNode> ValidDataTestCasesProvider()
    {
        JsonNode primaryTestEqCases = NextDateTest.IniTestData_validDataTest();
        JsonNode primaryTestBoundaryCases = NextDateTest.IniTestData_validDataBoundaryTest();


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


    @ParameterizedTest
    @MethodSource("GetNextDateInfoTestCasesProvider")
    void GetNextDateInfoTest(JsonNode testCase) throws IOException
    {

        NextDate nextDate=new NextDate();
        //造木桩 模拟对象以及对象返回值
        LunarUtil mockLunarUtil = mock(LunarUtil.class);
        when(mockLunarUtil.getLunarDateInfo(anyInt(),anyInt(),anyInt())).thenReturn(new String[]{"己丑年", "二月小", "初二", "牛"});
        // 打桩
        nextDate.setLunarUtil(mockLunarUtil);
        JsonNode temp=testCase;
        //ArrayList<String> list=new ObjectMapper().readValue(temp.get("out").toString(),new TypeReference<ArrayList<String>>(){});
        assertEquals(new ObjectMapper().readValue(temp.get("out").toString(),new TypeReference<List<String>>(){}),
                nextDate.getNextDateInfo(temp.get("in").get(0).asInt(),temp.get("in").get(1).asInt(),temp.get("in").get(2).asInt(),temp.get("in").get(3).asInt()));

    }

    static Iterator<JsonNode> GetNextDateInfoTestCasesProvider()
    {
        JsonNode primaryTestEqCases = NextDateTest.IniTestData_getNextDateInfoTest();
        JsonNode primaryTestBoundaryCases = NextDateTest.IniTestData_getNextDateBoundaryInfoTest();


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
