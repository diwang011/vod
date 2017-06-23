package com.vod.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

import com.vod.common.Constant;

public final class TimeFormatterUtil
{
    public static Date convertToDateTime(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : Constant.DateTimeFormat.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
        return res;
    }

    public static Date convertToENUSDateTime(String date)
    {
        Date res=null;
        try
        {
            res = (date == null || date.length() == 0) ? null : Constant.DateTimeENUSFormat.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
        return res;
    }

    public static Date convertToDate(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : Constant.DateFormat.parse(date);
        }
        catch (Exception e)
        {
            return null;
        }
        return res;
    }

    public static Date convertToSelectDate(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : Constant.DateFormatSelect.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
        return res;
    }

    public static Date convertToYMDOOODate(Date date, int day)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(5, day);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        return gc.getTime();
    }

    public static Date convertDateTimeToDate(Date dateTime, int day)
    {
        if (dateTime == null)
        {
            return null;
        }
        Date date = convertToDate(convertToDateYMDString(dateTime));
        if (date == null)
        {
            return null;
        }
        return DateUtils.addDays(date, day);
    }

    public static String convertToString(Date date)
    {
        String reportDate = Constant.DateTimeFormat.format(date);
        return reportDate;
    }
    
    public static String convertToStringAndFormat(Date date)
    {
        String reportDate = Constant.DateFormatForFile.format(date);
        return reportDate;
    }

    public static String convertToDateHMString(Date date)
    {
        String reportDate = Constant.DateHMFormat.format(date);
        return reportDate;
    }

    public static String convertToDateYMDString(Date date)
    {
        if (date == null)
        {
            return null;
        }
        String reportDate = Constant.DateFormat.format(date);
        return reportDate;
    }
    
    public static Date convertToDate(String date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        try
        {
            return formatter.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String convertToString(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        return formatter.format(date);
    }

    public static Date getBeforeMonthDate(int months)
    {
        Date current = new Date();
        Date result = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            result = formatter.parse(convertToString(current));
            Calendar c = Calendar.getInstance();
            c.setTime(result);
            c.add(Calendar.MONTH, 0 - months);
            result = c.getTime();
            System.out.println(formatter.format(result));
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        return result;
    }

    public static String getBeforeMonthDateString(int months)
    {
        Date current = new Date();
        Date result = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            result = formatter.parse(convertToString(current));
            Calendar c = Calendar.getInstance();
            c.setTime(result);
            c.add(Calendar.MONTH, 0 - months);
            result = c.getTime();
            System.out.println(formatter.format(result));
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        return convertToString(result);
    }

    public static Integer getHours(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date addDateByHours(Date date, int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static Integer daysBetween(Date beginTime, Date endTime)

    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endTime);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));

    }

    /**
     * 时间减去
     * @param date
     * @param day
     * @return
     */
    public static Date subtractDay(Date date, int day){
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        try
        {
            date = dft.parse(dft.format(calendar.getTime()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 時間大小比較，返回大值
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date timeCompare(Date beginDate,Date endDate)
    {
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try{
            c1.setTime(beginDate);
            c2.setTime(endDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        int result=c1.compareTo(c2);
        if(result==0)
        {
            return beginDate;
        }
        else if(result<0)
        {
            return endDate;
        }
        else
        {
            return beginDate;
        }
    }
    
    /**
     * 
     * @param date
     * @param trackNum
     * @return
     * 按单签收生成临时码规则
     */
    public static  String getString(Date date,String trackNum){
        String results="";
        String trackNumLast="";
        String resultLast="";
        if(trackNum.length()>4){
            trackNumLast=trackNum.substring(trackNum.length()-4);
        }
     
        String month=new Integer(date.getMonth()+1).toString();
        String day=new Integer(date.getDate()).toString();
        if(month.length()<2){
            results+="0"+month;
        }else{
            results+=month;
        }
        if(day.length()<2){
            results+="0"+day;
        }else{
            results+=day;
        }
        resultLast+="("+results+")"+trackNumLast;
        return resultLast;
    }
}
