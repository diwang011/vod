package com.vod.db.service;

import java.util.List;

import com.vod.db.model.UserInfo;
import com.vod.util.Response;

public interface DbUserInfoService 
{
    public Response<Integer> count();

    public Response<UserInfo> getById(Integer id);

    public Response<List<UserInfo>> list(Integer offset, Integer limit);

    public Response<List<UserInfo>> list();

    public Integer deleteById(Integer id);

    public Integer update(UserInfo record);

    public Integer insert(UserInfo record);
}
