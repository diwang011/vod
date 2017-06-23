package com.vod.util;

import com.vod.common.BaseResponse;

public class Response<T> extends BaseResponse<T>
{
    private String nonceToken; // against CRSF

    public String getNonceToken()
    {
        return nonceToken;
    }

    public void setNonceToken(String nonceToken)
    {
        this.nonceToken = nonceToken;
    }
}
