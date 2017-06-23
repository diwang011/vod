package com.vod.common.util.report;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class FieldNameAndHeaderMapping
{
    private Map<String, String> fieldNameAndHeaderMap; //fieldName -> Report Header Name

    public FieldNameAndHeaderMapping()
    {
    }
    
    public void setFieldNameCsvHeaderMap(Map<String, String> fieldNameAndHeaderMap)
    {
        if(fieldNameAndHeaderMap==null)
        {
            this.fieldNameAndHeaderMap = new LinkedHashMap<String, String>();
        }
        else
        {
            this.fieldNameAndHeaderMap = fieldNameAndHeaderMap;
        }
    }
    
    public Map<String, String> getFieldNameToHeaderMap()
    {
        if(fieldNameAndHeaderMap==null)
        {
            fieldNameAndHeaderMap = new LinkedHashMap<String, String>();
        }
        return Collections.unmodifiableMap(fieldNameAndHeaderMap);
    }
    
    public Map<String, String> getHeaderToFieldNameMap()
    {
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (String key : fieldNameAndHeaderMap.keySet())
        {
            result.put(fieldNameAndHeaderMap.get(key), key);
        }
        return Collections.unmodifiableMap(result);
    }
}
