package com.vod.db.service.impl;

import java.util.List;
import com.vod.db.model.UserInfoLog;
import com.vod.db.model.UserInfoLogExample;
import com.vod.db.mapper.UserInfoLogMapper;
import com.vod.util.Response;
import com.vod.db.service.DbUserInfoLogService;

public class DbUserInfoLogServiceImpl  implements DbUserInfoLogService
{
	private UserInfoLogMapper userInfoLogMapper;

	@Override
	public Response<Integer> count()
	{
		int total = 0;
		total = userInfoLogMapper.countByExample(null);
		Response<Integer> dbResponse = new Response<Integer>();
		dbResponse.setData(total);
        return dbResponse;
    }

    @Override
    public Response<UserInfoLog> getById(Integer id)
    {
        Response<UserInfoLog> dbResponse = new Response<UserInfoLog>();
        dbResponse.setData(userInfoLogMapper.selectByPrimaryKey(id));
        return dbResponse;
    }

    @Override
    public Response<List<UserInfoLog>> list(Integer offset, Integer limit)
    {
        Response<List<UserInfoLog>> dbResponse = new Response<List<UserInfoLog>>();
        UserInfoLogExample example = null;
        if (offset != null || limit != null)
        {
            example = new UserInfoLogExample();
            example.setLimit(limit);
            example.setOffset(offset);
        }
        dbResponse.setData(userInfoLogMapper.selectByExample(example));
        return dbResponse;
    }

    @Override
    public Integer deleteById(Integer id)
    {
        return userInfoLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer update(UserInfoLog record)
    {
        return userInfoLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer insert(UserInfoLog record)
    {
        return userInfoLogMapper.insert(record);
    }

    public void setUserInfoLogMapper(UserInfoLogMapper userInfoLogMapper)
    {
        this.userInfoLogMapper = userInfoLogMapper;
    }

}
