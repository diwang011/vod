package 反射;

public class ClassDemo
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void Fun()
    {
        System.out.println("Fun.........");
    }

    public void connectionPool(String name)
    {
        System.out.println(name + ">>>>>>>>>>>");
    }
}
