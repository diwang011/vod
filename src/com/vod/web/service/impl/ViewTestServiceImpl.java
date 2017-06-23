package com.vod.web.service.impl;

import com.vod.biz.service.BizTestService;
import com.vod.util.PerPageInfo;
import com.vod.util.Response;
import com.vod.web.service.ViewTestService;

public class ViewTestServiceImpl implements ViewTestService
{
    
    private BizTestService bizTestService;

 
    public void setBizTestService(BizTestService bizTestService)
    {
        this.bizTestService = bizTestService;
    }


    @Override
    public Response<PerPageInfo<String>> search()
    {
        return bizTestService.search();
    }

}
