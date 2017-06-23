package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class InventoryXlsable implements Reportable
{
    private String warehouseName;
    private String sku;
    private String loginId;
    // private String accountName;
    private String locationCode;
    private Integer productCount;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public InventoryXlsable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("warehouseName", "Varehouse");
            // fieldHeaderMap.put("accountName", "客户");
            fieldHeaderMap.put("loginId", "loginId");
            fieldHeaderMap.put("locationCode", "location");
            fieldHeaderMap.put("sku", "SKU");
            fieldHeaderMap.put("productCount", "qty");

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

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getLocationCode()
    {
        return locationCode;
    }

    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }

    public Integer getProductCount()
    {
        return productCount;
    }

    public void setProductCount(Integer productCount)
    {
        this.productCount = productCount;
    }

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }

}
