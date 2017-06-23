package com.vod.web.model;

import java.util.List;


public class ViewSessionUser  //session
{
    private Long accountId;
    private Integer parentId;
    private String email;
    private String name;
    private String password;
    private String language;
    private String defaultPage;
    private String defaultPrintLanguage;//默认打印语言
    private Integer rowsPerPage;
    private Integer refreshingRate;
    private Integer onlineTimeLimit;
    private Boolean publicEnvironment;
    private String verificationCode;
    private Boolean verified;
    private Boolean defaultFulfilmentType;
    private String defaultMeasurement;
    private String accountType;
    private List<String> functionalityList;
    private List<String> menuList;

    private Integer loginFailedCount;


    @Override
    public String toString()
    {
        return "ViewSessionUser [accountId=" + accountId + ", email=" + email + ", name=" + name + ", password="
                + password + ", language=" + language + ", defaultPage=" + defaultPage + ", defaultPrintLanguage="
                + defaultPrintLanguage + ", rowsPerPage=" + rowsPerPage + ", refreshingRate=" + refreshingRate
                + ", onlineTimeLimit=" + onlineTimeLimit + ", publicEnvironment=" + publicEnvironment
                + ", verificationCode=" + verificationCode + ", verified=" + verified + ", defaultFulfilmentType="
                + defaultFulfilmentType + ", defaultMeasurement=" + defaultMeasurement + ", accountType=" + accountType
                + ", functionalityList=" + functionalityList + ", resourceList="  + "]";
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getDefaultPage()
    {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage)
    {
        this.defaultPage = defaultPage;
    }

    public String getDefaultPrintLanguage()
    {
        return defaultPrintLanguage;
    }

    public void setDefaultPrintLanguage(String defaultPrintLanguage)
    {
        this.defaultPrintLanguage = defaultPrintLanguage;
    }

    public Integer getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    public Boolean getPublicEnvironment()
    {
        return publicEnvironment;
    }

    public void setPublicEnvironment(Boolean publicEnvironment)
    {
        this.publicEnvironment = publicEnvironment;
    }

    public String getVerificationCode()
    {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    public Integer getRefreshingRate()
    {
        return refreshingRate;
    }

    public void setRefreshingRate(Integer refreshingRate)
    {
        this.refreshingRate = refreshingRate;
    }

    public Integer getOnlineTimeLimit()
    {
        return onlineTimeLimit;
    }

    public void setOnlineTimeLimit(Integer onlineTimeLimit)
    {
        this.onlineTimeLimit = onlineTimeLimit;
    }

    public String getDefaultMeasurement()
    {
        return defaultMeasurement;
    }

    public void setDefaultMeasurement(String defaultMeasurement)
    {
        this.defaultMeasurement = defaultMeasurement;
    }

    public Boolean getVerified()
    {
        return verified;
    }

    public Boolean getDefaultFulfilmentType()
    {
        return defaultFulfilmentType;
    }

    public void setDefaultFulfilmentType(Boolean defaultFulfilmentType)
    {
        this.defaultFulfilmentType = defaultFulfilmentType;
    }

    public void setVerified(Boolean verified)
    {
        this.verified = verified;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public List<String> getFunctionalityList()
    {
        return functionalityList;
    }

    public void setFunctionalityList(List<String> functionalityList)
    {
        this.functionalityList = functionalityList;
    }


    public Integer getLoginFailedCount()
    {
        return loginFailedCount;
    }

    public void setLoginFailedCount(Integer loginFailedCount)
    {
        this.loginFailedCount = loginFailedCount;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public List<String> getMenuList()
    {
        return menuList;
    }

    public void setMenuList(List<String> menuList)
    {
        this.menuList = menuList;
    }

}
