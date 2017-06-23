package javaConcurrent.ConcurrentNavigableMap;

import java.util.concurrent.ConcurrentNavigableMap;

public class Task implements Runnable
{
    private ConcurrentNavigableMap<String, Contact> map;

    private String id;

    public Task(ConcurrentNavigableMap<String, Contact> map, String id)
    {
        super();
        this.map = map;
        this.id = id;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 1000; i++)
        {
            Contact contact = new Contact(id, String.valueOf(i + 1000));
            map.put(id + contact.getPhone(), contact);
        }
    }

}
