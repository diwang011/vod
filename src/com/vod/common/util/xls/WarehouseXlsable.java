package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class WarehouseXlsable implements Reportable
{
    private String mainLoginId;
    private String subLoginId;
    private String warehouseName;
    private String ownerName;
    private String phone;
    private String warehouseType;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String addressLine1;
    private String isVirtual;
    // private String description;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public WarehouseXlsable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("mainLoginId", "用户名称");
            fieldHeaderMap.put("subLoginId", "子账号");
            fieldHeaderMap.put("warehouseName", "仓库名称");
            fieldHeaderMap.put("warehouseType", "仓库类型");
            fieldHeaderMap.put("ownerName", "联系人");
            fieldHeaderMap.put("phone", "联系电话");
            fieldHeaderMap.put("country", "国家");
            fieldHeaderMap.put("state", "省/州");
            fieldHeaderMap.put("city", "城市");
            fieldHeaderMap.put("postalCode", "邮编");
            fieldHeaderMap.put("addressLine1", "地址");
            fieldHeaderMap.put("isVirtual", "是否虚拟仓");
            // fieldHeaderMap.put("description", "仓库说明");
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

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getWarehouseType()
    {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType)
    {
        this.warehouseType = warehouseType;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    public String getMainLoginId()
    {
        return mainLoginId;
    }

    public void setMainLoginId(String mainLoginId)
    {
        this.mainLoginId = mainLoginId;
    }

    public String getSubLoginId()
    {
        return subLoginId;
    }

    public void setSubLoginId(String subLoginId)
    {
        this.subLoginId = subLoginId;
    }

    public String getIsVirtual()
    {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual)
    {
        this.isVirtual = isVirtual;
    }

}
