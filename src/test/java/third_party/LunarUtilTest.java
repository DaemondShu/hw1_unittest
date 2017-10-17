package third_party;

import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

    /**
     * 没有闰月，7个29天的月份，5个30天的月份
     * 7*29+5*30=353
     * @throws Exception
     */
    @Test
    public void getDayNumTest001() throws Exception
    {
        int daynum = LunarUtil.getDayNum(LunarUtil.lunarInfo[65]);
        assertEquals(353, daynum);
    }


    /**
     * 没有闰月，6个29天的月份，6个30天的月份
     * 6*29+6*30=354
     * @throws Exception
     */
    @Test
    public void getDayNumTest002() throws Exception
    {
        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[1]);
        assertEquals(354,daynum);
    }



    /**
     * 没有闰月，5个29天的月份，7个30天的月份
     * 5*29+7*30=355
     * @throws Exception
     */
    @Test
    public void getDayNumTest003() throws Exception
    {
        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[2]);
        assertEquals(355,daynum);
    }

    /**
     * 有闰月，7个29天的月份，6个30天的月份
     * 6*29+7*30=383
     * @throws Exception
     */
    @Test
    public void getDayNumTest004() throws Exception
    {
        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[3]);
        assertEquals(383,daynum);
    }

    /**
     * 有闰月，6个29天的月份，7个30天的月份
     * 6*29+7*30=384
     * @throws Exception
     */
    @Test
    public void getDayNumTest005() throws Exception
    {
        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[0]);
        assertEquals(384,daynum);
    }

    /**
     * 有闰月，5个29天的月份，8个30天的月份
     * 5*29+8*30=385
     * @throws Exception
     */
    @Test
    public void getDayNumTest006() throws Exception
    {
        int daynum=LunarUtil.getDayNum(LunarUtil.lunarInfo[25]);
        assertEquals(385,daynum);
    }

    /**
     * 输入非法的16进制信息，期望返回-1
     * @throws Exception
     */
    @Test
    public void getDayNumTest007() throws Exception
    {
        int daynum=LunarUtil.getDayNum(0x00000);
        assertEquals(-1,daynum);
    }


}
