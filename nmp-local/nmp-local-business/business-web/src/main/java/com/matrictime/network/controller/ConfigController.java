package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.request.ResetDefaultConfigReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.QueryConfigByPagesResp;
import com.matrictime.network.response.ResetDefaultConfigResp;
import com.matrictime.network.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/config")
@RestController
@Slf4j
/**
 * 配置相关请求接口
 */
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 编辑配置
     * @param req
     * @return
     */
    @RequestMapping (value = "/editConfig",method = RequestMethod.POST)
    public Result<EditConfigResp> editConfig(@RequestBody EditConfigReq req){
        try {
            return  configService.editConfig(req);
        }catch (Exception e){
            log.error("editConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 查询接口（支持分页查询）
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryByPages",method = RequestMethod.POST)
    public Result<QueryConfigByPagesResp> queryConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            return  configService.queryByPages(req);
        }catch (Exception e){
            log.error("queryConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 恢复默认接口（支持全量恢复,同时需要同步数据）
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetDefaultConfig",method = RequestMethod.POST)
    public Result<ResetDefaultConfigResp> resetDefaultConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("queryConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
    // 根据设备类型查询列表(待定)

    // 调用http接口同步接口（支持批量D）

}
