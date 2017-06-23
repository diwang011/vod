package junit;


import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.vod.util.PerPageInfo;
import com.vod.util.Response;
import com.vod.web.service.ViewTestService;


public class TestWms extends AbstractJUnitTest
{
    @Resource
    private ViewTestService viewTestService;

    @Test
    @Rollback(false)
    public void search()
    {
        Response<PerPageInfo<String>> res= viewTestService.search();
        System.out.println(res.getData().getRows());
    }

    
}
