/**
 * 
 */
package com.vod.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author qiankaiyu
 *
 */
public class PropertiesReadUtil
{
    private static Logger logger = Logger.getLogger(PropertiesReadUtil.class);
    /**
     * @author junhu
     * @param filePath
     * @param key
     * @return
     */
    public static String readValue(String filePath, String key)
    {
        return read(filePath).getProperty(key);
    }
    
    public static Properties read(String filePath)
    {
        Properties props = new Properties();
        InputStream in = null;
        try
        {
            in = new FileInputStream(filePath);
            props.load(in);
        }
        catch (Exception e)
        {
            logger.error("readValue error: filePath=" + filePath , e);
        }finally{
            if(in != null)
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
        return props;
    }
    

    public static String fileTempDir()
    {
        Properties props = new Properties();
        String dirPath = null;
        try
        {
            InputStream in = new BufferedInputStream(
                    new FileInputStream(System.getProperty("omni.root") + "/WEB-INF/config/configure.properties"));
            props.load(in);
            dirPath = props.getProperty("path.for.generate.result");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dirPath;
    }
}
