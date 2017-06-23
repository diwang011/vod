package javaConcurrent.Thread;

import java.util.concurrent.*;

import javax.annotation.Resource;

import org.springframework.test.annotation.Rollback;

import javaConcurrent.ConcurrentHashMap.ConcurrentHashMapExample;
import junit.AbstractJUnitTest;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Test extends AbstractJUnitTest
{
    @Resource
    private TestImpl it;

    @org.junit.Test
    @Rollback(false)
    public void testTask()
    {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();

        int taskSize = 1000;
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++)
        {
            Callable c = new MyCallable(i + " ", it);
            Future f = pool.submit(c);
            list.add(f);
        }
        pool.shutdown();

        for (Future f : list)
        {
            try
            {
                System.out.println(">>>" + f.get().toString());
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
        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【" + (date2.getTime() - date1.getTime()) + "毫秒】");

    }
}

class MyCallable implements Callable<Object>
{
    private String taskNum;
    private TestImpl it;

    MyCallable(String taskNum, TestImpl it)
    {
        this.taskNum = taskNum;
        this.it = it;
    }

    public Object call() throws Exception
    {
        Boolean b = update();
        return taskNum + b;
    }

    private Boolean add()
    {
        //System.out.println(">>>" + taskNum + "任务启动");
        //Thread.sleep(1000);
        String name = new Random().nextInt(9000) + "";
        UserInfo userInfo = new UserInfo(name, 1000.000);
        Boolean b = it.add(userInfo);
        //System.out.println(">>>" + taskNum + "任务终止");
        return b;
    }

    private Boolean update()
    {
        Boolean b;
        Integer id =  new Random().nextInt(90)+1;
        synchronized (ConcurrentHashMapExample.getSyncObjectFor(id+""))
        {
            UserInfo userInfo = it.getById(id);
            if (userInfo==null)
            {
                System.out.println("error...->>>"+id);
            }
            userInfo.setAmount(userInfo.getAmount() - 10);
            b = it.update(userInfo);
        }
        return b;
    }
}