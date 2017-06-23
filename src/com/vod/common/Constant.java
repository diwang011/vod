package com.vod.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;

@SuppressWarnings("deprecation")
public final class Constant
{

    final public static int NEED_SYNCH = 2; // 同步次数
    final public static int LONG_REQUEST_MS = 20000; //

    final public static String DEFAULT_PASSWORD = "asdasd123";
    final public static Long VIRTUAL_WAREHOUSE_ID = -1l;
    final public static String SESSIONUSER = "viewSessionUser";
    final public static String SYSTEM_ACCOUNT = "system.account";
    final public static String STRING_FORMAT_PLAN_CALC_PARAM_NAME = "format.plan.calcParamName";
    final public static String STRING_FORMAT_PLAN_CALC_RATE_NAME = "format.plan.calcRateName";
    final public static Long SystemOperatorId = -99999L;
    final public static String DefaultLanguage = "en-us";

    final public static String DEFAULT_PHONE_NUMBER = "000-000-0000";

    final public static int MicrosecondsInSec = 1000;
    final public static int SecondsInMinute = 60;
    final public static int MinutesInHour = 60;
    final public static int HoursInDay = 24;
    final public static int MicrosecondsInHour = 1000 * 60 * 60;    
    final public static String DateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
    final public static String UTCDateTimeFormatString = "yyyy-MM-dd'T'HH:mm:ss";
    final public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat(DateTimeFormatString);
    final public static SimpleDateFormat DateTimeFormatPST = new SimpleDateFormat(DateTimeFormatString);
    final public static SimpleDateFormat DateTimeFormatUTC = new SimpleDateFormat(UTCDateTimeFormatString);

    final public static String DateTimeENUSFormatString = "HH:mm:ss MMM dd, yyyy z";
    final public static SimpleDateFormat DateTimeENUSFormat = new SimpleDateFormat(DateTimeENUSFormatString, Locale.US);
    final public static SimpleDateFormat DateTimeFormatForFileName = new SimpleDateFormat("yyyyMMddHHmmss");
    final public static SimpleDateFormat DateTimeFormatWithMicroSecondForFileName = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS");
    final public static String DateFormatString = "yyyy-MM-dd";
    final public static SimpleDateFormat DateFormat = new SimpleDateFormat(DateFormatString);
    final public static SimpleDateFormat DateFormatUTC = new SimpleDateFormat(DateFormatString);
    final public static String DateFormatSelectString = "yyyy/MM/dd";
    final public static SimpleDateFormat DateFormatSelect = new SimpleDateFormat(DateFormatSelectString);
    final public static String DateHMFormatString = "yyyy-MM-dd HH:mm";
    final public static SimpleDateFormat DateHMFormat = new SimpleDateFormat(DateHMFormatString);
    final public static SimpleDateFormat DateFormatForFile = new SimpleDateFormat("yyyyMMdd");
    final public static SimpleDateFormat DateFormatMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");

    final public static String ShortDateFormatString = "MMM-dd-yy";
    final public static SimpleDateFormat ShortDateFormat = new SimpleDateFormat(ShortDateFormatString,Locale.ENGLISH);
    
    public static final String ShortDateFormatStringBeginDate = "dd-MMM-yy";
    // final private static Logger logger = Logger.getLogger(Constant.class);
    //
    // private static String ContextPath="";

    public static String ApplicationName = "vod";
    public static String ApplicationVersion = "4.0";
    public static String TsvSuffix = ".tsv";
    public static String CsvSuffix = ".csv";
    public static String TxtSuffix = ".txt";
    public static String xlsSuffix = ".xls";
    public static String xlsxSuffix = ".xlsx";
    public static String pdfSuffix = ".pdf";
    public static final double KG_PER_LB = 2.20462;
    public static final double KG_PER_OZS = 35.27396;
    public static final double INCH_PER_CM = 0.393701;
    public static final double FT_PER_CM = 0.0328084;
    public static final double CM_CBM = 1000000;
    

    public static String ApplicationContextPath;
    public static String ENCODING = "GBK";
    
    /**
     * Configure in xml/properties file instead
     * 应该配置在configure.propertise, 注入在xml里面
     */
    @Deprecated
    public final static String UploadPath = "/resources/upload/";
    /**
     * Configure in xml/properties file instead
     * 应该配置在configure.properties, 注入在xml里面
     */
    @Deprecated
    public final static String DownloadPath = "/home/omni/result/";
    public final static String FBA_PACKAGELABELS_NAME = "fbalabels";
    public final static String FBA_FILE_TYPE_ZIP = ".zip";
    public final static String FBA_FILE_TYPE_PDF = ".pdf";

