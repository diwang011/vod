package com.vod.db.model;

public class DbBaseModel {
	private String language;
    private Long accountId;
    private String name;
    private Integer offSize;
    private Integer limit;

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getOffSize()
    {
        return offSize;
    }

    public void setOffSize(Integer offSize)
    {
        this.offSize = offSize;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }

}
