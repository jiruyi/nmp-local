package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.CompanyDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplCompanyInfoExample;
import com.matrictime.network.modelVo.CompanyInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service
public class CompanyDomainServiceImpl implements CompanyDomainService {

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;


    @Override
    public int insertCompany(CompanyInfoVo companyInfoVo) {
        NmplCompanyInfo companyInfo = new NmplCompanyInfo();
        BeanUtils.copyProperties(companyInfoVo,companyInfo);
        return companyInfoMapper.insertSelective(companyInfo);
    }

    @Override
    public int updateCompany(CompanyInfoVo companyInfoVo) {
        NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = companyInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andCompanyNetworkIdEqualTo(companyInfoVo.getCompanyNetworkId());
        NmplCompanyInfo companyInfo = new NmplCompanyInfo();
        BeanUtils.copyProperties(companyInfoVo,companyInfo);
        return companyInfoMapper.updateByExampleSelective(companyInfo,companyInfoExample);
    }

    @Override
    public List<NmplCompanyInfo> selectCompany(CompanyInfoVo companyInfoVo) {
        NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = companyInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andCompanyNetworkIdEqualTo(companyInfoVo.getCompanyNetworkId());
        List<NmplCompanyInfo> companyInfos = companyInfoMapper.selectByExample(companyInfoExample);
        return companyInfos;
    }
}
