package com.vod.common.util.txt;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class RmovalOrderTxtable implements Reportable
{
    private String requestDate;
    private String orderId;
    private String orderType;
    private String orderStatus;
    private String lastUpdatedDate;
    private String sku;
    private String fnsku;
    private String disposition;
    private Integer requestedQuantity;
    private Integer cancelledQuantity;
    private Integer disposedQuantity;
    private Integer shippedQuantity;
    private Integer inProcessQuantity;
    private Double removalFee;
    private String currency;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public RmovalOrderTxtable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("boxNo", "箱号");
            fieldHeaderMap.put("sku", "SKU");
            fieldHeaderMap.put("productName", "产品名称");
            fieldHeaderMap.put("count", "数量");
            fieldHeaderMap.put("packTypeName", "包装类型");
            fieldHeaderMapping.setFieldNameCsvHeaderMap(fieldHeaderMap);
        }
        return fieldHeaderMapping;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(String requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getLastUpdatedDate()
    {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getFnsku()
    {
        return fnsku;
    }

    public void setFnsku(String fnsku)
    {
        this.fnsku = fnsku;
    }

    public String getDisposition()
    {
        return disposition;
    }

    public void setDisposition(String disposition)
    {
        this.disposition = disposition;
    }

    public Integer getRequestedQuantity()
    {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity)
    {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getCancelledQuantity()
    {
        return cancelledQuantity;
    }

    public void setCancelledQuantity(Integer cancelledQuantity)
    {
        this.cancelledQuantity = cancelledQuantity;
    }

    public Integer getDisposedQuantity()
    {
        return disposedQuantity;
    }

    public void setDisposedQuantity(Integer disposedQuantity)
    {
        this.disposedQuantity = disposedQuantity;
    }

    public Integer getShippedQuantity()
    {
        return shippedQuantity;
    }

    public void setShippedQuantity(Integer shippedQuantity)
    {
        this.shippedQuantity = shippedQuantity;
    }

    public Integer getInProcessQuantity()
    {
        return inProcessQuantity;
    }

    public void setInProcessQuantity(Integer inProcessQuantity)
    {
        this.inProcessQuantity = inProcessQuantity;
    }

    public Double getRemovalFee()
    {
        return removalFee;
    }

    public void setRemovalFee(Double removalFee)
    {
        this.removalFee = removalFee;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public void setFieldHeaderMapping(FieldNameAndHeaderMapping fieldHeaderMapping)
    {
        this.fieldHeaderMapping = fieldHeaderMapping;
    }

}
