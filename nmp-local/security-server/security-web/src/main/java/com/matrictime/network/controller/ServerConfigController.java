package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.ServerConfigListReq;
import com.matrictime.network.req.ServerConfigRequest;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@RequestMapping(value = "/config")
@RestController
@Slf4j
public class ServerConfigController {

    @Resource
    private ServerConfigService serverConfigService;

    /**
     * 安全服务器参数查询
     * @param serverConfigRequest
     * @return
     */
    @RequestMapping(value = "/selectServerConfig",method = RequestMethod.POST)
    public Result selectConfiguration(@RequestBody ServerConfigRequest serverConfigRequest){
        try {
            Result result = serverConfigService.selectServerConfig(serverConfigRequest);
            return result;
        }catch (Exception e){
            log.error("selectServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 安全服务器配置插入
     * @param serverConfigRequest
     * @return
     */
    @RequestMapping(value = "/insertServerConfig",method = RequestMethod.POST)
    public Result insertConfiguration(@RequestBody ServerConfigRequest serverConfigRequest){
        try {
            if(StringUtils.isEmpty(serverConfigRequest.getConfigCode()) ||
                    StringUtils.isEmpty(serverConfigRequest.getNetworkId())){
                return new Result<>(false,"缺少必传参数");
            }
            Result result = serverConfigService.insertServerConfig(serverConfigRequest);
            return result;
        }catch (Exception e){
            log.error("insertServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 同步配置
     * @param serverConfigRequest
     * @return
     */
    @RequestMapping(value = "/synConfig",method = RequestMethod.POST)
    public Result synConfig(@RequestBody ServerConfigRequest serverConfigRequest){
        try {
            Result result = serverConfigService.synConfig(serverConfigRequest);
            return result;
        }catch (Exception e){
            log.error("synConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 同步配置
     * @param listReq
     * @return
     */
    @RequestMapping(value = "/synConfigList",method = RequestMethod.POST)
    public Result synConfigList(@RequestBody ServerConfigListReq listReq){
        try {
            Result result = serverConfigService.synConfigList(listReq);
            return result;
        }catch (Exception e){
            log.error("synConfigList exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 同步配置
     * @param listReq
     * @return
     */
    @RequestMapping(value = "/insertBatchServerConfig",method = RequestMethod.POST)
    public Result insertBatchServerConfig(@RequestBody ServerConfigListReq listReq){
        try {
            Result result = serverConfigService.insertBatchServerConfig(listReq);
            return result;
        }catch (Exception e){
            log.error("insertBatchServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
