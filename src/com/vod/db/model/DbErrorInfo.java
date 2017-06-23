package com.vod.db.model;

import com.vod.common.ErrorInfo;

public class DbErrorInfo extends ErrorInfo
{
    public static enum CODE{
        DB_INSERT_ERROR("DB insert fail {0}"),
        DB_UPDATE_ERROR("DB update fail {0}"),
        DB_DELETE_ERROR("DB delete fail {0}"),
        DB_FIND_ERROR("DB find fail {0}"),
        DB_PARAM_ERROR("DB parameter error {0}"),
        DB_COUNT_ERROR("DB count error {0}"),
        DB_SYSTEM_ERROR("DB system error {0}"),
        ;
        
        private String desc;
        CODE(String desc){
            this.setDesc(desc);
        }
        public String getDesc()
        {
            return desc;
        }
        public void setDesc(String desc)
        {
            this.desc = desc;
        }
    }

    
    protected DbErrorInfo(CODE code, ErrorInfo.SEVERITY severity, String... params)
    {
        super(code.name(),severity,populateDescription(code.desc,params));
    }
    protected DbErrorInfo(CODE code, ErrorInfo.SEVERITY severity, Throwable t,String... params)
    {
        super(code.name(),severity,t,populateDescription(code.desc,params));
    }
    
    public static DbErrorInfo newInstance(CODE code, ErrorInfo.SEVERITY severity, Throwable t,String... params)
    {
        return new DbErrorInfo(code, severity, t,params);
    }
    public static DbErrorInfo newInstance(CODE code, ErrorInfo.SEVERITY severity, Throwable t)
    {
        return new DbErrorInfo(code, severity, t);
    }    
    public static DbErrorInfo newInstance(CODE code, ErrorInfo.SEVERITY severity, String... params)
    {
        return new DbErrorInfo(code, severity, params);
    }

    public static DbErrorInfo newInstance(CODE code, ErrorInfo.SEVERITY severity) {
        return new DbErrorInfo(code,severity);
    }

}
