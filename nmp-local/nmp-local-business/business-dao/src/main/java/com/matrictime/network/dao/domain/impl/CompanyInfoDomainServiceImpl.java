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
        NmplCompanyInfoExample nmplCompanyInfoExample1 = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria1 = nmplCompanyInfoExample1.createCriteria();
        if (companyInfoRequest.getParentCode()!=null){
            criteria1.andParentCodeEqualTo(companyInfoRequest.getParentCode());
            NmplCompanyInfoExample nmplCompanyInfoExample2 = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria2 = nmplCompanyInfoExample2.createCriteria();
            if(companyInfoRequest.getCompanyType().equals("01")){
                criteria2.andCompanyIdEqualTo(Long.valueOf(companyInfoRequest.getParentCode())).andIsExistEqualTo(true).andCompanyTypeEqualTo("00");
            }else {
                criteria2.andCompanyIdEqualTo(Long.valueOf(companyInfoRequest.getParentCode())).andIsExistEqualTo(true).andCompanyTypeEqualTo("01");
            }
            List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample2);
            if (CollectionUtils.isEmpty(infos)){
                throw new SystemException("无父单位信息");
            }
        }

        if(companyInfoRequest.getCompanyCode()!=null){
            criteria1.andCompanyCodeEqualTo(companyInfoRequest.getCompanyCode()).andIsExistEqualTo(true)
                    .andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());;
            List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample1);
            if(!CollectionUtils.isEmpty(infos)){
                throw new SystemException("编码重复");
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
                nmplCompanyInfoExample.createCriteria().andParentCodeEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true)
                        .andCompanyTypeEqualTo("01");
                childs = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
                if(!CollectionUtils.isEmpty(childs)){
                    throw new SystemException("该运营商被大区绑定,解绑后可删除");
                }
                break;
            case "01":
                nmplCompanyInfoExample.createCriteria().andParentCodeEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true)
                        .andCompanyTypeEqualTo("02");
                 childs = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
                if(!CollectionUtils.isEmpty(childs)){
                    throw new SystemException("该大区被小区绑定,解绑后可删除");
                }
                break;
            case "02":
                nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true);
                deviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

                nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true);
                baseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
                if(!CollectionUtils.isEmpty(deviceInfos)||!CollectionUtils.isEmpty(baseStationInfos)){
                    throw new SystemException("该小区被设备绑定,解绑后可删除");
                }
                break;
            default:
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
        if(companyInfoRequest.getCompanyId()==null&&companyInfoRequest.getCompanyCode()==null){
            throw new SystemException("参数缺失");
        }
        NmplCompanyInfo info = nmplCompanyInfoMapper.selectByPrimaryKey(companyInfoRequest.getCompanyId());
        if (!info.getCompanyType().equals(companyInfoRequest.getCompanyType())){
            throw new SystemException("修改区域类型不一致");
        }
        if(companyInfoRequest.getCompanyCode()!=null){
            NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
            if(companyInfoRequest.getParentCode()!=null){
                criteria.andParentCodeEqualTo(companyInfoRequest.getParentCode());
            }
            criteria.andCompanyCodeEqualTo(companyInfoRequest.getCompanyCode()).andIsExistEqualTo(true)
                    .andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());
            List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
            if(!CollectionUtils.isEmpty(nmplCompanyInfoList)){

                if(!nmplCompanyInfoList.get(0).getCompanyId().equals(companyInfoRequest.getCompanyId())){
                    throw new SystemException("编码重复");
                }
            }
        }
        List<NmplCompanyInfo> childs = new ArrayList<>();
        List<NmplDeviceInfo> deviceInfos = new ArrayList<>();
        List<NmplBaseStationInfo> baseStationInfos = new ArrayList<>();
        if(!companyInfoRequest.getCompanyType().equals("02")){
            String childType = "";
            if(companyInfoRequest.getCompanyType().equals("00")){
                childType = "01";
            }
            if(companyInfoRequest.getCompanyType().equals("01")){
                childType = "02";
            }
            NmplCompanyInfoExample nmplCompanyInfoExample1 = new NmplCompanyInfoExample();
            nmplCompanyInfoExample1.createCriteria().andParentCodeEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true)
                    .andCompanyTypeEqualTo(childType);
            childs = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample1);
        }else {
            NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
            NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
            nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true);
            deviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
            nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(String.valueOf(info.getCompanyId())).andIsExistEqualTo(true);
            baseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        }
        if(!CollectionUtils.isEmpty(childs)||!CollectionUtils.isEmpty(deviceInfos)||!CollectionUtils.isEmpty(baseStationInfos)){
            if(!companyInfoRequest.getCompanyCode().equals(info.getCompanyCode())){
                throw new SystemException("存在子单位关联关系，无法修改code");
            }
            if(companyInfoRequest.getParentCode()!=null&&!companyInfoRequest.getParentCode().equals(info.getParentCode())){
                throw new SystemException("存在子单位关联关系，无法修改关联关系");
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

        //寻找区域的父级关系
        String parentType = "";
        if(companyInfoRequest.getCompanyType().equals("01")){
            parentType = "00";
        }else if(companyInfoRequest.getCompanyType().equals("02")) {
            parentType = "01";
        }
        NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
        companyInfoExample.createCriteria().andIsExistEqualTo(true).andCompanyTypeEqualTo(parentType);
        List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(companyInfoExample);
        Map<String,NmplCompanyInfo> map = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            map.put(String.valueOf(info.getCompanyId()),info);
        }
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
        if (companyInfoRequest.getCompanyName()!=null){
            criteria.andCompanyNameLike("%"+companyInfoRequest.getCompanyName()+"%");
        }
        if (companyInfoRequest.getCompanyType()!=null){
            criteria.andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());
        }
        if (companyInfoRequest.getCompanyCode()!=null){
            criteria.andCompanyCodeLike("%"+companyInfoRequest.getCompanyCode()+"%");
        }
        if (companyInfoRequest.getParentCode()!=null){
            List<String> ids = new ArrayList<>();
            for (NmplCompanyInfo info : infos) {
                if(info.getCompanyCode().contains(companyInfoRequest.getParentCode())){
                    ids.add(String.valueOf(info.getCompanyId()));
                }
            }
            criteria.andParentCodeIn(ids);
        }
        criteria.andIsExistEqualTo(true);
        nmplCompanyInfoExample.setOrderByClause("create_time desc");
        Page page = PageHelper.startPage(companyInfoRequest.getPageNo(),companyInfoRequest.getPageSize());
        List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);

        PageInfo<NmplCompanyInfoVo> pageResult =  new PageInfo<>();
        List<NmplCompanyInfoVo> nmplCompanyInfos = new ArrayList<>();
        for (NmplCompanyInfo nmplCompanyInfo : nmplCompanyInfoList) {
            NmplCompanyInfoVo companyInfo = new NmplCompanyInfoVo();
            BeanUtils.copyProperties(nmplCompanyInfo,companyInfo);
            if (companyInfo.getParentCode()!=null){
                companyInfo.setParentName(map.get(companyInfo.getParentCode()).getCompanyName());
                companyInfo.setFatherCode(map.get(companyInfo.getParentCode()).getCompanyCode());
            }
            nmplCompanyInfos.add(companyInfo);
        }
        pageResult.setList(nmplCompanyInfos);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }


    @Override
    public String getPreBID(String companyId) {

        NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
        companyInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(companyInfoExample);
        Map<String,NmplCompanyInfo> operatorMap = new HashMap<>();
        Map<String,NmplCompanyInfo> regionMap = new HashMap<>();
        Map<String,NmplCompanyInfo> villageMap = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            if(info.getCompanyType().equals("00")){
                operatorMap.put(String.valueOf(info.getCompanyId()),info);
            }
            if(info.getCompanyType().equals("01")){
                regionMap.put(String.valueOf(info.getCompanyId()),info);
            }
            if(info.getCompanyType().equals("02")){
                villageMap.put(String.valueOf(info.getCompanyId()),info);
            }
        }
        if(villageMap.get(companyId)!=null){
            NmplCompanyInfo village = villageMap.get(companyId);
            NmplCompanyInfo region = regionMap.get(village.getParentCode());
            if(region==null){
                return "";
            }
            NmplCompanyInfo operator = operatorMap.get(region.getParentCode());
            if(operator==null){
                return "";
            }
            return operator.getCountryCode()+"-"+operator.getCompanyCode()+"-"+region.getCompanyCode()+"-"+village.getCompanyCode();
        } else {
            return "";
        }
    }

    @Override
    public List<NmplCompanyInfoVo> queryCompanyList(CompanyInfoRequest companyInfoRequest) {

        Map<String,NmplCompanyInfo> operatorMap = new HashMap<>();
        Map<String,NmplCompanyInfo> regionMap = new HashMap<>();
        if(companyInfoRequest.getCompanyType().equals("02")){
            NmplCompanyInfoExample companyInfoExample1 = new NmplCompanyInfoExample();
            companyInfoExample1.createCriteria().andIsExistEqualTo(true);
            List<NmplCompanyInfo> allCompanyInfo = nmplCompanyInfoMapper.selectByExample(companyInfoExample1);
            for (NmplCompanyInfo nmplCompanyInfo : allCompanyInfo) {
                if(nmplCompanyInfo.getCompanyType().equals("00")){
                    operatorMap.put(String.valueOf(nmplCompanyInfo.getCompanyId()),nmplCompanyInfo);
                }
                if(nmplCompanyInfo.getCompanyType().equals("01")){
                    regionMap.put(String.valueOf(nmplCompanyInfo.getCompanyId()),nmplCompanyInfo);
                }
            }
        }
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();

        if (companyInfoRequest.getCompanyType()!=null){
            criteria.andCompanyTypeEqualTo(companyInfoRequest.getCompanyType());
        }
        criteria.andIsExistEqualTo(true);
        nmplCompanyInfoExample.setOrderByClause("create_time desc");
        List<NmplCompanyInfo> nmplCompanyInfoList = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
        List<NmplCompanyInfoVo> nmplCompanyInfos = new ArrayList<>();

        //查询所有的区域信息，获取bid
        NmplCompanyInfoExample companyInfoExample2 = new NmplCompanyInfoExample();
        companyInfoExample2.createCriteria().andIsExistEqualTo(true);
        List<NmplCompanyInfo> infos = nmplCompanyInfoMapper.selectByExample(companyInfoExample2);
        Map<String,NmplCompanyInfo> map = new HashMap<>();
        for (NmplCompanyInfo info : infos) {
            map.put(String.valueOf(info.getCompanyId()),info);
        }

        for (NmplCompanyInfo nmplCompanyInfo : nmplCompanyInfoList) {
            NmplCompanyInfoVo companyInfo = new NmplCompanyInfoVo();
            BeanUtils.copyProperties(nmplCompanyInfo,companyInfo);
            if(companyInfo.getCompanyType().equals("02")){
                //通过map找到大区id 大区名称
                companyInfo.setRegionId(regionMap.get(companyInfo.getParentCode()).getCompanyId());
                companyInfo.setRegionName(regionMap.get(companyInfo.getParentCode()).getCompanyName());

                //找到运营商id,运营商名称
                companyInfo.setOperatorId(operatorMap.get(regionMap.get(companyInfo.getParentCode()).getParentCode()).getCompanyId());
                companyInfo.setOperatorName(operatorMap.get(regionMap.get(companyInfo.getParentCode()).getParentCode()).getCompanyName());
            }
            companyInfo.setBid(getBid(String.valueOf(companyInfo.getCompanyId()),map));
            nmplCompanyInfos.add(companyInfo);
        }
        return nmplCompanyInfos;
    }



    private String getBid(String companyId,Map<String,NmplCompanyInfo>map){
        NmplCompanyInfo nmplCompanyInfo = map.get(companyId);
        String bid = nmplCompanyInfo.getCompanyCode();
        while (nmplCompanyInfo.getParentCode()!=null){
            nmplCompanyInfo = map.get(nmplCompanyInfo.getParentCode());
            bid = nmplCompanyInfo.getCompanyCode()+"-"+bid;
        }
        bid = nmplCompanyInfo.getCountryCode()+"-"+bid;
        return bid;
    }

}
