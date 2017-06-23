package com.vod.db.model;

public class DbAccountCourier
{
    //courieraccountinfo
    private int id;
    
    private int accountinfoid;
    
    private int courieraccountid;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAccountinfoid()
    {
        return accountinfoid;
    }

    public void setAccountinfoid(int accountinfoid)
    {
        this.accountinfoid = accountinfoid;
    }

    public int getCourieraccountid()
    {
        return courieraccountid;
    }

    public void setCourieraccountid(int courieraccountid)
    {
        this.courieraccountid = courieraccountid;
    }

    public DbAccountCourier(int id, int accountinfoid, int courieraccountid)
    {
        super();
        this.id = id;
        this.accountinfoid = accountinfoid;
        this.courieraccountid = courieraccountid;
    }

    public DbAccountCourier()
    {
        super();
        // TODO Auto-generated constructor stub
    }
}
