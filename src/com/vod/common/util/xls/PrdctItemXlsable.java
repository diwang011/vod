package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class PrdctItemXlsable implements Reportable
{
    private String boardNo;
    private String boxNoScope;
    private String sku;
    private String count;
    private String packTypeName;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public PrdctItemXlsable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("boardNo", "Board No");
            fieldHeaderMap.put("boxNoScope", "BoxNo Scope");
            fieldHeaderMap.put("sku", "SKU");
            fieldHeaderMap.put("count", "Count");
            fieldHeaderMap.put("packTypeName", "Pack Type");
            fieldHeaderMapping.setFieldNameCsvHeaderMap(fieldHeaderMap);
        }
        return fieldHeaderMapping;
    }

    public FieldNameAndHeaderMapping getFieldHeaderMapping()
    {
        return fieldHeaderMapping;
    }

    public void setFieldHeaderMapping(FieldNameAndHeaderMapping fieldHeaderMapping)
    {
        this.fieldHeaderMapping = fieldHeaderMapping;
    }

    public String getBoardNo()
    {
        return boardNo;
    }

    public void setBoardNo(String boardNo)
    {
        this.boardNo = boardNo;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public String getPackTypeName()
    {
        return packTypeName;
    }

    public void setPackTypeName(String packTypeName)
    {
        this.packTypeName = packTypeName;
    }

    public String getBoxNoScope()
    {
        return boxNoScope;
    }

    public void setBoxNoScope(String boxNoScope)
    {
        this.boxNoScope = boxNoScope;
    }

}
