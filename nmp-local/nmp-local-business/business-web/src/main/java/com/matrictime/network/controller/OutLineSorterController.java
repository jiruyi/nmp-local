package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.service.OutlineSorterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RequestMapping(value = "/outlineSorter")
@RestController
@Slf4j
public class OutLineSorterController {

    @Resource
    OutlineSorterService outlineSorterService;

    /**
     * 离线分发机创建接口
     * @param outlineSorterReq
     * @return
     */
    @ApiOperation(value = "离线分发机创建接口",notes = "离线分发机创建")
    @RequestMapping(value = "/saveOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "创建离线分发机",operType = "创建")
    @RequiresPermissions("sys:sorter:insert")
    public Result saveOutlineSorter(@RequestBody OutlineSorterReq outlineSorterReq){
        return outlineSorterService.save(outlineSorterReq);
    }

    /**
     * 离线分发机修改接口
     * @param outlineSorterReq
     * @return
     */
    @ApiOperation(value = "离线分发机修改接口",notes = "离线分发机修改")
    @RequestMapping(value = "/modifyOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "修改离线分发机",operType = "修改")
    @RequiresPermissions("sys:sorter:modify")
    public Result modifyOutlineSorter(@RequestBody OutlineSorterReq outlineSorterReq){
        return outlineSorterService.modify(outlineSorterReq);
    }

    /**
     * 离线分发机删除接口
     * @param outlineSorterReq
     * @return
     */
    @ApiOperation(value = "离线分发机删除接口",notes = "离线分发机删除")
    @RequestMapping(value = "/deleteOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "删除离线分发机",operType = "删除")
    @RequiresPermissions("sys:sorter:delete")
    public Result deleteOutlineSorter(@RequestBody OutlineSorterReq outlineSorterReq){
        return outlineSorterService.delete(outlineSorterReq);
    }

    /**
     * 离线分发机查询接口
     * @param outlineSorterReq
     * @return
     */
    @ApiOperation(value = "离线分发机查询接口",notes = "离线分发机查询")
    @RequestMapping(value = "/queryOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "查询离线分发机",operType = "查询")
    @RequiresPermissions("sys:sorter:query")
    public Result queryOutlineSorter(@RequestBody OutlineSorterReq outlineSorterReq){
        return outlineSorterService.queryByConditon(outlineSorterReq);
    }

    /**
     * 离线分发机上传接口
     * @param file
     * @return
     */
    @ApiOperation(value = "离线分发机上传接口",notes = "离线分发机上传")
    @RequestMapping(value = "/uploadOutlinePc",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "上传离线分发机",operType = "上传")
    @RequiresPermissions("sys:sorter:upload")
    public Result uploadOutlineSorter(@RequestBody MultipartFile file){
        return outlineSorterService.upload(file);
    }


    @ApiOperation(value = "离线分发机认证接口",notes = "离线分发机认证")
    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    @SystemLog(opermodul = "离线分发机管理模块",operDesc = "认证离线分发机",operType = "认证")
    public Result authOutlineSorter(@RequestBody OutlineSorterReq outlineSorterReq){
        return outlineSorterService.auth(outlineSorterReq);
    }






}
