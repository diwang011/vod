package javaConcurrent.ExecutorService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyExecutorService
{
    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //        executorService.execute(new Runnable()
        //        {
        //            public void run()
        //            {
        //                System.out.println("Asynchronous task");
        //            }
        //        });
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        for (int i = 0; i < 1000; i++)
        {
            User u=new User("u"+i,"");
            callables.add(u);
        }
        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        concurrentMap.put("key", "value");
        Object value = concurrentMap.get("key");
        List<Future<String>> futures;
        try
        {
            String result = executorService.invokeAny(callables);
            System.out.println("result = " + result);
            futures = executorService.invokeAll(callables);
            for (Future<String> future : futures)
            {
                System.out.println("future.get = " + future.get());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
