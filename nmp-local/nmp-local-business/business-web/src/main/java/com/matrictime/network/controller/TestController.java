package com.matrictime.network.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.ShellReq;
import com.matrictime.network.request.UploadSingleFileReq;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.UploadSingleFileResp;
import com.matrictime.network.response.UploadVersionFileResp;
import com.matrictime.network.service.UploadFileService;
import com.matrictime.network.service.impl.AsyncService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.KEY_SLASH;

@RequestMapping(value = "/test")
@RestController
@Slf4j
public class TestController extends SystemBaseService {

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncService asyncService;

    /**
     * 上传版本文件
     * @return
     */
    @RequestMapping(value = "/uploadTest",method = RequestMethod.POST)
    public Result uploadVersionFile(@RequestParam("file") MultipartFile file){
        Result<UploadSingleFileResp> result;
        try {
            UploadSingleFileReq req = new UploadSingleFileReq();

            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(DataConstants.KEY_POINT));
            String fileName = UUID.randomUUID()+suffix;

            req.setFile(file);
            req.setModuleName("moduleName");
            req.setFileName(fileName);
            req.setUploadPath("test"+KEY_SLASH);

            result = uploadFileService.uploadSingleFile(req);
        }catch (Exception e){
            log.error("VersionController.uploadVersionFile exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    public void redis(){
        try {
            redisTemplate.opsForValue().set("0000"+ com.matrictime.network.base.constant.DataConstants.USER_LOGIN_JWT_TOKEN,"token",1, TimeUnit.DAYS);
        }catch (Exception e){
            log.error("VersionController.redis exception:{}",e.getMessage());
        }
    }

    @RequestMapping(value = "/httpClient",method = RequestMethod.POST)
    public void httpClient(){
        try {

//            String post = HttpClientUtil.post("127.0.0.1", "8002", "nmp-local-business/config/queryConfigByPages", new HashMap<>());
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put(KEY_CONFIG_CODE,"configCode");
            jsonObject1.put(KEY_CONFIG_VALUE,"configValue");
            jsonObject1.put(KEY_UNIT,"unit");
            String json = jsonObject1.toJSONString();
            String post = HttpClientUtil.post("http://192.168.72.253:8080/param", json);
            System.out.println("post:"+post);
            JSONObject jsonObject = JSONObject.parseObject(post);
            Object s = jsonObject.get("isSuccess");
            System.out.println("s:"+s);

        }catch (Exception e){
            log.error("VersionController.redis exception:{}",e.getMessage());
        }
    }

    @RequestMapping(value = "/param",method = RequestMethod.POST)
    public void http(@RequestBody JSONObject jsonObject){
        try {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1 = jsonObject;

        }catch (Exception e){
            log.error("VersionController.redis exception:{}",e.getMessage());
        }
    }


    @RequestMapping(value = "/shell",method = RequestMethod.POST)
    public void shell(@RequestBody ShellReq shellReq){
        try {
            List<String> commands = shellReq.getCommands();
            Integer integer = ShellUtil.runShell(commands);
            log.info(String.valueOf(integer));
        }catch (Exception e){
            log.error("shell exception:{}",e.getMessage());
        }
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public void upload(@RequestBody ShellReq shellReq){
        try {
            String path = shellReq.getPath();
            String url = "http://192.168.72.34:8008/nmp-local-proxy/baseStation/uploadVersionFile";
            Map<String,String> map = new HashMap<>();
            map.put("systemId", "1");
            Map<String,String> fileMap = new HashMap<>();
            fileMap.put("file",path);
            String post = HttpClientUtil.sendPost(url, null, map, fileMap, "UTF-8", "UTF-8");
        }catch (Exception e){
            log.error("shell exception:{}",e.getMessage());
        }
    }




}
