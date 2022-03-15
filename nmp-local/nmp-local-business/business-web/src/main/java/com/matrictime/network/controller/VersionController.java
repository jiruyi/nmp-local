package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/version")
@RestController
@Slf4j
/**
 * 版本相关请求接口
 */
public class VersionController {

    @Autowired
    private VersionService versionService;


    /**
     * 编辑版本信息
     * @param req
     * @return
     */
    @RequestMapping (value = "/editVersion",method = RequestMethod.POST)
    public Result<EditVersionResp> editVersion(@RequestBody EditVersionReq req){
        try {
            return  versionService.editVersion(req);
        }catch (Exception e){
            log.error("editVersion exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 上传版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/uploadVersionFile",method = RequestMethod.POST)
    public Result<UploadVersionFileResp> uploadVersionFile(@RequestBody UploadVersionFileReq req){
        try {
            return  versionService.uploadVersionFile(req);
        }catch (Exception e){
            log.error("uploadVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/deleteVersionFile",method = RequestMethod.POST)
    public Result<DeleteVersionFileResp> deleteVersionFile(@RequestBody DeleteVersionFileReq req){
        try {
            return  versionService.deleteVersionFile(req);
        }catch (Exception e){
            log.error("deleteVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 根据系统查询版本文件列表
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryVersionFile",method = RequestMethod.POST)
    public Result<QueryVersionFileResp> queryVersionFile(@RequestBody QueryVersionFileReq req){
        try {
            return  versionService.queryVersionFile(req);
        }catch (Exception e){
            log.error("queryVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询推送设备/详情
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryVersionFileDetail",method = RequestMethod.POST)
    public Result<QueryVersionFileDetailResp> queryVersionFileDetail(@RequestBody QueryVersionFileDetailReq req){
        try {
            return  versionService.queryVersionFileDetail(req);
        }catch (Exception e){
            log.error("queryVersionFileDetail exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询推送设备/详情
     * @param req
     * @return
     */
    @RequestMapping (value = "/pushVersionFile",method = RequestMethod.POST)
    public Result<PushVersionFileResp> pushVersionFile(@RequestBody PushVersionFileReq req){
        try {
            return  versionService.pushVersionFile(req);
        }catch (Exception e){
            log.error("pushVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 启动版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/startVersionFile",method = RequestMethod.POST)
    public Result<StartVersionFileResp> startVersionFile(@RequestBody StartVersionFileReq req){
        try {
            return  versionService.startVersionFile(req);
        }catch (Exception e){
            log.error("startVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
