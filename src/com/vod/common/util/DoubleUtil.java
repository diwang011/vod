package com.vod.common.util;

import java.math.BigDecimal;

public class DoubleUtil
{
    private static final int DEF_DIV_SCALE = 2;
    private static final int CURRENCY_CONVERTER = 3;
    /**
     * v1 add v2
     * 
     * @param v1
     *            *
     * @param v2
     *            *
     * @return Double
     */
    public static Double add(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    /**
     * v1 sub v2
     * 
     * @param v1
     *            *
     * @param v2
     *            *
     * @return Double
     */
    public static Double sub(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * v1 mul v2
     * 
     * @param v1
     *            *
     * @param v2
     *            *
     * @return Double
     */
    public static Double mul(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }
    
    public static Double mul(Double v1, Integer v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * v1 div v2
     * 
     * @param v1
     *            *
     * @param v2
     *            *
     * @return Double
     */
    public static Double div(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, CURRENCY_CONVERTER, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
    
    public static Double divRoundUp(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_UP).doubleValue());
    }
    
    public static Double div(Long v1, Long v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * v1 div v2
     * 
     * @param v1
     *            *
     * @param v2
     *            *
     * @param scale
     *            *
     * @return Double
     */
    public static Double div(Double v1, Double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
    
    public static Double divRoundUp(Double v1, Double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_UP).doubleValue());
    }

    /**
     * doubel to int. eg:10.1 return 10 ,10.5 return 10 , 10.5001 return 11
     * 
     * @param v
     * @return
     */
    public static Double rint(Double v)
    {
        return Math.rint(v);
    }

    /**
     * doubel to int. eg:10.1 return 11
     * 
     * @param v
     * @return
     */
    public static Double ceil(Double v)
    {
        return Math.ceil(v);
    }

    public static Double floor(Double v)
    {
        return Math.floor(v);
    }
    
    public  static double getCubeRoot(double input){  
        if(input==0)  
            return 0;  
        double x0,x1;  
        x0=input;  
        x1=(2*x0/3)+(input/(x0*x0*3));//利用迭代法求解  
        while(Math.abs(x1-x0)>0.000001){  
            x0=x1;  
            x1=(2*x0/3)+(input/(x0*x0*3));  
        }  
        return x1;  
    }  
}
