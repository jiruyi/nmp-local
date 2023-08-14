package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditBasicConfigReq;
import com.matrictime.network.request.QueryDictionaryReq;
import com.matrictime.network.service.SystemConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/config")
@RestController
@Slf4j
public class SystemConfigController {

    @Autowired
    SystemConfigService systemConfigService;

    /**
     * 基础配置查询
     * @return
     */
    @RequestMapping(value = "/queryBasicConfig",method = RequestMethod.POST)
//    @SystemLog(opermodul = "基础配置",operDesc = "基础配置查询",operType = "查询")
//    @RequiresPermissions("sys:basic:query")
    public Result queryBasicConfig(){
        try {
            return  systemConfigService.queryBasicConfig();
        }catch (Exception e){
            log.error("SystemConfigController.queryBasicConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 基础配置编辑
     * @return
     */
    @RequestMapping(value = "/updateBasicConfig",method = RequestMethod.POST)
//    @SystemLog(opermodul = "基础配置",operDesc = "基础配置编辑",operType = "编辑")
//    @RequiresPermissions("sys:basic:update")
    public Result updateBasicConfig(@RequestBody EditBasicConfigReq req){
        try {
            return  systemConfigService.editBasicConfig(req);
        }catch (Exception e){
            log.error("SystemConfigController.updateBasicConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 字典表查询
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryDictionary",method = RequestMethod.POST)
//    @SystemLog(opermodul = "字典表",operDesc = "字典表查询",operType = "查询")
//    @RequiresPermissions("sys:dict:query")
    public Result queryDictionary(@RequestBody QueryDictionaryReq req){
        try {
            return  systemConfigService.queryDictionary(req);
        }catch (Exception e){
            log.error("SystemConfigController.queryDictionary exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 字典表上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadDictionary",method = RequestMethod.POST)
//    @SystemLog(opermodul = "字典表",operDesc = "字典表上传",operType = "上传")
//    @RequiresPermissions("sys:dict:upload")
    public Result uploadDictionary(@RequestBody MultipartFile file){
        return systemConfigService.uploadDictionary(file);
    }


}
