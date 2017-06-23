package com.vod.db.service;

import java.util.List;

import com.vod.db.model.UserInfoLog;
import com.vod.util.Response;

public interface DbUserInfoLogService
{

    public Response<Integer> count();

    public Response<UserInfoLog> getById(Integer id);

    public Response<List<UserInfoLog>> list(Integer offset, Integer limit);

    public Integer deleteById(Integer id);

    public Integer update(UserInfoLog record);

    public Integer insert(UserInfoLog record);

}
