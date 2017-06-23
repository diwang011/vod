package javaConcurrent.ExecutorService;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class User implements Serializable, Callable<String>
{
    String userName;
    String password;

    public User(String user, String pwd)
    {
        this.userName = user;
        this.password = pwd;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String call() throws Exception
    {
        return "Task "+userName+password;
    }
}
