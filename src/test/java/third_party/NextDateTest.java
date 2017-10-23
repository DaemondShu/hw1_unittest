package third_party;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    private static JsonNode testData_getNextDataInfoTest;
    private static String[] lunarInfoMock=new String[]{"己丑年", "二月小", "初二", "牛"};
    @BeforeClass
    public static void InitTestData()
    {
        testData_validDateTest = IniTestData_validDataTest();
        testData_getNextDataInfoTest=IniTestData_getNextDateInfoTest();

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

    private static ObjectNode ConstructTestData(ObjectNode data,String index,ArrayNode out,ArrayNode in)
    {
        ObjectNode temp=newObjectNode();
        temp.set("out",out);
        temp.set("in",in);
        data.set(index,temp);
        return data;
    }
    private static JsonNode IniTestData_getNextDateInfoTest()
    {
        ObjectNode testdata=newObjectNode();
//        testdata.set("out",newArrayNode().add(NextDate.FAIL).add("输入的日期超出应用可用范围"));
//        testdata.set("in",newArrayNode().add(1800).add(1).add(1).add(1));
//        testdata.set("001",testdata);

        //1800年1月1日，日期超出范围，期望返回fail以及错误信息：输入的日期超出应用可用范围
        testdata=ConstructTestData(testdata,"001",
                newArrayNode().add(NextDate.FAIL).add("输入的日期超出应用可用范围"),
                newArrayNode().add(1800).add(1).add(1).add(1));

        //2000年8月40日，日期不存在，期望返回fail以及错误信息：输入的日期不存在
        testdata=ConstructTestData(testdata,"002",
                newArrayNode().add(NextDate.FAIL).add("输入的日期不存在"),
                newArrayNode().add(2000).add(8).add(40).add(1));

        //2100年12月31日，nextdate超出范围，返回NO_LUNAR_INFO以及桩公历信息
        testdata=ConstructTestData(testdata,"003",
                newArrayNode().add(NextDate.NO_LUNAR_INFO).add(2101).add(1).add(1)
                        .add(lunarInfoMock[0]).add(lunarInfoMock[1]).add(lunarInfoMock[2]).add(lunarInfoMock[3]),
                newArrayNode().add(2100).add(12).add(31).add(1));

        //计算前几天：2001年1月6日，计算10天前,_getBackNDateInfo中day<0,month=0
        testdata=ConstructTestData(testdata,"004",
                newArrayNode().add(NextDate.SUCCESS).add(2000).add(12).add(27)
                        .add(lunarInfoMock[0]).add(lunarInfoMock[1]).add(lunarInfoMock[2]).add(lunarInfoMock[3]),
                newArrayNode().add(2001).add(1).add(6).add(-10));

        //2001年1月5日，计算4天前，_getBackNDateInfo中day=0,month=0
        testdata=ConstructTestData(testdata,"005",
                newArrayNode().add(NextDate.SUCCESS).add(2001).add(1).add(1)
                        .add(lunarInfoMock[0]).add(lunarInfoMock[1]).add(lunarInfoMock[2]).add(lunarInfoMock[3]),
                newArrayNode().add(2001).add(1).add(5).add(-4));

        //计算后一天：2007年12月31日，n=1
        testdata=ConstructTestData(testdata,"006",
                newArrayNode().add(NextDate.SUCCESS).add(2008).add(1).add(1)
                        .add(lunarInfoMock[0]).add(lunarInfoMock[1]).add(lunarInfoMock[2]).add(lunarInfoMock[3]),
                newArrayNode().add(2007).add(12).add(31).add(1));

        //计算后几天：2007年12月1日，n=32
        testdata=ConstructTestData(testdata,"007",
                newArrayNode().add(NextDate.SUCCESS).add(2008).add(1).add(2)
                        .add(lunarInfoMock[0]).add(lunarInfoMock[1]).add(lunarInfoMock[2]).add(lunarInfoMock[3]),
                newArrayNode().add(2007).add(12).add(1).add(32));
        return testdata;
    }
    private void VerifyGetNextDateInfo(String index) throws Exception
    {
        NextDate nextDate=new NextDate();
        //        造木桩
        LunarUtil mockLunarUtil = mock(LunarUtil.class);
        when(mockLunarUtil.getLunarDateInfo(anyInt(),anyInt(),anyInt()))
                .thenReturn(lunarInfoMock);
        //        打桩
        nextDate.setLunarUtil(mockLunarUtil);
        JsonNode temp=testData_getNextDataInfoTest.get(index);
        //ArrayList<String> list=new ObjectMapper().readValue(temp.get("out").toString(),new TypeReference<ArrayList<String>>(){});

        assertEquals(new ObjectMapper().readValue(temp.get("out").toString(),new TypeReference<List<String>>(){}),
                nextDate.getNextDateInfo(temp.get("in").get(0).asInt(),temp.get("in").get(1).asInt(),temp.get("in").get(2).asInt(),temp.get("in").get(3).asInt()));

    }
    @Test public void GetNextDateInfoTest001() throws Exception { VerifyGetNextDateInfo("001"); }

    @Test public void GetNextDateInfoTest002() throws Exception { VerifyGetNextDateInfo("002"); }

    @Test public void GetNextDateInfoTest003() throws Exception { VerifyGetNextDateInfo("003"); }

    @Test public void GetNextDateInfoTest004() throws Exception { VerifyGetNextDateInfo("004"); }

    @Test public void GetNextDateInfoTest005() throws Exception { VerifyGetNextDateInfo("005"); }

    @Test public void GetNextDateInfoTest006() throws Exception { VerifyGetNextDateInfo("006"); }

    @Test public void GetNextDateInfoTest007() throws Exception { VerifyGetNextDateInfo("007"); }


}