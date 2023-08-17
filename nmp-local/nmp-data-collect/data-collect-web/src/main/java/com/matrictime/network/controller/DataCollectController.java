package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.service.DataCollectService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */


@RequestMapping(value = "/dataCollect")
@Api(value = "流量收集相关接口", tags = "流量收集相关接口")
@RestController
@Slf4j
public class DataCollectController {

    @Resource
    private DataCollectService dataCollectService;

    @RequestMapping(value = "/selectDataCollect",method = RequestMethod.POST)
    public Result<DataCollectResponse> selectDataCollect(){
        Result<DataCollectResponse> result = new Result<>();
        try {
            result = dataCollectService.selectDataCollect();
        }catch (Exception e){
            log.info("selectDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

}
