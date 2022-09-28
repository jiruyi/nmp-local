package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.service.OutlinePcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@RestController
@RequestMapping(value = "/outlinePc",method = RequestMethod.POST)
@Slf4j
public class OutlinePcController {
    @Resource
    private OutlinePcService outlinePcService;

    @RequestMapping(value = "/updateOutlinePc",method = RequestMethod.POST)
    public Result<Integer> updateOutlinePc(@RequestBody OutlinePcReq outlinePcReq){
        Result result = new Result<>();
        try {
            result = outlinePcService.updateOutlinePc(outlinePcReq);
        }catch (Exception e){
            result.setErrorMsg("系统异常");
            result.setSuccess(false);
            log.info("updateOutlinePc:{}",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/batchInsertOutlinePc",method = RequestMethod.POST)
    public Result<Integer> batchInsertOutlinePc(@RequestBody OutlinePcListRequest listRequest){
        Result result = new Result<>();
        try {
            result = outlinePcService.batchInsertOutlinePc(listRequest);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorCode("系统异常");
            log.info("batchInsertOutlinePc:{}",e.getMessage());
        }
        return result;
    }
}
