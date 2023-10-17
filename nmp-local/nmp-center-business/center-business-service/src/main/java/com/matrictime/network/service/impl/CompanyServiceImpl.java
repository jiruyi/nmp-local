package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.CompanyDomainService;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.response.CompanyInfoResponse;
import com.matrictime.network.service.CompanyService;
import com.matrictime.network.service.DataHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service("company_info")
@Slf4j
public class CompanyServiceImpl implements CompanyService, DataHandlerService {

    @Resource
    private CompanyDomainService companyDomainService;

    @Transactional
    @Override
    public Result<Integer> receiveCompany(CompanyInfoResponse companyInfoResponse) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            for(CompanyInfoVo companyInfoVo: companyInfoResponse.getList()){
                List<NmplCompanyInfo> companyInfos = companyDomainService.selectCompany(companyInfoVo);
                if(CollectionUtils.isEmpty(companyInfos)){
                    i = companyDomainService.insertCompany(companyInfoVo);
                }else {
                    i = companyDomainService.updateCompany(companyInfoVo);
                }
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("receiveCompany:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * netty接受数据插入数据库
     * @param dataPushBody
     */
    @Override
    public void handlerData(DataPushBody dataPushBody) {
        try {
            if(ObjectUtils.isEmpty(dataPushBody)){
                return;
            }
            String dataJsonStr = dataPushBody.getBusiDataJsonStr();
            List<CompanyInfoVo> companyInfoVos = JSONArray.parseArray(dataJsonStr, CompanyInfoVo.class);
            for(CompanyInfoVo companyInfoVo: companyInfoVos){
                List<NmplCompanyInfo> companyInfos = companyDomainService.selectCompany(companyInfoVo);
                if(CollectionUtils.isEmpty(companyInfos)){
                    companyDomainService.insertCompany(companyInfoVo);
                }else {
                    companyDomainService.updateCompany(companyInfoVo);
                }
            }
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