    public final static Integer exportMaxLimit = 60000;
    public final static Integer exportDefaultLimit = 30;

    public final static String ENUMRATION_PREFIX = "Enum.";
    public static final String WAREHOUSE_ISSUE_TYPE = "WRHSISSUE";
    
    public final static String DASHBOARD_SPLIT = "|";
    public final static String REGEX_POSITIVE_INTEGER_NOT_EQUAL_TO_ZERO = "^[1-9]+[0-9]*$";
    
    public final static String SYSTEM_TIMEZONE_NAME = "US/Eastern";
    public final static TimeZone SYSTEM_TIMEZONE = TimeZone.getTimeZone(SYSTEM_TIMEZONE_NAME);
    
    public static Date BILLING_WAREHOUSE_RECHARGE_DATE = null;
    public final static int DEFAULT_QUERY_FROMDATE_MONTH = 3;
    public final static String PRODUCT_TYPE="Product";//产品类型
    public final static String CERTIFICATE ="Certificate";//证书类型
    public final static String AUDITING ="3";//产品审核状态
    public final static String ACTIVE ="1";//产品激活状态类型
    public final static String DISABLE ="2";//产品禁用状态
    public final static String EDASERVICE ="1";//产品导入EDA头程
    public final static String SPONTANEOUSSERVICE ="0";//产品导入自发头程
    public final static String EDASERVICEVALUE ="EDA";//产品导入EDA头程值
    public final static String SPONTANEOUSSERVICEVALUE ="SELF_TRANSPORT";//产品导入EDA头程值
    public final static String CHINESELANAUAGE ="zh-cn";//产品导入EDA头程值
    public final static String EXPORTCOUNTRY ="CN";//产品导入出口国家的值
    public final static String SKUVALIDATE ="[^a-zA-Z0-9]";
    //set system timezone
    public static final DefaultHolidayCalendar<Date> usHolidayCalendar;
    static {
        DateTimeFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateTimeFormatPST.setTimeZone(TimeZone.getTimeZone("PST"));
        DateTimeFormat.setTimeZone(SYSTEM_TIMEZONE);
        DateTimeFormatForFileName.setTimeZone(SYSTEM_TIMEZONE);
        DateTimeFormatWithMicroSecondForFileName.setTimeZone(SYSTEM_TIMEZONE);
        DateFormat.setTimeZone(SYSTEM_TIMEZONE);
        DateFormatForFile.setTimeZone(SYSTEM_TIMEZONE);
        DateFormatSelect.setTimeZone(SYSTEM_TIMEZONE);
        ShortDateFormat.setTimeZone(SYSTEM_TIMEZONE);
        DateHMFormat.setTimeZone(SYSTEM_TIMEZONE);
        DateFormatMMddyyyy.setTimeZone(SYSTEM_TIMEZONE);

        HashSet<Date> holidays = new HashSet<Date>();
        holidays.add(new Date(2015 - 1900, 11, 17)); //christmas 2015 12,25
        holidays.add(new Date(2015 - 1900, 11, 25)); //christmas 2015 12,25
        holidays.add(new Date(2016 - 1900, 0, 1));
        holidays.add(new Date(2016 - 1900, 0, 18));
        holidays.add(new Date(2016 - 1900, 1, 15));
        holidays.add(new Date(2016 - 1900, 4, 30));
        holidays.add(new Date(2016 - 1900, 6, 4));
        holidays.add(new Date(2016 - 1900, 8, 5));
        holidays.add(new Date(2016 - 1900, 9, 10));
        holidays.add(new Date(2016 - 1900, 10, 11));
        holidays.add(new Date(2016 - 1900, 10, 24));
        holidays.add(new Date(2016 - 1900, 11, 26));
        usHolidayCalendar = new DefaultHolidayCalendar<Date>(holidays); //for now
        
        TimeZone.setDefault(Constant.SYSTEM_TIMEZONE);
    }
    
    //fba手工补货---begin
    /**
     * fba手工文件子目录
     */
    public final static String FBA_MANUAL_FILES_DIR = "fbamanual/";
    /**
     * 分隔符
     */
    public final static String SPLIT_SEPERATOR = "|";
    public final static String SPLIT_SEPERATOR_ = "[|]";
    /**
     * package pdf
     */
    public final static String FBA_ATTACH_FILE_PACKAGE  = "package";
    /**
     * pallet pdf
     */
    public final static String FBA_ATTACH_FILE_PALLET  = "pallet";
    
    public final static int V4INK_USA_ACCOUNT_ID = 110;
    public final static int V4INK_CA_ACCOUNT_ID = 111;

}
