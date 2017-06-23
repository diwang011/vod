package com.vod.common.util;

import java.nio.ByteBuffer;

import org.apache.commons.codec.binary.Base64;

public class Base64Util
{

    public static String encodeToBASE64(String s)
    {
        if (s == null)
            return null;
        return Base64.encodeBase64String(s.getBytes());
    }

    public static String decodeFromBASE64(String s)
    {
        if (s == null)
            return null;
        try
        {
            byte[] b = Base64.decodeBase64(s);
            return new String(b);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ByteBuffer getFromBASE64byte(String s)
    {
        if (s == null)
            return null;
        try
        {
            byte[] bytes = Base64.decodeBase64(s);
            return ByteBuffer.wrap(bytes);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println(encodeToBASE64("742531883be465a6:90d77fcfa3f2e940319aab"));
        System.out.println(decodeFromBASE64("NzQyNTMxODgzYmU0NjVhNjo5MGQ3N2ZjZmEzZjJlOTQwMzE5YWFi"));

    }

}
