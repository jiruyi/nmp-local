package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplCompanyInfoExample;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CompanyInfoDomainServiceImpl implements CompanyInfoDomainService {

    @Autowired
    NmplCompanyInfoMapper nmplCompanyInfoMapper;

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "0?(13|14|15|18)[0-9]{9}";


    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }



    @Override
    public Integer save(CompanyInfoRequest companyInfoRequest) {
        if (companyInfoRequest.getTelephone()!=null){
            if(!isMobile(companyInfoRequest.getTelephone())){
                throw new SystemException("电话格式异常");
            }
        }
        if (companyInfoRequest.getParentCode()!=null){
            NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
            nmplCompanyInfoExample.createCriteria().andCompanyCodeEqualTo(companyInfoRequest.getParentCode()).andIsExistEqualTo(true);
            List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
            if (CollectionUtils.isEmpty(infos)){
                throw new SystemException("无父单位信息");
            }
        }

        NmplCompanyInfo nmplCompanyInfo = new NmplCompanyInfo();
        BeanUtils.copyProperties(companyInfoRequest,nmplCompanyInfo);
        nmplCompanyInfo.setCreateTime(new Date());
        nmplCompanyInfo.setUpdateTime(new Date());
        nmplCompanyInfo.setIsExist(true);
        return nmplCompanyInfoMapper.insert(nmplCompanyInfo);
    }

    @Override
    public Integer delete(CompanyInfoRequest companyInfoRequest) {
        if(companyInfoRequest.getCompanyId()==null){
            throw new SystemException("id参数缺失");
        }
        //删除逻辑未定
        NmplCompanyInfo info = nmplCompanyInfoMapper.selectByPrimaryKey(companyInfoRequest.getCompanyId());
        if (!info.getCompanyType().equals(companyInfoRequest.getCompanyType())){
            throw new SystemException("删除区域类型不一致");
        }

        NmplCompanyInfo nmplCompanyInfo = new NmplCompanyInfo();
        nmplCompanyInfo.setCompanyId(companyInfoRequest.getCompanyId());
        nmplCompanyInfo.setIsExist(false);
        nmplCompanyInfo.setUpdateTime(new Date());
        return nmplCompanyInfoMapper.updateByPrimaryKeySelective(nmplCompanyInfo);
    }

    @Override
    public Integer modify(CompanyInfoRequest companyInfoRequest) {
        if(companyInfoRequest.getCompanyId()==null){
            throw new SystemException("id参数缺失");
        }
        NmplCompanyInfo info = nmplCompanyInfoMapper.selectByPrimaryKey(companyInfoRequest.getCompanyId());
        if (!info.getCompanyType().equals(companyInfoRequest.getCompanyType())){
            throw new SystemException("修改区域类型不一致");
        }
        if (companyInfoRequest.getTelephone()!=null){
            if(!isMobile(companyInfoRequest.getTelephone())){
                throw new SystemException("电话格式异常");
            }
        }
        NmplCompanyInfo nmplCompanyInfo = new NmplCompanyInfo();
        BeanUtils.copyProperties(companyInfoRequest,nmplCompanyInfo);
        nmplCompanyInfo.setUpdateTime(new Date());
        return nmplCompanyInfoMapper.updateByPrimaryKeySelective(nmplCompanyInfo);
    }

    @Override
    public PageInfo<NmplCompanyInfoVo> queryByConditions(CompanyInfoRequest companyInfoRequest) {
        List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(null);
        Map<String,String> map = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            map.put(info.getCompanyCode(),info.getCompanyName());
        }
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
        if (companyInfoRequest.getCompanyName()!=null){
            criteria.andCompanyNameEqualTo(companyInfoRequest.getCompanyName());
        }
        if (companyInfoRequest.getCompanyType()!=null){
            criteria.andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());
        }
        if (companyInfoRequest.getCompanyCode()!=null){
            criteria.andCompanyCodeEqualTo(companyInfoRequest.getCompanyCode());
        }
        if (companyInfoRequest.getParentCode()!=null){
            criteria.andParentCodeEqualTo(companyInfoRequest.getParentCode());
        }
        criteria.andIsExistEqualTo(true);
        Page page = PageHelper.startPage(companyInfoRequest.getPageNo(),companyInfoRequest.getPageSize());
        List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);

        PageInfo<NmplCompanyInfoVo> pageResult =  new PageInfo<>();
        List<NmplCompanyInfoVo> nmplCompanyInfos = new ArrayList<>();
        for (NmplCompanyInfo nmplCompanyInfo : nmplCompanyInfoList) {
            NmplCompanyInfoVo companyInfo = new NmplCompanyInfoVo();
            BeanUtils.copyProperties(nmplCompanyInfo,companyInfo);
            if (companyInfo.getParentCode()!=null){
                companyInfo.setParentName(map.get(companyInfo.getParentCode()));
            }
            nmplCompanyInfos.add(companyInfo);
        }

        pageResult.setList(nmplCompanyInfos);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }
}
