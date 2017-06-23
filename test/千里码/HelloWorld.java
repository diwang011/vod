package 千里码;

public class HelloWorld
{
    public static void main(String[] args)
    {
        t1();//ASCII转换为字符串

        t2();//字符串转换为ASCII码
    }

    public static void t1()
    {//ASCII转换为字符串

        String s = "72 101 108 108 111 44 32 87 111 114 108 100 33";//ASCII码

        String[] chars = s.split(" ");
        System.out.println("ASCII 汉字 \n----------------------");
        for (int i = 0; i < chars.length; i++)
        {
            System.out.print((char) Integer.parseInt(chars[i]));
        }
    }

    public static void t2()
    {//字符串转换为ASCII码

        String s = "Hello, World!";//字符串

        char[] chars = s.toCharArray(); //把字符中转换为字符数组 

        System.out.println("\n\n汉字 ASCII\n----------------------");
        for (int i = 0; i < chars.length; i++)
        {//输出结果

            System.out.print((int) chars[i]+" ");
        }
    }
}
