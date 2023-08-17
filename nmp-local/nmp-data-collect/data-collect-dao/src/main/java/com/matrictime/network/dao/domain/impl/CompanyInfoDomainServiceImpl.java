package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;

import com.matrictime.network.modelVo.CompanyInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class CompanyInfoDomainServiceImpl implements CompanyInfoDomainService {

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;

    @Override
    public List<CompanyInfoVo> selectCompanyInfo() {

        return null;
    }
}
