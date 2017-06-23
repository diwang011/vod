package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class LocationXlsable implements Reportable
{
    private String warehouseName;
    private String serviceType;
    private String partitionCode;
    private String partitionName;
    private String tunnelId;
    private String positionX;
    private String positionY;
    private String positionZ;
    private Double lengthCm;
    private Double widthCm;
    private Double heightCm;
    private Double bearingKg;
    private String locationType;
    private String shelvesType;
    private Integer putawayPiority;
    private Integer pickupPiority;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public LocationXlsable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("warehouseName", "仓库名称");
            fieldHeaderMap.put("serviceType", "仓库类型");
            fieldHeaderMap.put("partitionCode", "分区代码");
            fieldHeaderMap.put("partitionName", "分区名称");
            fieldHeaderMap.put("tunnelId", "通道ID");
            fieldHeaderMap.put("positionX", "X(货架排名称)");
            fieldHeaderMap.put("positionY", "Y（货位号）");
            fieldHeaderMap.put("positionZ", "Z（层号）");
            fieldHeaderMap.put("shelvesType", "货架类型");
            fieldHeaderMap.put("locationType", "货位类型");
            fieldHeaderMap.put("lengthCm", "长(inch)");
            fieldHeaderMap.put("widthCm", "宽(inch)");
            fieldHeaderMap.put("heightCm", "高(inch)");
            fieldHeaderMap.put("bearingKg", "支持重量(LB)");
            fieldHeaderMap.put("putawayPiority", "上架优先级");
            fieldHeaderMap.put("pickupPiority", "下架优先级");
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

    public String getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(String serviceType)
    {
        this.serviceType = serviceType;
    }

    public String getPartitionCode()
    {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode)
    {
        this.partitionCode = partitionCode;
    }

    public String getPartitionName()
    {
        return partitionName;
    }

    public void setPartitionName(String partitionName)
    {
        this.partitionName = partitionName;
    }

    public String getTunnelId()
    {
        return tunnelId;
    }

    public void setTunnelId(String tunnelId)
    {
        this.tunnelId = tunnelId;
    }

    public String getPositionX()
    {
        return positionX;
    }

    public void setPositionX(String positionX)
    {
        this.positionX = positionX;
    }

    public String getPositionY()
    {
        return positionY;
    }

    public void setPositionY(String positionY)
    {
        this.positionY = positionY;
    }

    public String getPositionZ()
    {
        return positionZ;
    }

    public void setPositionZ(String positionZ)
    {
        this.positionZ = positionZ;
    }

    public Double getLengthCm()
    {
        return lengthCm;
    }

    public void setLengthCm(Double lengthCm)
    {
        this.lengthCm = lengthCm;
    }

    public Double getWidthCm()
    {
        return widthCm;
    }

    public void setWidthCm(Double widthCm)
    {
        this.widthCm = widthCm;
    }

    public Double getHeightCm()
    {
        return heightCm;
    }

    public void setHeightCm(Double heightCm)
    {
        this.heightCm = heightCm;
    }

    public Double getBearingKg()
    {
        return bearingKg;
    }

    public void setBearingKg(Double bearingKg)
    {
        this.bearingKg = bearingKg;
    }

    public Integer getPutawayPiority()
    {
        return putawayPiority;
    }

    public void setPutawayPiority(Integer putawayPiority)
    {
        this.putawayPiority = putawayPiority;
    }

    public Integer getPickupPiority()
    {
        return pickupPiority;
    }

    public void setPickupPiority(Integer pickupPiority)
    {
        this.pickupPiority = pickupPiority;
    }

    public String getLocationType()
    {
        return locationType;
    }

    public void setLocationType(String locationType)
    {
        this.locationType = locationType;
    }

    public String getShelvesType()
    {
        return shelvesType;
    }

    public void setShelvesType(String shelvesType)
    {
        this.shelvesType = shelvesType;
    }

}
