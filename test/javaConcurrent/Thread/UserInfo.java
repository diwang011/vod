package javaConcurrent.Thread;


public class UserInfo
{
    private Integer id;
    private String name;
    private Double amount;

    public UserInfo()
    {
    }

    public UserInfo(Integer id, Double amount)
    {
        super();
        this.id = id;
        this.amount = amount;
    }

    public UserInfo(String name, Double amount)
    {
        super();
        this.name = name;
        this.amount = amount;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

}
