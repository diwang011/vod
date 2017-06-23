package Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public abstract class TestEnvironment
{
    private String basePath = "/"+System.getProperty("user.dir");
    private String configPath = "/WebContent/WEB-INF/config/";
    private String log4JConfigFile = "log4j.properties";
    protected final Logger logger;

    protected void exec()
    {
        logger.info("==== begin to initial necessary data. ====");
        boolean initSuccessfully = init();
        logger.info("==== initial end ====");
        if(!initSuccessfully)
        {
            logger.info("Something wrong...");
            return;
        }
        logger.info("==== Begin ====");
        long startTime=System.currentTimeMillis();
        
        testTask();
        
        long endTime=System.currentTimeMillis();
        logger.info("Runing time :" + (endTime - startTime) + "ms");
        logger.info("==== End ====");
    }
    
    protected boolean init()
    {
        return true;
    }
    
    protected abstract void testTask();
    
    public TestEnvironment()
    {
        PropertyConfigurator.configure(basePath + configPath+ log4JConfigFile);
        logger = Logger.getLogger(this.getClass());
    }
    
    protected Logger getLogger()
    {
        return logger;
    }
    protected String getBasePath()
    {
        return basePath;
    }
    protected String getTestDataPath()
    {
        return basePath+"/testdata/";
    }
    
    protected String getInitDataPath()
    {
        return basePath+"/initdata/";
    }
    
    public void deply()
    {
        try
        {
            Thread.sleep(300);
        }
        catch (InterruptedException e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    protected String htmlCall(String requestUrl, String type, String requestString, String responseFileName)
    {
        if(requestUrl==null || requestUrl.length()==0)
        {
            logger.info("No request URL.");
            return null;
        }
        if(type==null || type.length()==0)
        {
            logger.info("No request type.");
            return null;
        }
        logger.info("Will call "+requestUrl+", with "+type +" data: "+requestString);
        if(responseFileName!=null && responseFileName.length()>0)
        {
            logger.info("the result will be saved into: "+responseFileName);
        }
        try
        {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/" + type);
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            connection.setRequestProperty("Content-Length", "" + requestString.length());
            StringReader br = new StringReader(requestString);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            for (;;)
            {
                int c = br.read();
                if (c < 0)
                    break;
                wr.writeByte(c);
            }
            br.close();
            wr.flush();
            // Get the response
            logger.info("response code: " + connection.getResponseCode());

            FileWriter fos = null;
            StringWriter sw = new StringWriter();
            if(responseFileName!=null && responseFileName.length()>0)
            {
                fos = new FileWriter(responseFileName);
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                if(fos!=null)
                {
                    fos.write(line);
                    sw.write(line);
                }
                logger.info(line);
            }
            wr.close();
            rd.close();
            if(fos!=null)
            {
                fos.flush();
                fos.close();
            }
            connection.disconnect();
            sw.close();
            return sw.toString();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
