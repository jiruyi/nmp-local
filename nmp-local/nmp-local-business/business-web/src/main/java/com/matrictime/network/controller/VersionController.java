package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.VersionFileResponse;
import com.matrictime.network.service.VersionService;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @RequiresPermissions("sys:version:save")
    @SystemLog(opermodul = "版本文件",operDesc = "上传版本文件",operType = "基站插入")
    @RequestMapping(value = "/uploadVersionFile",method = RequestMethod.POST)
    public Result<Integer> uploadVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        try {
            return versionService.insertVersionFile(uploadVersionFileReq);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage());
        }
    }

    @RequiresPermissions("sys:version:updateFile")
    @SystemLog(opermodul = "版本文件",operDesc = "更新版本文件",operType = "更新版本文件")
    @RequestMapping(value = "/updateVersionFile",method = RequestMethod.POST)
    public Result<Integer> updateVersionFile(UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.updateVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }

    @RequiresPermissions("sys:version:list")
    @SystemLog(opermodul = "版本文件",operDesc = "查询版本文件",operType = "查询版本文件")
    @RequestMapping(value = "/selectVersionFile",method = RequestMethod.POST)
    public Result<VersionFileResponse> selectVersionFile(@RequestBody UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.selectVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }

    @RequiresPermissions("sys:version:deleteFile")
    @SystemLog(opermodul = "版本文件",operDesc = "删除版本文件",operType = "删除版本文件")
    @RequestMapping(value = "/deleteVersionFile",method = RequestMethod.POST)
    public Result<Integer> deleteVersionFile(@RequestBody UploadVersionFileReq uploadVersionFileReq){
        try {
            return versionService.deleteVersionFile(uploadVersionFileReq);
        }catch (Exception e){
            return new Result<>(false,e.getMessage());
        }
    }







































}
