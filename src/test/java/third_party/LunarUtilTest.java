package third_party;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by SunMeng on 2017/10/15.
 */
public class LunarUtilTest {

//    @Test
//    public void getDayNumTest() throws Exception
//    {
//        LunarUtil lunarUtil=new LunarUtil();
//        Set<Integer> dayList=new HashSet<>();
//        int size=dayList.size();
//        for(int i=LunarUtil.yearBase;i<=LunarUtil.yearUpper;i++)
//        {
//            int res = LunarUtil.getDayNum(LunarUtil.lunarInfo[i-1900]);
//            if(res==382) System.out.print("found");
//            dayList.add(res);
//            if(dayList.size()>size)
//            {
//                System.out.println(res+" : "+i);
//                size=dayList.size();
//
//            }
//        }
//        System.out.println(dayList.size());
//        //for(int i=0;i<dayList.size();i++)
//            System.out.println(dayList);
//
//    }


    private static JsonNode testData_GetDayNumTest;
    private static JsonNode testData_GetLunarDateInfoTest;
    @BeforeClass
    public static void InitTestData()
    {
        testData_GetDayNumTest = IniTestData_GetDayNumTest();
        testData_GetLunarDateInfoTest=IniTestData_GetLunarDateInfoTest();

    }

    private static ObjectNode newObjectNode()
    {
        return new ObjectMapper().createObjectNode();
    }

    private static ArrayNode newArrayNode()
    {
        return new ObjectMapper().createArrayNode();
    }
    private static JsonNode IniTestData_GetDayNumTest()
    {
        ObjectNode testDataSet = new ObjectMapper().createObjectNode();
        testDataSet.set("001",   //没有闰月，7个29天的月份，5个30天的月份 7*29+5*30=353
                newObjectNode().put("in", LunarUtil.lunarInfo[65]).put("out", 353));
        testDataSet.set("002",   //没有闰月，6个29天的月份，6个30天的月份 6*29+6*30=354
                newObjectNode().put("in", LunarUtil.lunarInfo[1]).put("out", 354));
        testDataSet.set("003",   //没有闰月，5个29天的月份，7个30天的月份 5*29+7*30=355
                newObjectNode().put("in", LunarUtil.lunarInfo[2]).put("out", 355));
        testDataSet.set("004",   //有闰月，7个29天的月份，6个30天的月份 7*29+6*30=383
                newObjectNode().put("in", LunarUtil.lunarInfo[3]).put("out", 383));
        testDataSet.set("005",   //有闰月，6个29天的月份，7个30天的月份 6*29+7*30=384
                newObjectNode().put("in", LunarUtil.lunarInfo[0]).put("out", 384));
        testDataSet.set("006",   //有闰月，5个29天的月份，8个30天的月份 5*29+8*30=385
                newObjectNode().put("in", LunarUtil.lunarInfo[25]).put("out", 385));
        testDataSet.set("007",   //输入非法的16进制信息，期望返回-1
                newObjectNode().put("in", 0x00000).put("out", -1));

        return testDataSet;

    }
    private static ObjectNode ConstructTestData(ObjectNode data, String index, ArrayNode out, ArrayNode in)
    {
        ObjectNode temp=newObjectNode();
        temp.set("out",out);
        temp.set("in",in);
        data.set(index,temp);
        return data;
    }
    private static JsonNode IniTestData_GetLunarDateInfoTest()
    {
        ObjectNode testdata=newObjectNode();
        //年份小于应用范围
        testdata=ConstructTestData(testdata,"001",
                newArrayNode().add("").add("").add("").add(""),
                newArrayNode().add(1800).add(1).add(1));
        //年份大于应用范围
        testdata=ConstructTestData(testdata,"002",
                newArrayNode().add("").add("").add("").add(""),
                newArrayNode().add(2200).add(1).add(1));

        //月份不合法，期望输出["","","",""]，但是输出["庚子年","闰八月小","廿六","鼠"]
        testdata=ConstructTestData(testdata,"003",
                newArrayNode().add("").add("").add("").add(""),
                newArrayNode().add(1901).add(-2).add(20));

        //日期不合法，期望输出["","","",""]，但是输出["庚辰年","六月小","廿九","龙"]
        testdata=ConstructTestData(testdata,"004",
                newArrayNode().add("").add("").add("").add(""),
                newArrayNode().add(2000).add(6).add(60));

        //正常情况:total<30
        testdata=ConstructTestData(testdata,"005",
                newArrayNode().add("己亥年").add("腊月大").add("十九").add("猪"),
                newArrayNode().add(1900).add(1).add(20));

        //正常情况:total>30,total < daysInYear
        testdata=ConstructTestData(testdata,"006",
                newArrayNode().add("庚子年").add("闰八月小").add("廿六").add("鼠"),
                newArrayNode().add(1900).add(10).add(20));

        //正常情况:total>30,total > daysInYear,total> monthDays,quotient=0
        testdata=ConstructTestData(testdata,"007",
                newArrayNode().add("庚辰年").add("四月小").add("初七").add("龙"),
                newArrayNode().add(2000).add(5).add(10));

        //正常情况:total>30,total < daysInYear,remainder=9
        testdata=ConstructTestData(testdata,"008",
                newArrayNode().add("戊辰年").add("九月小").add("二十").add("龙"),
                newArrayNode().add(1988).add(10).add(30));

        return testdata;
    }

