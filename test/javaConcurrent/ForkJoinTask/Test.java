package javaConcurrent.ForkJoinTask;
import java.util.concurrent.*;
public class Test
{
    public static void main(String[] args)
    {
        ForkJoinPool forkJoinPool=new ForkJoinPool(100);  
        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);  
        
        long mergedResult = forkJoinPool.invoke(myRecursiveTask);  
          
        System.out.println("mergedResult = " + mergedResult);
    }
}
