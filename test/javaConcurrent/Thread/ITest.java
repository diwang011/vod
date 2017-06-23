package javaConcurrent.Thread;

import java.util.List;

public interface ITest
{
    public Boolean add(UserInfo userInfo);
    public Boolean update(UserInfo userInfo);
    public Boolean del(Integer id);
    public List<UserInfo> get();
    public UserInfo getById(Integer id);
}
