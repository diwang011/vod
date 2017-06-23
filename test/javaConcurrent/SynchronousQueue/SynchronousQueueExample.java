package javaConcurrent.SynchronousQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample
{
    public static void main(String[] args) throws Exception
    {
        BlockingQueue queue = new SynchronousQueue();

        //String implements java.lang.Comparable  
        queue.put("Value");

        Object value = queue.take();
    }
}
