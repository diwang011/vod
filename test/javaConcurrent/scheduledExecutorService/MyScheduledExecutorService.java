package javaConcurrent.scheduledExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class MyScheduledExecutorService
{
    public static void main(String[] args)
    {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        @SuppressWarnings("unchecked")
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable()
        {
            public Object call() throws Exception
            {
                System.out.println("Executed!");
                return "Called!";
            }
        }, 50, TimeUnit.SECONDS);
        try
        {
            System.out.println("result = " + scheduledFuture.get());
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scheduledExecutorService.shutdown();
    }

}
