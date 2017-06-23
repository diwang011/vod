package 千里码;

public class 第2333个能被2或者被3整除的正整数是多少
{
    public static void main(String[] args)
    {
        int num = 0;
        for (int i = 1;; i++)
        {
            if (i % 2 == 0 || i % 3 == 0)
            {
                num++;
            }
            if (num == 2333)
            {
                System.out.println(i);
                break;
            }
        }
    }
}
