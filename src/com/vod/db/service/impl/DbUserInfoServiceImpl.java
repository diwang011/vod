package com.vod.db.service.impl;

import java.util.Date;
import java.util.List;
import com.vod.db.model.UserInfo;
import com.vod.db.model.UserInfoExample;
import com.alibaba.fastjson.JSON;
import com.vod.db.mapper.UserInfoMapper;
import com.vod.util.Response;
import com.vod.db.service.DbUserInfoService;

public class DbUserInfoServiceImpl  implements DbUserInfoService
{
	private UserInfoMapper userInfoMapper;

	@Override
	public Response<Integer> count()
	{
		int total = 0;
		total = userInfoMapper.countByExample(null);
		Response<Integer> dbResponse = new Response<Integer>();
		dbResponse.setData(total);
        return dbResponse;
    }

    @Override
    public Response<UserInfo> getById(Integer id)
    {
        Response<UserInfo> dbResponse = new Response<UserInfo>();
        dbResponse.setData(userInfoMapper.selectByPrimaryKey(id));
        return dbResponse;
    }

    @Override
    public Response<List<UserInfo>> list(Integer offset, Integer limit)
    {
        Response<List<UserInfo>> dbResponse = new Response<List<UserInfo>>();
        UserInfoExample example = null;
        if (offset != null || limit != null)
        {
            example = new UserInfoExample();
            example.setLimit(limit);
            example.setOffset(offset);
        }
        dbResponse.setData(userInfoMapper.selectByExample(example));
        return dbResponse;
    }

    @Override
    public Integer deleteById(Integer id)
    {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer update(UserInfo record)
    {
        System.out.println(JSON.toJSONString(record)+new Date());
        return userInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer insert(UserInfo record)
    {
        System.out.println(JSON.toJSONString(record));
        return userInfoMapper.insert(record);
    }

    public void setUserInfoMapper(UserInfoMapper userInfoMapper)
    {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Response<List<UserInfo>> list()
    {
        Response<List<UserInfo>> dbResponse = new Response<List<UserInfo>>();
        UserInfoExample example = new UserInfoExample();
        dbResponse.setData(userInfoMapper.selectByExample(example));
        return dbResponse;
    }

}
