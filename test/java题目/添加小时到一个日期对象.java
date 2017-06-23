package java题目;

import java.util.Date;

public class 添加小时到一个日期对象
{
    public static void main(String[] args)
    {
        Date date = new Date();

        //Increase time by 6 hrs
        date.setTime(date.getTime() + 6 * 60 * 60 * 1000);
        System.out.println(date);

        //Decrease time by 6 hrs
        date = new Date();
        date.setTime(date.getTime() - 6 * 60 * 60 * 1000);
        System.out.println(date);
    }
}
