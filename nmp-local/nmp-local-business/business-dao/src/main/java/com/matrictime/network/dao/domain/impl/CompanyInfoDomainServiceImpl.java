package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyInfoDomainServiceImpl implements CompanyInfoDomainService {

    @Autowired
    NmplCompanyInfoMapper nmplCompanyInfoMapper;
    @Autowired
    NmplDeviceInfoMapper nmplDeviceInfoMapper;
    @Autowired
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

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

        if(companyInfoRequest.getCompanyCode()!=null){
            NmplCompanyInfoExample nmplCompanyInfoExample1 = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria1 = nmplCompanyInfoExample1.createCriteria();
            criteria1.andCompanyCodeEqualTo(companyInfoRequest.getCompanyCode()).andIsExistEqualTo(true);
            List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample1);
            if(!CollectionUtils.isEmpty(infos)){
                throw new SystemException("编码重复");
            }
        }
        if (companyInfoRequest.getParentCode()!=null){
            NmplCompanyInfoExample nmplCompanyInfoExample2 = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria2 = nmplCompanyInfoExample2.createCriteria();
            criteria2.andCompanyCodeEqualTo(companyInfoRequest.getParentCode()).andIsExistEqualTo(true);
            List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample2);
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
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        //删除逻辑判定 大类下面有小类则无法删除
        List<NmplCompanyInfo> childs = new ArrayList<>();
        List<NmplDeviceInfo> deviceInfos = new ArrayList<>();
        List<NmplBaseStationInfo> baseStationInfos = new ArrayList<>();
        switch (companyInfoRequest.getCompanyType()){
            case "00":
                nmplCompanyInfoExample.createCriteria().andParentCodeEqualTo(info.getCompanyCode()).andIsExistEqualTo(true)
                        .andCompanyTypeEqualTo("01");
                childs = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
                if(!CollectionUtils.isEmpty(childs)){
                    throw new SystemException("该运营商被大区绑定");
                }
                break;
            case "01":
                nmplCompanyInfoExample.createCriteria().andParentCodeEqualTo(info.getCompanyCode()).andIsExistEqualTo(true)
                        .andCompanyTypeEqualTo("02");
                 childs = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
                if(!CollectionUtils.isEmpty(childs)){
                    throw new SystemException("该大区被小区绑定");
                }
                break;
            case "02":
                nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(info.getCompanyCode()).andIsExistEqualTo(true);
                deviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

                nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(info.getCompanyCode()).andIsExistEqualTo(true);
                baseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
                if(!CollectionUtils.isEmpty(deviceInfos)||!CollectionUtils.isEmpty(baseStationInfos)){
                    throw new SystemException("该小区被设备绑定");
                }
                break;
        }
        NmplCompanyInfo nmplCompanyInfo = new NmplCompanyInfo();
        nmplCompanyInfo.setCompanyId(companyInfoRequest.getCompanyId());
        nmplCompanyInfo.setIsExist(false);
        nmplCompanyInfo.setUpdateTime(new Date());
        nmplCompanyInfo.setUpdateUser(companyInfoRequest.getUpdateUser());
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
        if(companyInfoRequest.getCompanyCode()!=null){
            NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
            nmplCompanyInfoExample.createCriteria().andCompanyCodeEqualTo(companyInfoRequest.getCompanyCode()).andIsExistEqualTo(true);
            List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
            if(!CollectionUtils.isEmpty(nmplCompanyInfoList)){
                if(!nmplCompanyInfoList.get(0).getCompanyId().equals(companyInfoRequest.getCompanyId())){
                    throw new SystemException("编码重复");
                }
            }
        }
        NmplCompanyInfo nmplCompanyInfo = new NmplCompanyInfo();
        BeanUtils.copyProperties(companyInfoRequest,nmplCompanyInfo);
        nmplCompanyInfo.setUpdateTime(new Date());
        nmplCompanyInfo.setUpdateUser(companyInfoRequest.getUpdateUser());
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

        nmplCompanyInfos = nmplCompanyInfos.stream()
                        .sorted(Comparator.comparing(NmplCompanyInfoVo::getCompanyId).reversed())
                        .collect(Collectors.toList());
        pageResult.setList(nmplCompanyInfos);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }


    @Override
    public String getPreBID(String companyCode) {
        List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(null);
        Map<String,NmplCompanyInfo> map = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            map.put(info.getCompanyCode(),info);
        }
        if(map.get(companyCode)!=null){
            NmplCompanyInfo village = map.get(companyCode);
            NmplCompanyInfo region = map.get(village.getParentCode());
            NmplCompanyInfo operator = map.get(region.getParentCode());
            return operator.getCountryCode()+"-"+operator.getCompanyCode()+"-"+region.getCompanyCode()+"-"+village.getCompanyCode();
        } else {
            return "";
        }
    }

    @Override
    public List<NmplCompanyInfoVo> queryCompanyList(CompanyInfoRequest companyInfoRequest) {
        Map<String,NmplCompanyInfo>map = new HashMap<>();
        if(companyInfoRequest.getCompanyType().equals("02")){
            List<NmplCompanyInfo> allCompanyInfo = nmplCompanyInfoMapper.selectByExample(null);
            for (NmplCompanyInfo nmplCompanyInfo : allCompanyInfo) {
                map.put(nmplCompanyInfo.getCompanyCode(),nmplCompanyInfo);
            }
        }
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();

        if (companyInfoRequest.getCompanyType()!=null){
            criteria.andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());
        }
        criteria.andIsExistEqualTo(true);

        List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
        List<NmplCompanyInfoVo> nmplCompanyInfos = new ArrayList<>();
        for (NmplCompanyInfo nmplCompanyInfo : nmplCompanyInfoList) {
            NmplCompanyInfoVo companyInfo = new NmplCompanyInfoVo();
            BeanUtils.copyProperties(nmplCompanyInfo,companyInfo);
            if(companyInfo.getCompanyType().equals("02")){
                //通过map找到大区id 大区名称
                companyInfo.setRegionId(map.get(companyInfo.getParentCode()).getCompanyId());
                companyInfo.setRegionName(map.get(companyInfo.getParentCode()).getCompanyName());

                //找到运营商id,运营商名称
                companyInfo.setOperatorId(map.get(map.get(companyInfo.getParentCode()).getParentCode()).getCompanyId());
                companyInfo.setOperatorName(map.get(map.get(companyInfo.getParentCode()).getParentCode()).getCompanyName());
            }
            nmplCompanyInfos.add(companyInfo);
        }
        return nmplCompanyInfos;

    }

}
