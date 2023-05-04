package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataListRequest;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.TerminalDataResponse;
import com.matrictime.network.service.TerminalDataService;
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
 * @date 2023/4/20.
 */
@RequestMapping(value = "/terminalData")
@RestController
@Slf4j
@Api(value = "终端流量",tags = "终端流量")
public class TerminalDataController {

    @Resource
    private TerminalDataService terminalDataService;

    @SystemLog(opermodul = "终端流量",operDesc = "终端流量列表展示",operType = "查询")
    @RequestMapping(value = "/selectTerminalData",method = RequestMethod.POST)
    public Result<PageInfo> selectTerminalData(@RequestBody TerminalDataRequest terminalDataRequest){
        Result<PageInfo> result = new Result<>();
        try {
            if(StringUtils.isEmpty(terminalDataRequest.getParenIp())){
                throw new RuntimeException("缺少必传参数");
            }
            result = terminalDataService.selectTerminalData(terminalDataRequest);
        }catch (Exception e){
            log.info("selectTerminalData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    @SystemLog(opermodul = "终端流量",operDesc = "终端流量流量收集",operType = "插入")
    @RequestMapping(value = "/collectTerminalData",method = RequestMethod.POST)
    public Result<Integer> collectTerminalData(@RequestBody TerminalDataListRequest terminalDataListRequest){
        Result<Integer> result = new Result<>();
        try {
            result = terminalDataService.collectTerminalData(terminalDataListRequest);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("collectTerminalData:{}",e.getMessage());
        }
        return result;
    }
}
