package com.matrictime.network.controller;


import com.matrictime.network.model.Result;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.VersionFileResponse;
import com.matrictime.network.service.VersionService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RequestMapping(value = "/version")
@RestController
@Slf4j
/**
 * 版本相关请求接口
 */
public class VersionController {

    @Resource
    private VersionService versionService;

    @RequestMapping(value = "/uploadVersionFile",method = RequestMethod.POST)
    public Result<Integer> uploadVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        try {
            return versionService.insertVersionFile(uploadVersionFileReq);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/updateVersionFile",method = RequestMethod.POST)
    public Result<Integer> updateVersionFile(UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.updateVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/selectVersionFile",method = RequestMethod.POST)
    public Result<VersionFileResponse> selectVersionFile(@RequestBody UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.selectVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteVersionFile",method = RequestMethod.POST)
    public Result<Integer> deleteVersionFile(@RequestBody UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.deleteVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }







































}
