package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.service.DataCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Service
@Slf4j
public class DataCollectServiceImpl implements DataCollectService {

    @Resource
    private DataCollectDomainService collectDomainService;

    @Override
    public Result<DataCollectResponse> selectDataCollect() {
        Result<DataCollectResponse> result = new Result<>();
        DataCollectResponse dataCollectResponse = new DataCollectResponse();
        try {
            dataCollectResponse.setList(collectDomainService.selectDataCollect());
            result.setResultObj(dataCollectResponse);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }
}
