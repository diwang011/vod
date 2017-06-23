package com.vod.common.util.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils
{
    //连接超时时间，默认120秒
    private static int socketTimeout = 120000;
    //传输超时时间，默认360秒
    private static int connectTimeout = 360000;

    public static String doHttpGet(String url, Map<String, Object> parameterMap, Map<String, Object> optionalMap)
            throws IOException, ClientProtocolException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        try
        {

            url = keyValue2Url(keyValue2Url(url, parameterMap, true), optionalMap, false);

            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(requestConfig);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>()
            {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
                {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300)
                    {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    }
                    else
                    {
                        throw new ClientProtocolException("Unexpected response status: " + status + " reason: "
                                + response.getStatusLine().getReasonPhrase());
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            return responseBody;
        }
        finally
        {
            httpclient.close();
        }
    }

    private static String keyValue2Url(String url, Map<String, Object> parameterMap, boolean flag)
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
            if (sb != null && sb.length() > 0)
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
            if (sb != null && sb.length() > 0)
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

    public static String doHttpPost(String url, Map<String, Object> map, Map<String, Object> optionalMap)
            throws ClientProtocolException, IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        try
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (null != map)
            {
                for (Map.Entry<String, Object> entry : map.entrySet())
                {
                    nvps.add(new BasicNameValuePair(entry.getKey(), (String) (null == entry.getValue() ? "" : entry
                            .getValue())));
                }
            }
            if (null != optionalMap)
            {
                for (Map.Entry<String, Object> omap : optionalMap.entrySet())
                {
                    if (null != omap.getValue())
                    {
                        nvps.add(new BasicNameValuePair(omap.getKey(), (String) (null == omap.getValue() ? "" : omap
                                .getValue())));
                    }
                }
            }

            HttpEntity encodedUrl = new UrlEncodedFormEntity(nvps);
            httpPost.setEntity(encodedUrl);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try
            {
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                return str;
            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
    }

}