    /**
     * common test method for GetDayNum
     */
    private void VerifyGetDayNumTest(String Item) throws Exception
    {
        JsonNode temp = testData_GetDayNumTest.get(Item);
        assertEquals(temp.get("out").asInt(), LunarUtil.getDayNum(temp.get("in").asInt()));
    }
    private void VerifyGetLunarDateInfoTest(String index) throws Exception
    {
        LunarUtil lunarUtil=new LunarUtil();
        JsonNode temp=testData_GetLunarDateInfoTest.get(index);
        assertEquals(new ObjectMapper().readValue(temp.get("out").toString(),new TypeReference<String[]>(){}),
                lunarUtil.getLunarDateInfo(
                temp.get("in").get(0).asInt(),temp.get("in").get(1).asInt(),temp.get("in").get(2).asInt()));
    }
//    /**
//     * 没有闰月，7个29天的月份，5个30天的月份
//     * 7*29+5*30=353
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest001() throws Exception
//    {
//        int daynum = LunarUtil.getDayNum(LunarUtil.lunarInfo[65]);
//        assertEquals(353, daynum);
//    }
//
//
//    /**
//     * 没有闰月，6个29天的月份，6个30天的月份
//     * 6*29+6*30=354
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest002() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[1]);
//        assertEquals(354,daynum);
//    }
//
//
//
//    /**
//     * 没有闰月，5个29天的月份，7个30天的月份
//     * 5*29+7*30=355
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest003() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[2]);
//        assertEquals(355,daynum);
//    }
//
//    /**
//     * 有闰月，7个29天的月份，6个30天的月份
//     * 7*29+6*30=383
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest004() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[3]);
//        assertEquals(383,daynum);
//    }
//
//    /**
//     * 有闰月，6个29天的月份，7个30天的月份
//     * 6*29+7*30=384
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest005() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[0]);
//        assertEquals(384,daynum);
//    }
//
//    /**
//     * 有闰月，5个29天的月份，8个30天的月份
//     * 5*29+8*30=385
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest006() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[25]);
//        assertEquals(385,daynum);
//    }
//
//    /**
//     * 输入非法的16进制信息，期望返回-1
//     * @throws Exception
//     */
//    @Test
//    public void getDayNumTest007() throws Exception
//    {
//        int daynum=LunarUtil.getDayNum(0x00000);
//        assertEquals(-1,daynum);
//    }

    @Test public void getDayNumTest001() throws Exception { VerifyGetDayNumTest("001"); }

    @Test public void getDayNumTest002() throws Exception { VerifyGetDayNumTest("002"); }

    @Test public void getDayNumTest003() throws Exception { VerifyGetDayNumTest("003"); }

    @Test public void getDayNumTest004() throws Exception { VerifyGetDayNumTest("004"); }

    @Test public void getDayNumTest005() throws Exception { VerifyGetDayNumTest("005"); }

    @Test public void getDayNumTest006() throws Exception { VerifyGetDayNumTest("006"); }

    @Test public void getDayNumTest007() throws Exception { VerifyGetDayNumTest("007"); }

    @Test public void getLunarDateInfoTest001() throws Exception { VerifyGetLunarDateInfoTest("001"); }

    @Test public void getLunarDateInfoTest002() throws Exception { VerifyGetLunarDateInfoTest("002"); }

    @Test public void getLunarDateInfoTest003() throws Exception { VerifyGetLunarDateInfoTest("003"); }

    @Test public void getLunarDateInfoTest004() throws Exception { VerifyGetLunarDateInfoTest("004"); }

    @Test public void getLunarDateInfoTest005() throws Exception { VerifyGetLunarDateInfoTest("005"); }

    @Test public void getLunarDateInfoTest006() throws Exception { VerifyGetLunarDateInfoTest("006"); }

    @Test public void getLunarDateInfoTest007() throws Exception { VerifyGetLunarDateInfoTest("007"); }

    @Test public void getLunarDateInfoTest008() throws Exception { VerifyGetLunarDateInfoTest("008"); }






}
