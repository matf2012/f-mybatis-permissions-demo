package com.f.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.f.demo.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tang.shi on 2019/01/09.
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static CloseableHttpClient httpclient;
    private final static String DEF_CHARSET = "UTF-8";
    private final static int DEF_TIMEOUT = 5000;

    static {
        // 启用连接线程池，最大150个连接
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(150);
        cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(5000).build();
        cm.setDefaultSocketConfig(socketConfig);
        // 默认超时    ConnectionRequestTimeout连接池超时     ConnectTimeout建立连接超时     SocketTimeout读取数据超时
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).setCookieSpec(null).build();
        httpclient = HttpClients.custom().setDefaultRequestConfig(config).setConnectionManager(cm).build();

        //空闲连接清理
        IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(cm, 60, TimeUnit.SECONDS, 30, TimeUnit.SECONDS);
        idleConnectionEvictor.start();
    }

    /**
     * HTTP发送
     *
     * @param url         地址
     * @param postcontent 发送报文
     * @param charset     编码集
     * @param headerMap   请求头信息
     * @param timeout     超时时间
     * @return 返回报文
     */
    public static String post(String url, String postcontent, String charset, Map<String, String> headerMap, int timeout) throws BusinessException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            byte[] data = postcontent.getBytes(charset);

            if (data != null) {
                ByteArrayEntity byteEntity = new ByteArrayEntity(data);
                httpPost.setEntity(byteEntity);
            }
            if (headerMap != null) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            RequestConfig localConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(timeout).setCookieSpec(null).build();
            httpPost.setConfig(localConfig);

            response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new BusinessException("HTTP请求异常，异常代码为" + response.getStatusLine().getStatusCode());
            }

            byte[] rtnData = EntityUtils.toByteArray(response.getEntity());

            result = new String(rtnData, charset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("发送HTTP请求异常" + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postForString(String url, Object param) {
        return postForString(url, param, null, 5000);
    }

    public static <T> T postForObject(String url, Object param, Class<T> c) {
        return postForObject(url, param, null, 5000, c);
    }

    public static <T> T postForObject(String url, Object param, Map<String, String> headerMap, int timeOut, Class<T> c) {
        String result = postForString(url, param, headerMap, timeOut);

        if (result != null) {
            return JSONObject.parseObject(result, c);
        } else {
            return null;
        }
    }

    public static String postForString(String url, Object param, Map<String, String> headerMap, int timeOut) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.addHeader("Accept", "application/json");
            if (headerMap != null) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            String jsonParam = "";
            if (param != null) {
                jsonParam = JSONObject.toJSONString(param);
                HttpEntity entity = new StringEntity(jsonParam);
                httpPost.setEntity(entity);
            }
            RequestConfig localConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
                    .setSocketTimeout(timeOut).setCookieSpec(null).build();
            httpPost.setConfig(localConfig);

            response = httpclient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
            logger.debug("HTTP POST :");
            logger.debug("url:" + url);
            logger.debug("param:" + jsonParam.substring(0,1024));
            logger.debug("result:" + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP发送get请求
     *
     * @param url
     * @param referer
     * @return
     */
    public static String get(String url, String referer) throws BusinessException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            if(StringUtils.isNotBlank(referer)){
                httpGet.setHeader("Referer",referer);
            }
            RequestConfig localConfig = RequestConfig.custom().setConnectionRequestTimeout(DEF_TIMEOUT).setConnectTimeout(DEF_TIMEOUT).setSocketTimeout(DEF_TIMEOUT).setCookieSpec(null).build();
            httpGet.setConfig(localConfig);
            response = httpclient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new BusinessException("HTTP请求异常，异常代码为" + response.getStatusLine().getStatusCode());
            }

            byte[] rtnData = EntityUtils.toByteArray(response.getEntity());

            result = new String(rtnData, DEF_CHARSET);
        } catch (Exception e) {
            throw new BusinessException("发送HTTP请求异常" + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static InputStream postFile(String url, Object param, Map<String, String> headerMap, int timeOut) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.addHeader("Accept", "application/json");
            if (headerMap != null) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            String jsonParam = "";
            if (param != null) {
                jsonParam = JSONObject.toJSONString(param);
                HttpEntity entity = new StringEntity(jsonParam);
                httpPost.setEntity(entity);
            }
            RequestConfig localConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
                    .setSocketTimeout(timeOut).setCookieSpec(null).build();
            httpPost.setConfig(localConfig);

            response = httpclient.execute(httpPost);
            InputStream is = response.getEntity().getContent();
            logger.debug("HTTP POST :");
            logger.debug("url:" + url);
            logger.debug("param:" + jsonParam);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP发送get请求
     *
     * @param url
     * @param timeout
     * @return
     */
    public static InputStream getFile(String url, int timeout) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            RequestConfig localConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(timeout).setCookieSpec(null).build();
            httpGet.setConfig(localConfig);
            response = httpclient.execute(httpGet);
            InputStream is = response.getEntity().getContent();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
