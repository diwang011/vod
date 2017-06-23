package java题目;

public class 实例初始化块
{
    static int count;
    int i;
    {
        //This is an instance initializers. Run every time an object is created.
        //static and instance variables can be accessed
        System.out.println("Instance Initializer");
        i = 6;
        count = count + 1;
        System.out.println("Count when Instance Initializer is run is " + count);
    }

    public static void main(String[] args) {
        实例初始化块 example = new 实例初始化块();
        实例初始化块 example1 = new 实例初始化块();
        实例初始化块 example2 = new 实例初始化块();
    }
}
