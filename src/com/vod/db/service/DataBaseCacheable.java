package com.vod.db.service;

import com.vod.common.Cacheable;

public interface DataBaseCacheable<T> extends Cacheable
{
    public void removeFromCache(T record);

    public void removeFromCache(Long pk);

    public void addIntoCache(T record);

    public void updateCache(T record);
}
