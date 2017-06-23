package com.vod.common.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * From http://stackoverflow.com/a/27806218/6370101 
 * @author rogerdpack
 *
 */
public abstract class JvmLock
{
    private static final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    public static Object getSyncObjectFor(String id)
    {
        locks.putIfAbsent(id, new Object());
        return locks.get(id);

        //        synchronized (locks)
        //        {
        //            Object lock = locks.get(id);
        //            if (lock == null)
        //            {
        //                lock = new Object();
        //                locks.put(id, lock);
        //            }
        //            return lock;
        //        }
    }
}
