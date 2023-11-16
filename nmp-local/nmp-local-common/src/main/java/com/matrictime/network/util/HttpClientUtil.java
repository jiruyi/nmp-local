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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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
    public static String post(String url, String data, Integer socketTimeout, Integer connectTimeout) throws ClientProtocolException, IOException {
        String httpEntityContent = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
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
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url) throws ClientProtocolException, IOException {
        String httpEntityContent = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
            httpPost.setConfig(requestConfig);
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
        sb.append(path);
        return sb.toString();
    }


    /**
     * 发送post请求
     *
     * @param requestUrl       请求url
     * @param requestHeader    请求头
     * @param formTexts        表单数据
     * @param files            上传文件
     * @param requestEncoding  请求编码
     * @param responseEncoding 响应编码
     * @return 页面响应html
     */
    public static String sendPost(String requestUrl, Map<String, String> requestHeader, Map<String, String> formTexts, Map<String, String> files, String requestEncoding, String responseEncoding) {
        OutputStream out = null;
        BufferedReader reader = null;
        String result = "";
        try {
            if (requestUrl == null || requestUrl.isEmpty()) {
                return result;
            }
            URL realUrl = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "text/html, application/xhtml+xml, image/jxr, */*");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
            if (requestHeader != null && requestHeader.size() > 0) {
                for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            if (requestEncoding == null || requestEncoding.isEmpty()) {
                requestEncoding = "UTF-8";
            }
            if (responseEncoding == null || responseEncoding.isEmpty()) {
                responseEncoding = "UTF-8";
            }
            if (requestHeader != null && requestHeader.size() > 0) {
                for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (files == null || files.size() == 0) {
                connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                out = new DataOutputStream(connection.getOutputStream());
                if (formTexts != null && formTexts.size() > 0) {
                    String formData = "";
                    for (Map.Entry<String, String> entry : formTexts.entrySet()) {
                        formData += entry.getKey() + "=" + entry.getValue() + "&";
                    }
                    formData = formData.substring(0, formData.length() - 1);
                    out.write(formData.toString().getBytes(requestEncoding));
                }
            } else {
                String boundary = "-----------------------------" + String.valueOf(new Date().getTime());
                connection.setRequestProperty("content-type", "multipart/form-data; boundary=" + boundary);
                out = new DataOutputStream(connection.getOutputStream());
                if (formTexts != null && formTexts.size() > 0) {
                    StringBuilder sbFormData = new StringBuilder();
                    for (Map.Entry<String, String> entry : formTexts.entrySet()) {
                        sbFormData.append("--" + boundary + "\r\n");
                        sbFormData.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                        sbFormData.append(entry.getValue() + "\r\n");
                    }
                    out.write(sbFormData.toString().getBytes(requestEncoding));
                }
                for (Map.Entry<String, String> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    String filePath = entry.getValue();
                    if (fileName == null || fileName.isEmpty() || filePath == null || filePath.isEmpty()) {
                        continue;
                    }
                    File file = new File(filePath);
                    if (!file.exists()) {
                        continue;
                    }
                    out.write(("--" + boundary + "\r\n").getBytes(requestEncoding));
                    out.write(("Content-Disposition: form-data; name=\"" + fileName + "\"; filename=\"" + file.getName() + "\"\r\n").getBytes(requestEncoding));
                    out.write(("Content-Type: application/x-msdownload\r\n\r\n").getBytes(requestEncoding));
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                    out.write(("\r\n").getBytes(requestEncoding));
                }
                out.write(("--" + boundary + "--").getBytes(requestEncoding));
            }
            out.flush();
            out.close();
            out = null;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), responseEncoding));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}