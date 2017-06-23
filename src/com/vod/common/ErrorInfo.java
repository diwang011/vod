package com.vod.common;

import java.util.Calendar;

import org.apache.log4j.Logger;

public  class ErrorInfo
{
    private String code;
    private String desc;
    private SEVERITY severity;
    private String language;
    private String category;                            //错误代码层级
    /**
     * 发生错误的ref
     */
    private String refNum;
    
    public static enum SEVERITY{INFO,WARN,ERROR};
    
    protected final Logger logger = Logger.getLogger(this.getClass());
    
    public ErrorInfo(){}
    
    protected ErrorInfo(String code, SEVERITY severity, Throwable t, String desc)
    {
        String ref = "ref#" + Calendar.getInstance().getTimeInMillis();
        //desc = ref + "/" + desc; //legacy
        //log on error
        switch (severity){
        case ERROR:
            logger.error(code + ": " + desc, t);
            break;
        case INFO:
            logger.info(code + ": " + desc, t);
            break;
        case WARN:
            logger.warn(code + ": " + desc, t);
            break;
        default:
            break;
            
        }
        this.code = code;
        this.desc = desc;
        this.refNum = ref;
        this.severity = severity;
    }
    
    protected static String populateDescription(String desc,String... params){
        for (int i=0; params != null && i<params.length;i++) {
            String param = params[i];
            if (param != null)
                desc = desc.replaceFirst("\\{"+i+"\\}", param);
        }
        return desc;
    }
    protected static String populateDescription(String desc, Throwable t)
    {
        String msg = t.toString();
        msg += t.getMessage() != null ? ": " + t.getMessage() : "";
        if (desc.indexOf("{0}")!=-1) 
        {
            desc = desc.replaceFirst("\\{0\\}", msg);
        } else {
            desc += " "+ msg;
        }
        return desc;
    }    
    @Deprecated
    protected ErrorInfo(String code, String desc)
    {
        this(code,SEVERITY.ERROR,desc);
    }
    
    protected ErrorInfo(String name, SEVERITY severity, String desc)
    {
        this(name, severity,new Throwable(desc),desc);
    }

    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getDesc()
    {
        return desc;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    @Override
    public String toString()
    {
        return refNum + "@[" + code + ": " + desc + "]";
    }
    public SEVERITY getSeverity()
    {
        return severity;
    }
    public void setSeverity(SEVERITY severity)
    {
        this.severity = severity;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getRefNum()
    {
        return refNum;
    }

    public void setRefNum(String refNum)
    {
        this.refNum = refNum;
    }
    
    
    
}
