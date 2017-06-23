package com.vod.common;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CommonUploadFile
{
    private List<MultipartFile> multipartFileList;
    private List<CommonAttachedFile> attachedFileList;

    public List<MultipartFile> getMultipartFileList()
    {
        return multipartFileList;
    }

    public void setMultipartFileList(List<MultipartFile> multipartFileList)
    {
        this.multipartFileList = multipartFileList;
    }

    public List<CommonAttachedFile> getAttachedFileList()
    {
        return attachedFileList;
    }

    public void setAttachedFileList(List<CommonAttachedFile> attachedFileList)
    {
        this.attachedFileList = attachedFileList;
    }

}
