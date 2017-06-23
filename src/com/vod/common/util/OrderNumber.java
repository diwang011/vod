package com.vod.common.util;

/**
 * 
 * @author vod
 * 
 */
public class OrderNumber
{
    public static String getOrderNumber()
    {
        String orderNo = "" + System.nanoTime();
        orderNo = orderNo.substring(7);
        return orderNo;
    }

    public static String getRequstNumber()
    {
        String orderNo = "" + System.nanoTime();
        orderNo = orderNo.substring(10);
        return orderNo;
    }

    public static void main(String[] args)
    {
        System.out.println(getOrderNumber());
        System.out.println(getRequstNumber());
    }

}
