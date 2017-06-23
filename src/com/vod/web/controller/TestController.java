package com.vod.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vod.util.PerPageInfo;
import com.vod.util.Response;
import com.vod.web.model.ClientRequest;
import com.vod.web.service.ViewTestService;

@Controller
@RequestMapping("/")
public class TestController extends BaseController
{
    @Resource
    private ViewTestService viewTestService;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public @ResponseBody Response<PerPageInfo<String>> search(@RequestBody ClientRequest<String> req)
            throws Exception
    {
        traceGetIntoMethod("search(POST)");
        Response<PerPageInfo<String>> res = null;
        String viewInventoryRecordCondition = parseOmniRequestData(req, String.class);
        if (null == viewInventoryRecordCondition)
        {
            res = new Response<PerPageInfo<String>>();
            return res;
        }
        res = viewTestService.search();
        traceGetOffFromMethod("search(POST)");
        return res;
    }

}
