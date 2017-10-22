package third_party;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @BeforeClass
    public static void InitTestData()
    {
         testData_GetDayNumTest = IniTestData_GetDayNumTest();
    }

    private static ObjectNode newObjectNode()
    {
        return new ObjectMapper().createObjectNode();
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

    /**
     * common test method for GetDayNum
     */
    private void VerifyGetDayNumTest(String Item) throws Exception
    {
        JsonNode temp = testData_GetDayNumTest.get(Item).get("");
        assertEquals(temp.get("out").asInt(), LunarUtil.getDayNum(temp.get("in").asInt()));
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







}
