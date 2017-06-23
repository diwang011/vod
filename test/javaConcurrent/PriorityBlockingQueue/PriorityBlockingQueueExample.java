package javaConcurrent.PriorityBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample
{
    public static void main(String[] args) throws Exception
    {
        BlockingQueue queue = new PriorityBlockingQueue();

        //String implements java.lang.Comparable  
        queue.put("Value");

        String value = (String) queue.take();
    }
}
