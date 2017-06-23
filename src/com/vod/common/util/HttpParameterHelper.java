package com.vod.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vod
 * 
 */
public class HttpParameterHelper
{

    private HttpParameterHelper()
    {
    };

    private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

    public static HttpParameterHelper builder()
    {
        return new HttpParameterHelper();
    }

    public Map<String, Map<String, Object>> getMap()
    {
        return map;
    }

    /**
     * 
     * @param key
     * @param value
     * @param flag
     *            true(REQUIRED) false(OPTIONAL)
     * @return
     */
    public HttpParameterHelper parameter2Map(String key, Object value, boolean flag)
    {
        if (flag)
        {
            if (null == map.get(HttpClientHelper.REQUIRED))
            {
                map.put(HttpClientHelper.REQUIRED, new HashMap<String, Object>());
            }
            map.get(HttpClientHelper.REQUIRED).put(key, value);
        }
        else
        {
            if (null == map.get(HttpClientHelper.OPTIONAL))
            {
                map.put(HttpClientHelper.OPTIONAL, new HashMap<String, Object>());
            }
            map.get(HttpClientHelper.OPTIONAL).put(key, value);
        }
        return this;
    }

    public HttpParameterHelper parameter2Head(String key, Object value)
    {
        if (null == map.get(HttpClientHelper.HEAD))
        {
            map.put(HttpClientHelper.HEAD, new HashMap<String, Object>());
        }
        map.get(HttpClientHelper.HEAD).put(key, value);

        return this;
    }

}
