package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.CompanyDomainService;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.response.CompanyInfoResponse;
import com.matrictime.network.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

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
}
