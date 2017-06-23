package javaConcurrent.Thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.vod.db.model.UserInfoLog;
import com.vod.db.service.DbUserInfoLogService;
import com.vod.db.service.DbUserInfoService;
import com.vod.util.Response;

public class TestImpl implements ITest
{
    private DbUserInfoService dbUserInfoService;
    private DbUserInfoLogService dbUserInfoLogService;

    @Override
    public Boolean add(UserInfo userInfo)
    {
        com.vod.db.model.UserInfo user = copyBean(userInfo);
        Integer i = dbUserInfoService.insert(user);
        if (i == 1)
        {
            return true;
        }
        return false;
    }

    @Override
    public Boolean del(Integer id)
    {
        Integer i = dbUserInfoService.deleteById(id);
        if (i == 1)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<UserInfo> get()
    {
        List<UserInfo> list = new ArrayList<>();
        Response<List<com.vod.db.model.UserInfo>> response = dbUserInfoService.list();
        List<com.vod.db.model.UserInfo> userInfolist = response.getData();
        if (list != null && list.size() > 0)
        {
            for (com.vod.db.model.UserInfo userInfo : userInfolist)
            {
                UserInfo u = copyBean(userInfo);
                list.add(u);
            }
        }

        return list;
    }

    @Override
    public Boolean update(UserInfo userInfo)
    {
        com.vod.db.model.UserInfo record = copyBean(userInfo);
        Integer i = dbUserInfoService.update(record);
        if (i == 1)
        {
            UserInfoLog log=new UserInfoLog();
            log.setAmount(record.getAmount());
            log.setUserInfoId(record.getId());
            log.setTime(new Date());
            dbUserInfoLogService.insert(log);
            return true;
        }
        return false;
    }

    public void setDbUserInfoService(DbUserInfoService dbUserInfoService)
    {
        this.dbUserInfoService = dbUserInfoService;
    }

    @Override
    public UserInfo getById(Integer id)
    {
        Response<com.vod.db.model.UserInfo> response = dbUserInfoService.getById(id);
        com.vod.db.model.UserInfo userInfo = response.getData();
        UserInfo u = null;
        if (userInfo != null)
        {
            u = copyBean(userInfo);

        }
        return u;
    }

    private com.vod.db.model.UserInfo copyBean(UserInfo fromBean)
    {
        com.vod.db.model.UserInfo toBean = new com.vod.db.model.UserInfo();
        BeanUtils.copyProperties(fromBean, toBean);
        return toBean;
    }

    private UserInfo copyBean(com.vod.db.model.UserInfo fromBean)
    {
        UserInfo toBean = new UserInfo();
        BeanUtils.copyProperties(fromBean, toBean);
        return toBean;
    }

    public void setDbUserInfoLogService(DbUserInfoLogService dbUserInfoLogService)
    {
        this.dbUserInfoLogService = dbUserInfoLogService;
    }
}
