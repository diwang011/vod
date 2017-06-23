package com.vod.common;


public class CommonAttachedFile
{
    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    private String remark;
    
    @Override
    public String toString()
    {
        return "CommonAttachedFile [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", filePath="
                + filePath + ", remark=" + remark + "]";
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
