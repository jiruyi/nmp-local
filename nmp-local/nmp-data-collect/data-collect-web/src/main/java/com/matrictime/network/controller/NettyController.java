package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.netty.client.NettyClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/10/9 0009 11:05
 * @desc
 */
@RequestMapping(value = "/netty")
@Api(value = "netty链接修改", tags = "netty链接修改")
@RestController
@Slf4j
public class NettyController {

    @Autowired
    private NettyClient nettyClient;

    @ApiOperation(value = "netty链路修改", notes = "netty链路修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateDataCollectNetty() {
        try {
            //链路修改后重新启动新的链接
            nettyClient.start();
            return new Result();
        }catch (Exception e){
            log.error("ScheduleController updateScheduleCron exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

}
