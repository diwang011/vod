package javaConcurrent.ArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javaConcurrent.BlockingQueue.Consumer;
import javaConcurrent.BlockingQueue.Producer;

public class ArrayBlockingQueueExample
{
    public static void main(String[] args) throws Exception
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);  
        queue.put("1");
        String string = queue.take(); 
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }
}
