package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.service.OutlinePcService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 一体机管理模块
 * @author zhangyunjie
 */
@RequestMapping(value = "/outlinePc")
@RestController
@Slf4j
public class OutlinePcController {
    @Resource
    OutlinePcService outlinePcService;

    /**
     * 一体机创建接口
     * @param outlinePcReq
     * @return
     */
    @ApiOperation(value = "一体机创建接口",notes = "一体机创建")
    @RequestMapping(value = "/saveOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "一体机管理模块",operDesc = "创建一体机",operType = "新增")
    @RequiresPermissions("sys:pc:insert")
    public Result saveOutlinePc(@RequestBody OutlinePcReq outlinePcReq){
        return outlinePcService.save(outlinePcReq);
    }

    /**
     * 一体机修改接口
     * @param outlinePcReq
     * @return
     */
    @ApiOperation(value = "一体机修改接口",notes = "一体机修改")
    @RequestMapping(value = "/modifyOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "一体机管理模块",operDesc = "修改一体机",operType = "编辑")
    @RequiresPermissions("sys:pc:modify")
    public Result modifyOutlinePc(@RequestBody OutlinePcReq outlinePcReq){
        return outlinePcService.modify(outlinePcReq);
    }

    /**
     * 一体机删除接口
     * @param outlinePcReq
     * @return
     */
    @ApiOperation(value = "一体机删除接口",notes = "一体机删除")
    @RequestMapping(value = "/deleteOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "一体机管理模块",operDesc = "删除一体机",operType = "删除")
    @RequiresPermissions("sys:pc:delete")
    public Result deleteOutlinePc(@RequestBody OutlinePcReq outlinePcReq){
        return outlinePcService.delete(outlinePcReq);
    }

    /**
     * 一体机查询接口
     * @param outlinePcReq
     * @return
     */
    @ApiOperation(value = "一体机查询接口",notes = "一体机查询")
    @RequestMapping(value = "/queryOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "一体机管理模块",operDesc = "查询一体机",operType = "查询")
    @RequiresPermissions("sys:pc:query")
    public Result queryOutlinePc(@RequestBody OutlinePcReq outlinePcReq){
        return outlinePcService.queryByConditon(outlinePcReq);
    }

    /**
     * 一体机上传接口
     * @param file
     * @return
     */
    @ApiOperation(value = "一体机上传接口",notes = "一体机上传")
    @RequestMapping(value = "/uploadOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "一体机上传模块",operDesc = "上传一体机",operType = "上传")
    @RequiresPermissions("sys:pc:upload")
    public Result uploadOutlinePc(@RequestBody MultipartFile file){
        return outlinePcService.upload(file);
    }


}
