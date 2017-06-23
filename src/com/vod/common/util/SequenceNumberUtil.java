package com.vod.common.util;

public class SequenceNumberUtil
{

    public static String generateRechargeOrderSN()
    {
        return "RCG" + System.currentTimeMillis();
    }

}
