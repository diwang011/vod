package com.vod.common;

/**
 * Used as the base return object for functions, captures operation results count 
 * @author Haoyu Tang
 *
 */
public class OperationResponse
{
    private int createdCount;
    private int updatedCount;
    private int failedCount;
    
    public OperationResponse()
    {
        this.createdCount = 0;
        this.updatedCount = 0;
        this.failedCount = 0;
    }

    public int getCreatedCount()
    {
        return createdCount;
    }

    public void setCreatedCount(int createdCount)
    {
        this.createdCount = createdCount;
    }

    public int getUpdatedCount()
    {
        return updatedCount;
    }

    public void setUpdatedCount(int updatedCount)
    {
        this.updatedCount = updatedCount;
    }

    public int getFailedCount()
    {
        return failedCount;
    }

    public void setFailedCount(int failedCount)
    {
        this.failedCount = failedCount;
    }

    public void addToCreatedCount(int n)
    {
        this.createdCount += n;
    }

    public void addToUpdatedCount(int n)
    {
        this.updatedCount += n;
    }

    public void addToFailedCount(int n)
    {
        this.failedCount += n;
    }

    public void addData(OperationResponse operationResponse)
    {
        if (operationResponse != null)
        {
            this.createdCount += operationResponse.getCreatedCount();
            this.updatedCount += operationResponse.getUpdatedCount();
            this.failedCount += operationResponse.getFailedCount();
        }
    }
    
}
