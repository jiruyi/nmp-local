package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;

import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplCompanyInfoExample;
import com.matrictime.network.dao.model.NmplDataPushRecord;
import com.matrictime.network.dao.model.NmplDataPushRecordExample;
import com.matrictime.network.modelVo.CompanyInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class CompanyInfoDomainServiceImpl implements CompanyInfoDomainService {

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;

    @Override
    public List<CompanyInfoVo> selectCompanyInfo() throws Exception {
        List<CompanyInfoVo> companyInfoVoList = new ArrayList<>();
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        nmplCompanyInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplCompanyInfo> infos = companyInfoMapper.selectByExample(nmplCompanyInfoExample);
        Map<String,NmplCompanyInfo> map = new HashMap<>();
        Map<String,String> bidMap = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            map.put(String.valueOf(info.getCompanyId()),info);
        }
        //补充bid
        for (NmplCompanyInfo info : infos) {
            CompanyInfoVo companyInfoVo = new CompanyInfoVo();
            String bid = info.getCompanyCode();
            NmplCompanyInfo nmplCompanyInfo = info;
            while (nmplCompanyInfo.getParentCode()!=null){
                nmplCompanyInfo = map.get(nmplCompanyInfo.getParentCode());
                if(nmplCompanyInfo==null){
                    throw new SystemException("区域信息异常");
                }
                bid =nmplCompanyInfo.getCompanyCode()+"-"+bid;
            }
            bid = nmplCompanyInfo.getCountryCode()+"-"+bid;
            bidMap.put(String.valueOf(info.getCompanyId()),bid);
            BeanUtils.copyProperties(info,companyInfoVo);
            companyInfoVo.setCompanyNetworkId(bid);
            companyInfoVoList.add(companyInfoVo);
        }
        for (CompanyInfoVo companyInfoVo : companyInfoVoList) {
            companyInfoVo.setParentCode(bidMap.get(companyInfoVo.getParentCode()));
        }
        return companyInfoVoList;
    }
}
