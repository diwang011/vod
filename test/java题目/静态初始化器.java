package java题目;

public class 静态初始化器
{
    static int count;
    int i;

    static
    {
        //This is a static initializers. Run only when Class is first loaded.
        //Only static variables can be accessed
        System.out.println("Static Initializer");
        //i = 6;//COMPILER ERROR
        System.out.println("Count when Static Initializer is run is " + count);
    }

    public static void main(String[] args)
    {
        静态初始化器 example = new 静态初始化器();
        静态初始化器 example2 = new 静态初始化器();
        静态初始化器 example3 = new 静态初始化器();
    }
}
