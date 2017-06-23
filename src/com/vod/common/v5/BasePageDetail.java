package com.vod.common.v5;

import java.util.List;

public class BasePageDetail<T>
{
    private int total;
    private int rowsPerPage;
    private int offset;
    private List<T> datas;

    @Override
    public String toString()
    {
        return "BasePageDetail [total=" + total + ", rowsPerPage=" + rowsPerPage + ", offset=" + offset + ", datas="
                + datas + "]";
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public List<T> getDatas()
    {
        return datas;
    }

    public void setDatas(List<T> datas)
    {
        this.datas = datas;
    }

}
