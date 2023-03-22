package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.VersionControlService;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.VersionFileResponse;
import com.matrictime.network.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    @Resource
    private VersionControlService versionControlService;


    /**
     * 编辑版本信息
     * @author hexu
     * @param
     * @return
     */
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

    //-----------------------------------------------------------------------------------------------


    /**
     * 加载版本文件
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/loadVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "加载版本文件",operType = "加载版本")
    @RequiresPermissions("sys:versionControl:load")
    public Result loadVersionFile(@RequestBody VersionReq req){
        return versionControlService.loadVersionFile(req);
    }

    /**
     * 查询加载版本列表
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryLoadVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "查询加载版本列表",operType = "查询加载版本")
    @RequiresPermissions("sys:versionControl:queryLoadVersion")
    public Result<PageInfo> queryLoadVersion(@RequestBody VersionReq req){
        return versionControlService.queryLoadVersion(req);
    }

    /**
     * 启动加载版本文件
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/runLoadVersionFile",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "启动已加载文件",operType = "启动已记载版本")
    @RequiresPermissions("sys:versionControl:loadAndRun")
    public Result runLoadVersionFile(@RequestBody VersionReq req){
        return versionControlService.runLoadVersionFile(req);
    }

    //--------------------------------------------------------------------------

    /**
     * 启动版本
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/runVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "启动已停止版本",operType = "启动版本")
    @RequiresPermissions("sys:versionControl:run")
    public Result runVersion(@RequestBody VersionReq req){
        return versionControlService.runVersion(req);
    }

    /**
     * 查询运行版本列表
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryRunVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "查询运行版本列表",operType = "查询运行版本列表")
    @RequiresPermissions("sys:versionControl:queryRunVersion")
    public Result<PageInfo> queryRunVersion(@RequestBody VersionReq req){
        return versionControlService.queryRunVersion(req);
    }

    /**
     * 停止运行版本文件
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/stopRunVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "停止已运行版本",operType = "停止版本")
    @RequiresPermissions("sys:versionControl:stop")
    public Result stopRunVersion(@RequestBody VersionReq req){
        return versionControlService.stopRunVersion(req);
    }

    /**
     * 卸载运行版本文件
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/uninstallRunVersion",method = RequestMethod.POST)
    @SystemLog(opermodul = "版本模块",operDesc = "卸载已停止版本",operType = "卸载版本")
    @RequiresPermissions("sys:versionControl:uninstall")
    public Result uninstallRunVersion(@RequestBody VersionReq req){
        return versionControlService.uninstallRunVersion(req);
    }
}
