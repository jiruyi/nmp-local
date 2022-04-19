package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.dao.mapper.NmplVersionFileMapper;
import com.matrictime.network.dao.model.NmplVersionFile;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.VersionService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RequestMapping(value = "/version")
@RestController
@Slf4j
/**
 * 版本相关请求接口
 */
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Autowired(required = false)
    private NmplVersionFileMapper nmplVersionFileMapper;


    /**
     * 编辑版本信息
     * @param req
     * @return
     */
    @RequestMapping (value = "/editVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "编辑版本信息",operType = "操作")
    @RequiresPermissions("sys:version:save")
    public Result<EditVersionResp> editVersion(@RequestBody EditVersionReq req){
        try {
            return  versionService.editVersion(req);
        }catch (Exception e){
            log.error("VersionController.editVersion exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 上传版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/uploadVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "上传版本文件",operType = "上传")
    @RequiresPermissions("sys:version:import")
    public Result<UploadVersionFileResp> uploadVersionFile(UploadVersionFileReq req){
        try {
            return  versionService.uploadVersionFile(req);
        }catch (Exception e){
            log.error("VersionController.uploadVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/deleteVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "删除版本文件",operType = "删除")
    @RequiresPermissions("sys:version:deleteFile")
    public Result<DeleteVersionFileResp> deleteVersionFile(@RequestBody DeleteVersionFileReq req){
        try {
            return  versionService.deleteVersionFile(req);
        }catch (Exception e){
            log.error("VersionController.deleteVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 根据系统查询版本文件列表
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "根据系统查询版本文件列表",operType = "查询")
    @RequiresPermissions("sys:version:fileList")
    public Result<QueryVersionFileResp> queryVersionFile(@RequestBody QueryVersionFileReq req){
        try {
            return  versionService.queryVersionFile(req);
        }catch (Exception e){
            log.error("VersionController.queryVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 根据系统查询版本列表
     * @return
     */
    @RequestMapping (value = "/queryVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "根据系统查询版本列表",operType = "查询")
    @RequiresPermissions("sys:version:list")
    public Result<QueryVersionResp> queryVersion(){
        try {
            return  versionService.queryVersion();
        }catch (Exception e){
            log.error("VersionController.queryVersion exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询推送设备/详情
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryVersionFileDetail",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "查询推送设备/详情",operType = "查询")
    @RequiresPermissions("sys:version:detail")
    public Result<QueryVersionFileDetailResp> queryVersionFileDetail(@RequestBody QueryVersionFileDetailReq req){
        try {
            return  versionService.queryVersionFileDetail(req);
        }catch (Exception e){
            log.error("VersionController.queryVersionFileDetail exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 推送设备
     * @param req
     * @return
     */
    @RequestMapping (value = "/pushVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "推送设备",operType = "推送")
    @RequiresPermissions("sys:version:push")
    public Result<PushVersionFileResp> pushVersionFile(@RequestBody PushVersionFileReq req){
        try {
            return  versionService.pushVersionFile(req);
        }catch (Exception e){
            log.error("VersionController.pushVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 获取版本文件
     * @param fileId
     * @param resp
     */
    @RequestMapping (value = "/getVersionFile",method = RequestMethod.GET)
    @SystemLog(opermodul = "版本模块",operDesc = "获取版本文件",operType = "取文件")
    public void getVersionFile(@RequestParam("fileId") String fileId,HttpServletResponse resp){
        try {
            if (ParamCheckUtil.checkVoStrBlank(fileId)){
                throw new Exception("fileId不能为空");
            }
            NmplVersionFile file = nmplVersionFileMapper.selectByPrimaryKey(Long.parseLong(fileId));
            if (file == null){
                throw new Exception("版本文件不存在");
            }
            File tempFile = new File(file.getFilePath()+file.getFileName());
            if (!tempFile.exists()){
                throw new Exception("本地版本文件不存在");
            }
            resp.setContentType("text/html; charset=gb2312");
            FileInputStream fis = null;
            OutputStream os = resp.getOutputStream();
            try {
                fis = new FileInputStream(tempFile);
                IOUtils.copy(fis, os);
                resp.flushBuffer();
            }catch (IOException e){
                log.error(e.getMessage());
            }finally {
                if (fis != null){
                    try {
                        fis.close();
                    }catch (IOException e){
                        log.error("输入流关闭失败:"+e.getMessage());
                    }
                }
                if (os != null){
                    try {
                        os.close();
                    }catch (IOException e){
                        log.error("输出流关闭失败:"+e.getMessage());
                    }
                }
            }
        }catch (Exception e){
            log.error("VersionController.getVersionFile exception:{}",e.getMessage());
        }
    }

    /**
     * 启动版本文件
     * @param req
     * @return
     */
    @RequestMapping (value = "/startVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "启动版本文件",operType = "操作")
    @RequiresPermissions("sys:version:start")
    public Result<StartVersionFileResp> startVersionFile(@RequestBody StartVersionFileReq req){
        try {
            return  versionService.startVersionFile(req);
        }catch (Exception e){
            log.error("VersionController.startVersionFile exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
