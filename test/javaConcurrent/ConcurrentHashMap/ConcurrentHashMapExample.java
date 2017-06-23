package javaConcurrent.ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapExample
{
    public static void main(String[] args)
    {
        ConcurrentMap concurrentMap =new ConcurrentHashMap();
        concurrentMap.put("key", "value");
        Object value = concurrentMap.get("key"); 
    }

    private static final ConcurrentHashMap<String, Object> maps = new ConcurrentHashMap<>();

    public static Object getSyncObjectFor(String id)
    {
        maps.putIfAbsent(id, new Object());
        return maps.get(id);

    }

    
}
