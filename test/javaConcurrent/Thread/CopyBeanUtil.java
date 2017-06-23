package javaConcurrent.Thread;

import net.sf.cglib.beans.BeanCopier;

public  class CopyBeanUtil
{
    public static Object copyBean(Object toBean)
    {
        Object fromBean=new Object();
        BeanCopier bc = BeanCopier.create(Object.class, Object.class,
                false);
        bc.copy(toBean, fromBean, null);
        return toBean;
    }
}
