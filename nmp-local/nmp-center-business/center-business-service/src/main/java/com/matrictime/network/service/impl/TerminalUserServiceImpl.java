package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.DataHandlerService;
import com.matrictime.network.service.TerminalUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
@Slf4j
public class TerminalUserServiceImpl implements TerminalUserService, DataHandlerService {

    @Resource
    private TerminalUserDomainService terminalUserDomainService;

    @Transactional
    @Override
    public Result<Integer> receiveTerminalUser(TerminalUserResponse terminalUserResponse) {
        Result<Integer> result = new Result<>();
        try {
            List<TerminalUserVo> list = terminalUserResponse.getList();
            int i = 0;
            for(TerminalUserVo terminalUserVo: list){
                List<NmplTerminalUser> nmplTerminalUsers = terminalUserDomainService.selectTerminalUser(terminalUserVo);
                if(CollectionUtils.isEmpty(nmplTerminalUsers)){
                    i = terminalUserDomainService.insertTerminalUser(terminalUserVo);
                }else {
                    i = terminalUserDomainService.updateTerminalUser(terminalUserVo);
                }
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("receiveTerminalUser:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public void handlerData(DataPushBody dataPushBody) {
        try {
            if(ObjectUtils.isEmpty(dataPushBody)){
                return;
            }
            String dataJsonStr = dataPushBody.getBusiDataJsonStr();
            List list = JSONObject.parseObject(dataJsonStr, List.class);
            for(Object terminalUserVo: list){
                List<NmplTerminalUser> nmplTerminalUsers = terminalUserDomainService.selectTerminalUser((TerminalUserVo) terminalUserVo);
                if(CollectionUtils.isEmpty(nmplTerminalUsers)){
                    terminalUserDomainService.insertTerminalUser((TerminalUserVo) terminalUserVo);
                }else {
                    terminalUserDomainService.updateTerminalUser((TerminalUserVo) terminalUserVo);
                }
            }
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
