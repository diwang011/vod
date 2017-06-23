package javaConcurrent.DelayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedElement implements Delayed
{

    @Override
    public int compareTo(Delayed o)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {
        return unit.convert(10000, TimeUnit.NANOSECONDS);
    }

}
