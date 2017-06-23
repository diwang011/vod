package 反射;

import java.lang.reflect.Method;

public class Test
{
    public static void main(String[] args)
    {
        ClassDemo demo=new ClassDemo();
        Class cl= demo.getClass();
        Method[] m1= cl.getDeclaredMethods();
//        for (Method method : m1)
//        {
//            System.out.println(method.getName());
//        }
        
        try
        {
            Method m2= cl.getDeclaredMethod("Fun",null);
            m2.invoke(demo, args);
            
            Method m3= cl.getDeclaredMethod("connectionPool",String.class);
            m3.invoke(demo, "TACK");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
