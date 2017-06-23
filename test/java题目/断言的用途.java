package java题目;

public class 断言的用途
{
    private  int computerSimpleInterest(int principal,float interest,int years){
        assert(principal>0);
        return 100;
    }
    public static void main(String[] args)
    {
        断言的用途 example=new 断言的用途();
        System.out.println(example.computerSimpleInterest(1, 1, 1));
    }
}
