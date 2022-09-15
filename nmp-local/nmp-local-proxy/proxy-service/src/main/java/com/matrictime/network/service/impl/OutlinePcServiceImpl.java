package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.service.OutlinePcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@Slf4j
@Service
public class OutlinePcServiceImpl implements OutlinePcService {

    @Resource
    private OutlinePcDomainService outlinePcDomainService;

    @Override
    public Result<Integer> updateOutlinePc(OutlinePcReq outlinePcReq) {
        Result result = new Result<>();
        try {
            result.setResultObj(outlinePcDomainService.updateOutlinePc(outlinePcReq));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常");
            log.info("updateOutlinePc:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> batchInsertOutlinePc(OutlinePcListRequest listRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(outlinePcDomainService.batchInsertOutlinePc(listRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常");
            log.info("batchInsertOutlinePc:{}",e.getMessage());
        }
        return result;
    }


















}
