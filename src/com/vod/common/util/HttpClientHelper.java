package com.vod.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http get&post respose string(json,xml)
 * 
 * @author vod
 * 
 */
public class HttpClientHelper
{
    private static Logger log = Logger.getLogger(HttpClientHelper.class);
    public static final String HEAD = "head";
    public static final String REQUIRED = "required";
    public static final String OPTIONAL = "optional";

    private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

    /**
     * doHttpGet
     * 
     * @param url
     * @return
     * @throws Exception 
     */
    public String doHttpGet(String url) throws Exception
    {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            if (null != map)
            {

                url = keyValue2Url(keyValue2Url(url, map.get(REQUIRED), true), map.get(OPTIONAL), false);
            }
            HttpGet httpget = new HttpGet(url);
            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpget.addHeader(head.getKey(), "" + head.getValue());
                }
            }
            log.info("Executing request " + httpget.getRequestLine());
            final Status s = new Status();
            ResponseHandler<String> responseHandler = new ResponseHandler<String>()
            {
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                {
                    int status = response.getStatusLine().getStatusCode();
                    s.setStatus(status);
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;

                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            log.info("--------------------response status: " + s.getStatus() + "--------------------");
            log.info(responseBody);
            return responseBody;
        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                log.error("close exception " + e);
            }
        }
    }

    /**
     * doHttpDelete
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public String doHttpDelete(String url) throws Exception
    {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            if (null != map)
            {

                url = keyValue2Url(keyValue2Url(url, map.get(REQUIRED), true), map.get(OPTIONAL), false);
            }

            HttpDelete httpDelete = new HttpDelete(url);
            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpDelete.addHeader(head.getKey(), "" + head.getValue());
                }
            }
            log.info("Executing request " + httpDelete.getRequestLine());
            final Status s = new Status();
            ResponseHandler<String> responseHandler = new ResponseHandler<String>()
            {
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                {
                    int status = response.getStatusLine().getStatusCode();
                    s.setStatus(status);

                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;

                }
            };
            String responseBody = httpclient.execute(httpDelete, responseHandler);
            log.info("--------------------response status: " + s.getStatus() + "--------------------");
            log.info(responseBody);
            return responseBody;
        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                log.error("close exception " + e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param strEntity
     * @return
     * @throws Exception
     */
    public String doHttpPost(String url, String strEntity) throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost(url);

            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpPost.addHeader(head.getKey(), "" + head.getValue());
                }
            }

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (null != map && null != map.get(REQUIRED))
            {
                for (Map.Entry<String, Object> entry : map.get(REQUIRED).entrySet())
                {
                    nvps.add(new BasicNameValuePair(entry.getKey(),
                            "" + (null == entry.getValue() ? "" : entry.getValue())));
                }
            }
            if (null != map && null != map.get(OPTIONAL))
            {
                for (Map.Entry<String, Object> omap : map.get(OPTIONAL).entrySet())
                {
                    if (null != omap.getValue())
                    {
                        nvps.add(new BasicNameValuePair(omap.getKey(),
                                "" + (null == omap.getValue() ? "" : omap.getValue())));
                    }
                }
            }
            CloseableHttpResponse response = null;
            try
            {
                if (null != strEntity && !"".equals(strEntity))
                {
                    StringEntity se = new StringEntity(strEntity, "utf-8");
                    // se.setContentType("text/xml");
                    httpPost.setEntity(se);
                }
                else
                {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                }
                response = httpclient.execute(httpPost);
                log.info(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                log.info(str);
                return str;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                try
                {
                    response.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param url
     * @param strEntity
     * @return
     * @throws Exception
     */
    public Object doHttpPost(String url, String strEntity, ResponseHandler<Object> responseHandler) throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost(url);

            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpPost.addHeader(head.getKey(), "" + head.getValue());
                }
            }

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (null != map && null != map.get(REQUIRED))
            {
                for (Map.Entry<String, Object> entry : map.get(REQUIRED).entrySet())
                {
                    nvps.add(new BasicNameValuePair(entry.getKey(),
                            "" + (null == entry.getValue() ? "" : entry.getValue())));
                }
            }
            if (null != map && null != map.get(OPTIONAL))
            {
                for (Map.Entry<String, Object> omap : map.get(OPTIONAL).entrySet())
                {
                    if (null != omap.getValue())
                    {
                        nvps.add(new BasicNameValuePair(omap.getKey(),
                                "" + (null == omap.getValue() ? "" : omap.getValue())));
                    }
                }
            }
            try
            {
                if (null != strEntity && !"".equals(strEntity))
                {
                    StringEntity se = new StringEntity(strEntity, "utf-8");
                    // se.setContentType("text/xml");
                    httpPost.setEntity(se);
                }
                else
                {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                }
                return httpclient.execute(httpPost, responseHandler);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            // catch (ClientProtocolException e)
            // {
            // e.printStackTrace();
            // }
            // catch (IOException e)
            // {
            // e.printStackTrace();
            // }
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param url
     * @param parameterMap
     * @param flag
     *            =true REQUIRED, false optional
     * @return
     */
    private String keyValue2Url(String url, Map<String, Object> parameterMap, boolean flag)
    {
        StringBuilder sb = new StringBuilder("");
        if (flag)
        {
            if (null != parameterMap)
            {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet())
                {
                    sb.append(entry.getKey()).append("=").append(null == entry.getValue() ? "" : entry.getValue())
                            .append("&");
                }
            }
            if (sb.length() > 0)
            {
                if (url.indexOf("?") != -1)
                {
                    url += sb.toString();
                }
                else
                {
                    url += "?" + sb.toString();
                }
            }
        }
        else
        {
            if (null != parameterMap)
            {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet())
                {
                    if (null != entry.getValue())
                    {
                        sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                }
            }
            if (sb.length() > 0)
            {

                if (url.indexOf("?") != -1)
                {
                    url += sb.substring(0, sb.length() - 1);
                }
                else
                {
                    url += "?" + sb.substring(0, sb.length() - 1);
                }
            }
        }
        return url;
    }

    /**
     * 
     * @param url
     * @param fileName
     * @return
     */
    public byte[] doHttpPostAndSaveFile(String url, String fileName)
    {
        Map<String, Object> requiredMap = map.get(REQUIRED);
        Map<String, Object> optionalMap = map.get(OPTIONAL);
        byte[] content = new byte[0];
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (null != requiredMap)
            {
                for (Map.Entry<String, Object> entry : requiredMap.entrySet())
                {
                    nvps.add(new BasicNameValuePair(entry.getKey(),
                            "" + (null == entry.getValue() ? "" : entry.getValue())));
                }
            }
            if (null != optionalMap)
            {
                for (Map.Entry<String, Object> omap : optionalMap.entrySet())
                {
                    if (null != omap.getValue())
                    {
                        nvps.add(new BasicNameValuePair(omap.getKey(),
                                "" + (null == omap.getValue() ? "" : omap.getValue())));
                    }
                }
            }
            CloseableHttpResponse response = null;
            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                response = httpclient.execute(httpPost);
                log.info(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toByteArray(entity);

            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    response.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return content;
    }

    public byte[] doHttpGetAndSaveFile(String url, String fileName)
    {
        byte[] content = new byte[0];
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            if (null != map)
            {

                url = keyValue2Url(keyValue2Url(url, map.get(REQUIRED), true), map.get(OPTIONAL), false);
            }

            HttpGet httpget = new HttpGet(url);
            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpget.addHeader(head.getKey(), "" + head.getValue());
                }
            }
            log.info("Executing request " + httpget.getRequestLine());
            final Status s = new Status();
            ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>()
            {
                public byte[] handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                {
                    int status = response.getStatusLine().getStatusCode();
                    s.setStatus(status);
                    log.info("--------------------response status: " + status + "--------------------");
                    HttpEntity entity = response.getEntity();
                    return EntityUtils.toByteArray(entity);

                }
            };
            content = httpclient.execute(httpget, responseHandler);

            return content;
        }
        catch (ClientProtocolException e)
        {
            log.error(e);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                log.error("close exception " + e);
            }
        }
        return null;
    }

    /**
     * 
     * @param key
     * @param value
     * @param flag
     *            true---->(http parameter required) false------>(http parameter optional)
     */
    public HttpClientHelper parameter2Http(String key, Object value, boolean flag)
    {
        if (flag)
        {
            if (null == map.get(REQUIRED))
            {
                map.put(REQUIRED, new HashMap<String, Object>());
            }
            map.get(REQUIRED).put(key, value);
        }
        else
        {
            if (null == map.get(OPTIONAL))
            {
                map.put(OPTIONAL, new HashMap<String, Object>());
            }
            map.get(OPTIONAL).put(key, value);
        }
        return this;
    };

    /**
     * 
     * @param key
     * @return
     */
    public String removeFromHeader(String key)
    {
        String value = null;
        if (null == map.get(HEAD))
        {
            map.put(HEAD, new HashMap<String, Object>());
            return null;
        }
        if (null != map.get(HEAD).get(key))
        {
            value = "" + map.get(HEAD).remove(key);
        }

        return value;
    };

    /**
     * http head parameter eg:"Content-Type", "text/xml"
     * 
     * @param key
     * @param value
     */
    public HttpClientHelper parameter2Head(String key, Object value)
    {
        if (null == map)
        {
            throw new NullPointerException();
        }

        if (null == map.get(HEAD))
        {
            map.put(HEAD, new HashMap<String, Object>());
        }
        map.get(HEAD).put(key, value);
        return this;
    };

    public static void write2File(InputStream input, String fileName)
    {
        PrintStream ps = null;
        BufferedInputStream bis = null;
        try
        {
            bis = new BufferedInputStream(input);
            ps = new PrintStream(new FileOutputStream(new File(fileName)));
            int readBytes = 0;
            while ((readBytes = bis.read()) != -1)
            {
                ps.write(readBytes);
            }
            ps.close();
            bis.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setMap(Map<String, Map<String, Object>> map)
    {
        this.map = map;
    }

    public static void main(String[] args) throws Exception
    {

    }

    class Status
    {
        private int status;

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }
    }

    public byte[] doHttpGetLabel(String url) throws Exception
    {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            if (null != map)
            {

                url = keyValue2Url(keyValue2Url(url, map.get(REQUIRED), true), map.get(OPTIONAL), false);
            }
            HttpGet httpget = new HttpGet(url);
            if (null != map && null != map.get(HEAD))
            {
                for (Map.Entry<String, Object> head : map.get(HEAD).entrySet())
                {
                    httpget.addHeader(head.getKey(), "" + head.getValue());
                }
            }
            log.info("Executing request " + httpget.getRequestLine());
            final Status s = new Status();
            ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>()
            {
                public byte[] handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                {
                    int status = response.getStatusLine().getStatusCode();
                    s.setStatus(status);
                    HttpEntity entity = response.getEntity();
                    if (entity != null)
                    {
                        InputStream inStream = entity.getContent();
                        return inStream != null ? input2byte(inStream) : null;
                    }
                    return null;
                }
            };
            byte[] responseBody = httpclient.execute(httpget, responseHandler);
            log.info("--------------------response status: " + s.getStatus() + "--------------------");
            log.info(responseBody);
            return responseBody;
        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                log.error("close exception " + e);
            }
        }
    }

    public static final byte[] input2byte(InputStream inStream) throws IOException
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0)
        {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
