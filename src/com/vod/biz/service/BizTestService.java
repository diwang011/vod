package com.vod.biz.service;


import com.vod.util.PerPageInfo;
import com.vod.util.Response;


public interface BizTestService
{
    public Response<PerPageInfo<String>> search();


}
