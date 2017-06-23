package com.vod.common.util;

/**
 * 
 * @author vod
 * 
 */
public class EnumUtils
{

    @SuppressWarnings("rawtypes")
    public static Enum fetchEnum(Class clazz, String name)
    {
        Enum e = null;
        Enum[] em = (Enum[]) clazz.getEnumConstants();
        for (Enum i : em)
        {
            if (i.name().equalsIgnoreCase(name))
            {
                e = i;
                break;
            }
        }
        return e;
    }
}
