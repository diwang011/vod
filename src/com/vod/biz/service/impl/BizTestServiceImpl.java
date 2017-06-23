package com.vod.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.vod.biz.service.BizTestService;
import com.vod.db.model.AccountInfo;
import com.vod.db.service.DbAccountInfoService;
import com.vod.util.PerPageInfo;
import com.vod.util.Response;

public class BizTestServiceImpl implements BizTestService
{
    private DbAccountInfoService dbAccountInfoService;


    public void setDbAccountInfoService(DbAccountInfoService dbAccountInfoService)
    {
        this.dbAccountInfoService = dbAccountInfoService;
    }


    @Override
    public Response<PerPageInfo<String>> search()
    {
        Response<PerPageInfo<String>> response=new Response<PerPageInfo<String>>();
        Response<AccountInfo> ac=dbAccountInfoService.getById(110L);
        PerPageInfo<String> data=new PerPageInfo<>();
        List<String> rows=new ArrayList<>();
        rows.add(ac.getData().getAccountName());
        data.setRows(rows);
        response.setData(data);
        return response;
    }
}
