package third_party;

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

    /**
     * 年份不符合要求，大于可用范围
     * 期望返回错误码 errDateOutOfRange = 101
     * @throws Exception
     */
    @Test
    public void validDateTest001() throws Exception
    {
        int error = NextDate.validDate(2101,1,1);
        assertEquals(101,error);
    }

    /**
     * 年份不符合，小于可用范围
     * 期望返回错误码 errDateOutOfRange = 101
     * @throws Exception
     */
    @Test
    public void validDateTest002() throws Exception
    {
        int error = NextDate.validDate(1800,1,1);
        assertEquals(101,error);
    }

    /**
     * 月份非法，小于1
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest003() throws Exception
    {
        int error = NextDate.validDate(2001,0,1);
        assertEquals(102,error);
    }

    /**
     * 月份非法，大于13
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest004() throws Exception
    {
        int error = NextDate.validDate(2001,13,1);
        assertEquals(102,error);
    }

    /**
     * 日期非法，小于1
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest005() throws Exception
    {
        int error = NextDate.validDate(2007,4,0);
        assertEquals(102,error);
    }

    /**
     * 日期非法，大于该月份的最大日期
     * 非闰年的2月29日
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest006() throws Exception
    {
        int error = NextDate.validDate(1905,2,29);
        assertEquals(102,error);
    }

    /**
     * 日期非法，大于该月份的最大日期
     * 小月的31日
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest007() throws Exception
    {
        int error = NextDate.validDate(2010,9,31);
        assertEquals(102,error);
    }

    /**
     * 日期非法，大于该月份的最大日期
     * 大月的32日
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest008() throws Exception
    {
        int error = NextDate.validDate(2011,8,32);
        assertEquals(102,error);
    }

    /**
     * 日期非法，大于该月份的最大日期
     * 闰年的2月30日
     * 期望返回错误码 errNoSuchDate = 102
     * @throws Exception
     */
    @Test
    public void validDateTest009() throws Exception
    {
        int error = NextDate.validDate(1904,2,30);
        assertEquals(102,error);
    }


    /**
     * 日期合法
     * 期望返回正确码 validSuccess = 100
     * @throws Exception
     */
    @Test
    public void validDateTest010() throws Exception
    {
        int error = NextDate.validDate(1968,8,10);
        assertEquals(100,error);
    }



}