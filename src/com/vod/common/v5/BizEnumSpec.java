package com.vod.common.v5;

public class BizEnumSpec
{
    private String code;
    private String label;

    @Override
    public String toString()
    {
        return "BizEnumSpec [code=" + code + ", label=" + label + "]";
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

}
