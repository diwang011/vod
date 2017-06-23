package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class SubLocationXlsable implements Reportable
{
//    private String num;
    private String sublocation;
    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public SubLocationXlsable()
    {

    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
//            fieldHeaderMap.put("num", "NO.");
            fieldHeaderMap.put("sublocation", "sublocation");
            fieldHeaderMapping.setFieldNameCsvHeaderMap(fieldHeaderMap);
        }
        return fieldHeaderMapping;
    }

//    public String getNum()
//    {
//        return num;
//    }
//
//    public void setNum(String num)
//    {
//        this.num = num;
//    }

    public String getSublocation()
    {
        return sublocation;
    }

    public void setSublocation(String sublocation)
    {
        this.sublocation = sublocation;
    }

}
