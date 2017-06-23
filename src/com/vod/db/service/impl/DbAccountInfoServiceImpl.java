package com.vod.db.service.impl;

import java.util.List;

import com.vod.db.mapper.AccountInfoMapper;
import com.vod.db.model.AccountInfo;
import com.vod.db.model.AccountInfoExample;
import com.vod.db.service.DbAccountInfoService;
import com.vod.util.Response;

public class DbAccountInfoServiceImpl extends BaseDbServiceImpl<AccountInfo> implements DbAccountInfoService
{
    private AccountInfoMapper accountInfoMapper;

    @Override
    protected int insert(AccountInfo record)
    {
        int row = -1;
        row = accountInfoMapper.insert(record);
        return row;
    }

    @Override
    protected int update(AccountInfo record)
    {
        int row = -1;
        row = accountInfoMapper.updateByPrimaryKey(record);
        return row;
    }

    @Override
    public Response<AccountInfo> getById(Long id)
    {
        Response<AccountInfo> dbResponse = new Response<AccountInfo>();
        dbResponse.setData(accountInfoMapper.selectByPrimaryKey(id));
        return dbResponse;
    }

    @Override
    public Response<Long> count()
    {
        long total = 0;
        total = accountInfoMapper.countByExample(null);
        Response<Long> dbResponse = new Response<Long>();
        dbResponse.setData(total);
        return dbResponse;
    }

    @Override
    public Response<List<AccountInfo>> list(Integer offset, Integer limit)
    {
        Response<List<AccountInfo>> dbResponse = new Response<List<AccountInfo>>();
        AccountInfoExample example = null;
        if (offset != null || limit != null)
        {
            example = new AccountInfoExample();
            example.setLimit(limit);
            example.setOffset(offset);
        }
        dbResponse.setData(accountInfoMapper.selectByExample(example));
        return dbResponse;
    }

    @Override
    protected int deleteById(Long id)
    {
        int row = -1;
        row = accountInfoMapper.deleteByPrimaryKey(id);
        return row;
    }

    public void setAccountInfoMapper(AccountInfoMapper accountInfoMapper)
    {
        this.accountInfoMapper = accountInfoMapper;
    }

}
