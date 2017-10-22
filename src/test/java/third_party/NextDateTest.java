package third_party;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NextDateTest
{
//    @Test
//    public void mockDemo() throws Exception
//    {
//        NextDate nextDate = new NextDate();
////        造木桩
//        LunarUtil mockLunarUtil = mock(LunarUtil.class);
//        when(mockLunarUtil.getLunarDateInfo(anyInt(),anyInt(),anyInt(),anyInt()))
//                .thenReturn(new String[]{"己丑年", "二月小小小小", "初二", "牛"});
//
//        System.out.println(mockLunarUtil.getLunarDateInfo(1,1,1,1).length);
//
////        打桩
//        nextDate.setLunarUtil(mockLunarUtil);
//        ArrayList<String> result = nextDate.getNextDateInfo(2009,2,25,1);
//        assertEquals("[1, 2009, 2, 26, 4, 己丑年, 二月大, 初二, 牛]", result.toString());
//
////        assertArrayEquals(Arrays.asList("1","2009","2","26","4","己丑年","二月大","初三","牛").toArray(), result.toArray());
//
//    }

    private static JsonNode testData_validDateTest;
    @BeforeClass
    public static void InitTestData()
    {
        testData_validDateTest = IniTestData_validDataTest();
    }
    private static ObjectNode newObjectNode()
    {
        return new ObjectMapper().createObjectNode();
    }
    private static ArrayNode newArrayNode()
    {
        return new ObjectMapper().createArrayNode();
    }
    private static JsonNode IniTestData_validDataTest()
    {


        ObjectNode testDataSet = new ObjectMapper().createObjectNode();

        //年份不符合要求，大于可用范围，期望返回错误码 errDateOutOfRange = 101
        testDataSet.set("001",
                newObjectNode().put("out",101).set("in",newArrayNode().add(2101).add(1).add(1)));

        //    年份不符合，小于可用范围， 期望返回错误码 errDateOutOfRange = 101
        testDataSet.set("002",
                newObjectNode().put("out",101).set("in",newArrayNode().add(1800).add(1).add(1)));

        //月份非法，小于1， 期望返回错误码 errNoSuchDate = 102
        testDataSet.set("003",
                newObjectNode().put("out",102).set("in",newArrayNode().add(2001).add(0).add(1)));

        //月份非法，大于13，期望返回错误码 errNoSuchDate = 102
        testDataSet.set("004",
                newObjectNode().put("out",102).set("in",newArrayNode().add(2001).add(13).add(1)));

        //日期非法，小于1， 期望返回错误码 errNoSuchDate = 102
        testDataSet.set("005",
                newObjectNode().put("out",102).set("in",newArrayNode().add(2007).add(4).add(0)));

        //日期非法，大于该月份的最大日期，非闰年的2月29日， 期望返回错误码 errNoSuchDate = 102
        testDataSet.set("006",
                newObjectNode().put("out",102).set("in",newArrayNode().add(1905).add(2).add(29)));

        //日期非法，大于该月份的最大日期，小月的31日，期望返回错误码 errNoSuchDate = 102
        testDataSet.set("007",
                newObjectNode().put("out",102).set("in",newArrayNode().add(2010).add(9).add(31)));

        //日期非法，大于该月份的最大日期，大月的32日，期望返回错误码 errNoSuchDate = 102
        testDataSet.set("008",
                newObjectNode().put("out",102).set("in",newArrayNode().add(2011).add(8).add(32)));

        //日期非法，大于该月份的最大日期，闰年的2月30日，期望返回错误码 errNoSuchDate = 102
        testDataSet.set("009",
                newObjectNode().put("out",102).set("in",newArrayNode().add(1904).add(2).add(30)));

        //日期合法，期望返回正确码 validSuccess = 100
        testDataSet.set("010",
                newObjectNode().put("out",100).set("in",newArrayNode().add(1968).add(8).add(10)));

        return testDataSet;
    }
    private void VerifyValidDateTest(String Item) throws Exception
    {
        JsonNode temp = testData_validDateTest.get(Item).get("in");
        int res=NextDate.validDate(temp.get(0).asInt(),temp.get(1).asInt(),temp.get(2).asInt());
        assertEquals(testData_validDateTest.get(Item).get("out").asInt(),res);
    }

    @Test public void validDateTest001() throws Exception { VerifyValidDateTest("001"); }

    @Test public void validDateTest002() throws Exception { VerifyValidDateTest("002"); }

    @Test public void validDateTest003() throws Exception { VerifyValidDateTest("003"); }

    @Test public void validDateTest004() throws Exception { VerifyValidDateTest("004"); }

    @Test public void validDateTest005() throws Exception { VerifyValidDateTest("005"); }

    @Test public void validDateTest006() throws Exception { VerifyValidDateTest("006"); }

    @Test public void validDateTest007() throws Exception { VerifyValidDateTest("007"); }

    @Test public void validDateTest008() throws Exception { VerifyValidDateTest("008"); }

    @Test public void validDateTest009() throws Exception { VerifyValidDateTest("009"); }

    @Test public void validDateTest010() throws Exception { VerifyValidDateTest("010"); }

//
//    /**
//     * 年份不符合要求，大于可用范围
//     * 期望返回错误码 errDateOutOfRange = 101
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest001() throws Exception
//    {
//        int error = NextDate.validDate(2101,1,1);
//        assertEquals(101,error);
//    }
//
//    /**
//     * 年份不符合，小于可用范围
//     * 期望返回错误码 errDateOutOfRange = 101
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest002() throws Exception
//    {
//        int error = NextDate.validDate(1800,1,1);
//        assertEquals(101,error);
//    }
//
//    /**
//     * 月份非法，小于1
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest003() throws Exception
//    {
//        int error = NextDate.validDate(2001,0,1);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 月份非法，大于13
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest004() throws Exception
//    {
//        int error = NextDate.validDate(2001,13,1);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 日期非法，小于1
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest005() throws Exception
//    {
//        int error = NextDate.validDate(2007,4,0);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 日期非法，大于该月份的最大日期
//     * 非闰年的2月29日
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest006() throws Exception
//    {
//        int error = NextDate.validDate(1905,2,29);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 日期非法，大于该月份的最大日期
//     * 小月的31日
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest007() throws Exception
//    {
//        int error = NextDate.validDate(2010,9,31);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 日期非法，大于该月份的最大日期
//     * 大月的32日
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest008() throws Exception
//    {
//        int error = NextDate.validDate(2011,8,32);
//        assertEquals(102,error);
//    }
//
//    /**
//     * 日期非法，大于该月份的最大日期
//     * 闰年的2月30日
//     * 期望返回错误码 errNoSuchDate = 102
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest009() throws Exception
//    {
//        int error = NextDate.validDate(1904,2,30);
//        assertEquals(102,error);
//    }
//
//
//    /**
//     * 日期合法
//     * 期望返回正确码 validSuccess = 100
//     * @throws Exception
//     */
//    @Test
//    public void validDateTest010() throws Exception
//    {
//        int error = NextDate.validDate(1968,8,10);
//        assertEquals(100,error);
//    }
//


}