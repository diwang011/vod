package com.vod.common.util;

import java.util.UUID;

public class UUIDUtil
{

    public static String randomUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
