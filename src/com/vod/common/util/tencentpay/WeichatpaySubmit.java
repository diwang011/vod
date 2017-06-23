package com.vod.common.util.tencentpay;

import java.util.Map;

public class WeichatpaySubmit
{
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(String key, Map<String, Object> sPara)
    {
        String mysign = "";
        mysign = Signature.getSign(sPara, key);
        return mysign;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(String key, UnifiedOrder order)
    {
        String mysign = "";
        try
        {
            mysign = Signature.getSign(order, key);
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mysign;
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取处理结果
     * @return 处理结果
     * @throws Exception
     */
    public static String buildRequest(String url, String key, UnifiedOrder order)
            throws Exception
    {
        //待请求参数数组
        String sign  = buildRequestMysign(key, order);
        order.setSign(sign);
        HttpsRequest httpResq = new HttpsRequest();
        String respone = httpResq.sendPost(url, order);
        return respone;
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取处理结果
     * @return 处理结果
     * @throws Exception
     */
    public static String buildRequest(String url, String key, OrderQuery orderQuery)
            throws Exception
    {
        //待请求参数数组
        String sign  = buildRequestMysign(key, orderQuery);
        orderQuery.setSign(sign);
        HttpsRequest httpResq = new HttpsRequest();
        String respone = httpResq.sendPost(url, orderQuery);
        return respone;
    }
    
    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取处理结果
     * @return 处理结果
     * @throws Exception
     */
    public static String buildRequest(String url, String key, OrderClose orderClose)
            throws Exception
    {
        //待请求参数数组
        String sign  = buildRequestMysign(key, orderClose);
        orderClose.setSign(sign);
        HttpsRequest httpResq = new HttpsRequest();
        String respone = httpResq.sendPost(url, orderClose);
        return respone;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(String key, OrderQuery order)
    {
        String mysign = "";
        try
        {
            mysign = Signature.getSign(order, key);
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mysign;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(String key, OrderClose orderClose)
    {
        String mysign = "";
        try
        {
            mysign = Signature.getSign(orderClose, key);
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mysign;
    }
}
