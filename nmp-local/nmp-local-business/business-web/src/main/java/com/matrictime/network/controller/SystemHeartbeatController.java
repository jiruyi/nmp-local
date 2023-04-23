package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.request.TerminalUserResquest;
import com.matrictime.network.response.SystemHeartbeatResponse;
import com.matrictime.network.service.SystemHeartbeatService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@RequestMapping(value = "/systemHeartbeat")
@RestController
@Slf4j
@Api(value = "业务心跳管理",tags = "业务心跳管理")
public class SystemHeartbeatController {

    @Resource
    private SystemHeartbeatService systemHeartbeatService;

    @SystemLog(opermodul = "业务心跳管理",operDesc = "更新心跳状态",operType = "更新")
    @RequestMapping(value = "/updateSystemHeartbeat",method = RequestMethod.POST)
    public Result<Integer> updateSystemHeartbeat(@RequestBody SystemHeartbeatResponse systemHeartbeatResponse){
        Result<Integer> result = new Result<>();
        try {
            result = systemHeartbeatService.updateSystemHeartbeat(systemHeartbeatResponse);
        }catch (Exception e){
            log.info("updateSystemHeartbeat:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    @SystemLog(opermodul = "业务心跳管理",operDesc = "查询心跳状态",operType = "查询")
    @RequestMapping(value = "/selectSystemHeartbeat",method = RequestMethod.POST)
    public Result<SystemHeartbeatResponse> selectSystemHeartbeat(@RequestBody SystemHeartbeatRequest systemHeartbeatRequest){
        Result<SystemHeartbeatResponse> result = new Result<>();
        try {
            result = systemHeartbeatService.selectSystemHeartbeat(systemHeartbeatRequest);
        }catch (Exception e){
            log.info("selectSystemHeartbeat:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }




}
