package com.vod.db.service;

import java.util.List;

import com.vod.util.Response;

public interface BaseDbService<T>
{
    public Response<Long> count();

    public Response<T> getById(Long id);

    public Response<List<T>> list(Integer offset, Integer limit);

    public Response<List<T>> list();

    public Response<Integer> save(T record, Long operatorId);

    public Response<Integer> delete(T record, Long operatorId);

    public Response<Integer> deleteById(Long id, Long operatorId);
}
