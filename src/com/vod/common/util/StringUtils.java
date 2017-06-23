/**
 * 
 */
package com.vod.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author log
 * @mail chenlong@gz.iscas.ac.cn
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils
{
    public static List<String> converToList(String strs)
    {
        if (isEmpty(strs))
        {
            return null;
        }
        List<String> strList = new ArrayList<String>();
        for (String str : strs.split(","))
        {
            if (!isEmpty(str))
            {
                strList.add(str);
            }
        }
        return strList;
    }
    public static String converToString(String[] strList)
    {
        if (strList == null)
        {
            return null;
        }
        String strs = "";
        for (String str : strList)
        {
            if (!strs.isEmpty())
            {
                strs += ",";
            }
            strs += str;
        }
        return strs;
    }
    public static String converToString(Collection<String> strList)
    {
        if (strList == null)
        {
            return null;
        }
        String strs = "";
        for (String str : strList)
        {
            if (!strs.isEmpty())
            {
                strs += ",";
            }
            strs += str;
        }
        return strs;
    }

    /**
     * 去空格方法
     * 
     * @param strs
     * @return
     */
    public static String removeWhiteSpaces(String strs)
    {

        if (strs == null)
        {
            return null;
        }
        String str ="";
            str = strs.trim();

            while (str.startsWith("　"))
            {// 这里判断是不是全角空格
                str = str.substring(1, str.length()).trim();
            }
            while (str.endsWith("　"))
            {
                str = str.substring(0, str.length() - 1).trim();
            }

        return strs.replaceAll("[　*| *| *|�*|\\s]*", "");
    }

    /**
     * 将null或空字符串转成null
     * @param str
     * @return
     */
    public static String emptyStringToNull(String str){
        if (str == null||str.length()==0){
            return null;
        } else {
            return str;
        }
    }
    
    /**
     * 去除所有非字母&数字的字符
     * @param str
     * @return
     */
    public static String stripNonAlphaNumeric(String str){
        if (str == null)
            return null;
        return str.replaceAll("[^A-Za-z0-9]", "");
    }

    /**
     * 去除所有非数字和小数点的字符
     * @param str
     * @return
     */
    public static String stripNonPointNumeric(String str){
        if (str == null)
            return null;
        return str.replaceAll("[^0-9.]", "");
    }

    public static boolean isEmpty(String str)
    {
        if (str == null || str.trim().length() <= 0)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 去除memo中的非追踪号信息
     * @param str
     * @return
     */
    public static String stripForPickupTrackingInfo(String str){
        if (str == null)
            return null;
        str = str.replaceAll(", ", ",");
        str = str.replaceAll(" ", ",");
        str= str.replaceAll("[^A-Z0-9,]", "");
        String[] strArray = str.split(",");
        String strReturn = "";
        for(int i=0;i<strArray.length;i++)
        {
            String strTmp = strArray[i];
            if(strTmp.length()>6)
                strReturn = strReturn + strTmp + " ";
        }
        
        if(strReturn.length()>0)
        {
            strReturn = strReturn.substring(0, strReturn.length()-1);
        }
        return strReturn;
    }

    /**
     * 校验email格式是否正确
     * @param email
     * @return
     */
    public static Boolean checkEmail(String email)
    {
        if (isEmpty(email))
        {
            return false;
        }
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
