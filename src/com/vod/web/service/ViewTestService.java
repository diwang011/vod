package com.vod.web.service;


import com.vod.util.PerPageInfo;
import com.vod.util.Response;
public interface ViewTestService
{

    public Response<PerPageInfo<String>> search();


}
