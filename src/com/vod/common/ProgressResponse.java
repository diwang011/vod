package com.vod.common;

/**
 * Used as the base progress return object for functions, captures progress results count 
 * @author Haoyu Tang
 *
 */
public class ProgressResponse
{
    private int totalCount;
    private int processedCount;
    private int failedCount;
    
    public ProgressResponse()
    {
        this.totalCount = 0;
        this.processedCount = 0;
        this.failedCount = 0;
    }

    public void addToProcessedCount(int n)
    {
        this.processedCount += n;
    }

    public void addToFailedCount(int n)
    {
        this.failedCount += n;
        this.processedCount += n;
    }

    public void addData(ProgressResponse progressResponseResponse)
    {
        if (progressResponseResponse != null)
        {
            this.processedCount += progressResponseResponse.getProcessedCount();
            this.failedCount += progressResponseResponse.getFailedCount();
            this.totalCount += progressResponseResponse.getTotalCount();
        }
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public int getProcessedCount()
    {
        return processedCount;
    }

    public void setProcessedCount(int processedCount)
    {
        this.processedCount = processedCount;
    }

    public int getFailedCount()
    {
        return failedCount;
    }


    public void setFailedCount(int failedCount)
    {
        this.failedCount = failedCount;
    }
    
}
