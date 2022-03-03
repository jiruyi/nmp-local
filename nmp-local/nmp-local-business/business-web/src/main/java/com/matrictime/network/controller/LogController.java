package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 15:39
 * @desc
 */

@RequestMapping(value = "/log")
@Api(value = "网关日志和用户登录明细",tags = "网关日志和用户登录明细")
@RestController
@Slf4j
public class LogController {

    @ApiOperation(value = "查询网关日志记录",notes = "查询网关日志记录")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询网关日志记录",operType = "查询")
    @RequestMapping(value = "/oper/query",method = RequestMethod.POST)
    public Result<PageInfo> queryLogList(@RequestBody LogRequest logRequest){
        try {
        }catch (Exception e){
            log.error("queryLogList发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
        return null;
    }
}
