package com.vod.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author vod
 * 
 */
public class DateUtil
{
    // UTC (0000-00-00T00:00:00)
    public static String date2DateTimeStr(Date date)
    {
        String dataStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
        return dataStr;
    }

    public static String before6HoursDateTimeStr(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.HOUR, -6);
        String dataStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(c.getTime());
        return dataStr;
    }

    public static Date before6HoursDate(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.HOUR, -6);
        return c.getTime();
    }

    public static Date before36HoursDate(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.HOUR, -36);
        return c.getTime();
    }

    public static String date2StrSimple(Date date)
    {
        String dataStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return dataStr;
    }

    public static String before10Day(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.DATE, -10);
        String dataStr = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dataStr;
    }

    public static Date getNextDate()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, 1);
        Date nextDate = calendar.getTime();

        return nextDate;
    }

    public static Date afterArgsMinDate(Date date, Long args)
    {
        Long timeInterval = date.getTime() + args * 1000 * 60;
        return new Date(timeInterval);
    }

    public static Date parseDateTime(String date) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(date);
    }

    public static String DateTimebefore10Day(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.DATE, -10);
        String dataStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(c.getTime());
        return dataStr;
    }

    public static Date DateTimeBeforeIntevalDays(Date date, Integer inteval)
    {
        Calendar c = Calendar.getInstance();
        if (null != date)
        {
            c.setTime(date);
        }
        c.add(Calendar.DAY_OF_YEAR, -inteval);
        return c.getTime();
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    public static void main(String[] args)
    {
        //System.out.println(getNextDate());
        
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
            Date d1 = sdf.parse("1970-01-01");  
            Date d2 = new Date();  
            System.out.println(daysBetween(d1,d2));
        }
        catch (Exception e){
            
        }
         
    }
}
