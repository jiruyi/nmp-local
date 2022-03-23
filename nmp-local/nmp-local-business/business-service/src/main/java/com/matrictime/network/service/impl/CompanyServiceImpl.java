package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;

import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.CompanyService;
import com.matrictime.network.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CompanyServiceImpl extends SystemBaseService implements CompanyService {
    private Map<String,String> map = new HashMap<String,String>(){
        {
            put("00","运营商");
            put("01","大区");
            put("02","小区");
        }
    };



    @Autowired
    CompanyInfoDomainService companyInfoDomainService;

    @Override
    public Result save(CompanyInfoRequest companyInfoRequest) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            companyInfoRequest.setCreateUser(nmplUser.getNickName());
            result = buildResult(companyInfoDomainService.save(companyInfoRequest));
        }catch (Exception e){
            log.info(map.get(companyInfoRequest.getCompanyType())+"创建异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result modify(CompanyInfoRequest companyInfoRequest) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            companyInfoRequest.setUpdateUser(nmplUser.getNickName());
            result = buildResult(companyInfoDomainService.modify(companyInfoRequest));
        }catch (Exception e){
            log.info(map.get(companyInfoRequest.getCompanyType())+"修改异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result delete(CompanyInfoRequest companyInfoRequest) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            companyInfoRequest.setUpdateUser(nmplUser.getNickName());
            result = buildResult(companyInfoDomainService.delete(companyInfoRequest));
        }catch (Exception e){
            log.info(map.get(companyInfoRequest.getCompanyType())+"删除异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<PageInfo> queryByConditon(CompanyInfoRequest companyInfoRequest) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplCompanyInfoVo> pageResult =  new PageInfo<>();
            pageResult = companyInfoDomainService.queryByConditions(companyInfoRequest);
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

}
