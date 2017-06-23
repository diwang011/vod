package com.vod.common;

public abstract class BaseRequest<T>
{
    private T data;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
    
}
