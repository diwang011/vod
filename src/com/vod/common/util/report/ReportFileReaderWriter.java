package com.vod.common.util.report;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class ReportFileReaderWriter<T extends Reportable>
{
    private String datePatternString = "yyyy-MM-dd";
    private String dateTimePatternString = "yyyy-MM-dd HH:mm:ss";
    private String extraDatePatternString;

    private SimpleDateFormat datePattern = new SimpleDateFormat(datePatternString);
    private SimpleDateFormat datetimePattern = new SimpleDateFormat(dateTimePatternString);

    protected final Logger logger;

    public ReportFileReaderWriter()
    {
        this(null, null);
    }

    public ReportFileReaderWriter(String datePattern, String datetimePattern)
    {
        logger = Logger.getLogger(this.getClass());
        if (datePattern != null && datePattern.length() > 0)
        {
            try
            {
                this.datePattern = new SimpleDateFormat(datePattern);
            }
            catch (Exception e)
            {
                logger.error("ReportFileReaderWriter datePattern=" + datePattern + e.getMessage(), e);
                this.datePattern = new SimpleDateFormat(datePatternString);
            }
        }
        if (datetimePattern != null && datetimePattern.length() > 0)
        {
            try
            {
                this.datetimePattern = new SimpleDateFormat(datetimePattern);
            }
            catch (Exception e)
            {
                logger.error("ReportFileReaderWriter datetimePattern=" + datetimePattern + e.getMessage(), e);
                this.datetimePattern = new SimpleDateFormat(dateTimePatternString);
            }
        }
    }

    protected SimpleDateFormat getDateTimeFormat()
    {
        return datetimePattern;
    }

    protected SimpleDateFormat getDateFormat()
    {
        return datePattern;
    }

    public String getExtraDatePatternString()
    {
        return extraDatePatternString;
    }

    public void setExtraDatePatternString(String extraDatePatternString)
    {
        this.extraDatePatternString = extraDatePatternString;
    }

    abstract public List<T> readReportFile(Class<T> rowClass, String fileName) throws Exception;

    abstract public void writeReportFile(List<T> content, String fileName);
    
    abstract public void writeReportFile(Map<String,List<? extends Reportable>> multiConentMap, String fileName);
}
