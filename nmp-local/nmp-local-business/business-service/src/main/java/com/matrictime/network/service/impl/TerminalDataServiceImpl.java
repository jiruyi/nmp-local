package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.TerminalDataResponse;
import com.matrictime.network.service.TerminalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Service
@Slf4j
public class TerminalDataServiceImpl implements TerminalDataService {

    @Resource
    private TerminalDataDomainService terminalDataDomainService;

    @Override
    public Result<TerminalDataResponse> selectTerminalData(TerminalDataRequest terminalDataRequest) {
        Result<TerminalDataResponse> result = new Result<>();
        try {
            result.setResultObj(terminalDataDomainService.selectTerminalData(terminalDataRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("selectTerminalData:{}",e.getMessage());
        }
        return result;
    }
}
