package com.matrictime.network.util;

import com.matrictime.network.constant.DataConstants;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;


public class HttpClientUtil {

    // TODO: 2022/4/1 上线需确认超时时间 
    private static final Integer SOCKET_TIME_OUT = 6000;

    private static final Integer CONNECT_TIME_OUT = 1000;

    private static final Integer MAX_CONN_TOTAL = 200;

    private static final String HTTP_TITLE = "http://";

    private static CloseableHttpClient httpClient;


    private HttpClientUtil() {
    }

    public static void main(String[] args) {
        try {
            post("http://127.0.0.1:8006/verify","{\"afterSix\":\"123123\",\"phoneNumber\":\"12345678904\",\"destination\":\"2\",\"uuid\":1}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static{
        httpClient =  HttpClients.custom().setConnectionManagerShared(true).setMaxConnTotal(MAX_CONN_TOTAL).build();
    }


    /**
     * 封装HTTP POST方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> formParams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formParams, "UTF-8");
        httpPost.setEntity(param);
        HttpResponse response = httpClient.execute(httpPost);
//        log.info("************{}", response);
        String httpEntityContent = getHttpEntityContent(response);
//        log.info("************{}", httpEntityContent);
        httpPost.abort();
//        log.info("************{}", httpEntityContent);
        return httpEntityContent;

    }

    /**
     * 封装HTTP POST方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String ip ,String port, String path, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        StringBuffer sb = new StringBuffer(HTTP_TITLE);
        sb.append(ip);
        sb.append(DataConstants.KEY_SPLIT);
        sb.append(port);
        sb.append(DataConstants.KEY_SLASH);
        sb.append(path);
        HttpPost httpPost = new HttpPost(sb.toString());
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> formParams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formParams, "UTF-8");
        httpPost.setEntity(param);
        HttpResponse response = httpClient.execute(httpPost);
//        log.info("************{}", response);
        String httpEntityContent = getHttpEntityContent(response);
//        log.info("************{}", httpEntityContent);
        httpPost.abort();
//        log.info("************{}", httpEntityContent);
        return httpEntityContent;

    }

    /**
     * 封装HTTP POST方法
     *
     * @param
     * @param （如JSON串）
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String data) throws ClientProtocolException, IOException {
        String httpEntityContent = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            httpEntityContent = getHttpEntityContent(response);
            httpPost.abort();
        }catch (ClientProtocolException e){
            httpClient.close();
            throw new ClientProtocolException(e);
        }catch (IOException e){
            httpClient.close();
            throw new IOException(e);
        }
        return httpEntityContent;
    }

    /**
     * 封装HTTP POST方法
     *
     * @param
     * @param （如JSON串）
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String data, Map<String,String> header) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        if (!CollectionUtils.isEmpty(header)){
            Iterator<Map.Entry<String,String>> it=header.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,String> entry=it.next();
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        httpPost.setEntity(new StringEntity(data, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP POST方法
     * 传输表单的post方法
     * @param
     * @param url,data
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postForm(String url, String filePath) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.addBinaryBody("file",new File(filePath));
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        httpPost.setConfig(requestConfig);
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     *
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpGet.setConfig(requestConfig);
        httpGet.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpGet.setConfig(requestConfig);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP PUT方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpPut.setConfig(requestConfig);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPut.setEntity(param);
        HttpResponse response = httpClient.execute(httpPut);
        String httpEntityContent = getHttpEntityContent(response);
        httpPut.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     *
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String delete(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete();
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpDelete.setConfig(requestConfig);
        httpDelete.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete();
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
        httpDelete.setConfig(requestConfig);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpDelete.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }


    /**
     * 设置请求参数
     *
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * trun ip,port,path to url
     * @param ip
     * @param port
     * @param path
     * @return
     */
    public static String getUrl(String ip,String port,String path){
        StringBuffer sb = new StringBuffer(HTTP_TITLE);
        sb.append(ip);
        sb.append(DataConstants.KEY_SPLIT);
        sb.append(port);
        sb.append(DataConstants.KEY_SLASH);
        sb.append(path);
        return sb.toString();
    }



}