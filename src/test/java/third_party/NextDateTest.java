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
    @Test
    public void mockDemo() throws Exception
    {
        NextDate nextDate = new NextDate();
//        造木桩
        LunarUtil mockLunarUtil = mock(LunarUtil.class);
        when(mockLunarUtil.getLunarDateInfo(anyInt(),anyInt(),anyInt(),anyInt()))
                .thenReturn(new String[]{"己丑年", "二月小小小小", "初二", "牛"});

        System.out.println(mockLunarUtil.getLunarDateInfo(1,1,1,1).length);

//        打桩
        nextDate.setLunarUtil(mockLunarUtil);
        ArrayList<String> result = nextDate.getNextDateInfo(2009,2,25,1);
        assertEquals("[1, 2009, 2, 26, 4, 己丑年, 二月大, 初二, 牛]", result.toString());

//        assertArrayEquals(Arrays.asList("1","2009","2","26","4","己丑年","二月大","初三","牛").toArray(), result.toArray());

    }

}