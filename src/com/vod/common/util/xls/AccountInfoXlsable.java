package com.vod.common.util.xls;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class AccountInfoXlsable implements Reportable
{
    private String mainLoginId;
    private String subLoginId;
    private String accountType;
    private String accountName;
    private String contactNumber;
    private String faxNumber;
    private String webchatId;
    private String altEmail;
    private String qqId;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String addressLine1;
    private String phoneNumber;

    private FieldNameAndHeaderMapping fieldHeaderMapping = null;

    public AccountInfoXlsable()
    {
    }

    @Override
    public FieldNameAndHeaderMapping fetchFieldNameAndHeaderMapping()
    {
        if (fieldHeaderMapping == null)
        {
            fieldHeaderMapping = new FieldNameAndHeaderMapping();
            Map<String, String> fieldHeaderMap = new LinkedHashMap<String, String>();
            fieldHeaderMap.put("country", "国家");
            fieldHeaderMap.put("mainLoginId", "主帐号名称");
            fieldHeaderMap.put("subLoginId", "用户名称");
            fieldHeaderMap.put("accountType", "帐号类型");
            fieldHeaderMap.put("accountName", "联系人姓名");
            fieldHeaderMap.put("contactNumber", "电话");
            fieldHeaderMap.put("faxNumber", "Fax");
            fieldHeaderMap.put("webchatId", "Web");
            fieldHeaderMap.put("altEmail", "Email");
            fieldHeaderMap.put("addressLine1", "地址");
            fieldHeaderMap.put("postalCode", "邮编");
            fieldHeaderMap.put("city", "城市");
            fieldHeaderMap.put("state", "省/州");
            fieldHeaderMap.put("qqId", "QQ");
            fieldHeaderMap.put("phoneNumber", "手机");
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

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getFaxNumber()
    {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public String getWebchatId()
    {
        return webchatId;
    }

    public void setWebchatId(String webchatId)
    {
        this.webchatId = webchatId;
    }

    public String getAltEmail()
    {
        return altEmail;
    }

    public void setAltEmail(String altEmail)
    {
        this.altEmail = altEmail;
    }

    public String getQqId()
    {
        return qqId;
    }

    public void setQqId(String qqId)
    {
        this.qqId = qqId;
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

}
