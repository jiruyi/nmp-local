package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.CurrentCountRequest;
import com.matrictime.network.response.BelongInformationResponse;
import com.matrictime.network.response.CountBaseStationResponse;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class BaseStationInfoDomainServiceImpl implements BaseStationInfoDomainService {
    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    private NmplDeviceExtraInfoMapper nmplDeviceExtraInfoMapper;

    @Resource
    private NmplBaseStationMapper nmplbaseStationMapper;

    @Resource
    private NmplLinkRelationMapper nmplLinkRelationMapper;

    @Resource
    private NmplPcDataMapper nmplPcDataMapper;

    @Resource
    private NmplStaticRouteMapper nmplStaticRouteMapper;

    @Resource
    private NmplCompanyInfoMapper nmplCompanyInfoMapper;

    @Resource
    private NmplDeviceCountMapper nmplDeviceCountMapper;

    @Resource
    private NmplDeviceExtMapper deviceExtMapper;


    @Override
    public int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        insertCheckUnique(baseStationInfoRequest);
        NmplBaseStation nmplbaseStation = new NmplBaseStation();
        BeanUtils.copyProperties(baseStationInfoRequest,nmplbaseStation);
//        return nmplBaseStationInfoMapper.insertBaseStationInfo(baseStationInfoRequest);
        return nmplbaseStationMapper.insertSelective(nmplbaseStation);
    }

    @Override
    public int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        //查询bid是否重复
        updateCheckUnique(baseStationInfoRequest);
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfo nmplBaseStationInfo = new NmplBaseStationInfo();
        BeanUtils.copyProperties(baseStationInfoRequest,nmplBaseStationInfo);
        nmplBaseStationInfoExample.clear();
        nmplBaseStationInfoExample.createCriteria().andStationIdEqualTo(nmplBaseStationInfo.getStationId());
        return nmplBaseStationInfoMapper.updateByExampleSelective(nmplBaseStationInfo,nmplBaseStationInfoExample);
        //return nmplBaseStationInfoMapper.updateBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public int deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        /**
         * 基站的删除逻辑暂未定，现在支持逻辑删除
         */
        deleteCheck(baseStationInfoRequest);
        return nmplBaseStationInfoMapper.deleteBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public PageInfo<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Page page = PageHelper.startPage(baseStationInfoRequest.getPageNo(),baseStationInfoRequest.getPageSize());
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
        PageInfo<BaseStationInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(baseStationInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<BaseStationInfoVo> selectLinkBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectForRoute(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectForRoute(baseStationInfoRequest);
    }


    @Override
    public List<BaseStationInfoVo> selectActiveBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectActiveBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectBaseStationBatch(List<String> list) {
        return nmplBaseStationInfoMapper.selectBaseStationBatch(list);
    }

    @Override
    public StationVo selectDeviceId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectDeviceId(baseStationInfoRequest);
    }

    @Override
    public BaseStationInfoVo selectByNetworkId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectByNetworkId(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectByOperatorId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectByOperatorId(baseStationInfoRequest);
    }

    @Override
    public PageInfo<BaseStationInfoVo> selectBaseStationList(BaseStationInfoRequest baseStationInfoRequest) {
        Page page = PageHelper.startPage(baseStationInfoRequest.getPageNo(),baseStationInfoRequest.getPageSize());
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationList(baseStationInfoRequest);
        PageInfo<BaseStationInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(baseStationInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<BaseStationInfoVo> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectBaseStation(baseStationInfoRequest);
    }


    @Override
    public void insertCheckUnique(BaseStationInfoRequest baseStationInfoRequest) {
        //同设备ip不可相同
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp()).andIsExistEqualTo(true);
        nmplBaseStationInfoExample.or().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp()).andIsExistEqualTo(true).andDeviceTypeEqualTo("00");
        nmplDeviceExtraInfoExample.or().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("同设备ip或入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(baseStationInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplDeviceInfoExample.or().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(baseStationInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo("00");
        nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("不同设备ip+端口或入网码重复");
        }
    }


    @Override
    public void updateCheckUnique(BaseStationInfoRequest baseStationInfoRequest) {
        //修改时不能修改设备号 以及ip
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.or().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true).andDeviceTypeEqualTo("00");
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            //如果被修改的设备是自己
            if(!nmplBaseStationInfos.get(0).getStationId().equals(baseStationInfoRequest.getStationId())){
                throw new SystemException("基站入网码重复");
            }
        }
        if(!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("备用设备入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(baseStationInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplDeviceInfoExample.or().andStationNetworkIdEqualTo(baseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(baseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(baseStationInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo("00");
        nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("不同设备ip+端口或入网码重复");
        }
    }

    @Override
    public void deleteCheck(BaseStationInfoRequest baseStationInfoRequest) {

        String stationId = baseStationInfoRequest.getStationId();

        NmplLinkRelationExample nmplLinkRelationExample = new NmplLinkRelationExample();
        NmplLinkRelationExample.Criteria criteria = nmplLinkRelationExample.createCriteria();
        criteria.andMainDeviceIdEqualTo(stationId).andIsExistEqualTo("1");
        nmplLinkRelationExample.or().andFollowDeviceIdEqualTo(stationId).andIsExistEqualTo("1");


        NmplPcDataExample nmplPcDataExample = new NmplPcDataExample();
        nmplPcDataExample.createCriteria().andStationIdEqualTo(stationId);

        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        nmplStaticRouteExample.createCriteria().andStationIdEqualTo(stationId).andIsExistEqualTo(true);

        List<NmplLinkRelation> nmplLinkRelations = nmplLinkRelationMapper.selectByExample(nmplLinkRelationExample);

        List<NmplPcData> nmplPcData = nmplPcDataMapper.selectByExample(nmplPcDataExample);

        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);

        if(!CollectionUtils.isEmpty(nmplLinkRelations)||!CollectionUtils.isEmpty(nmplPcData)||!CollectionUtils.isEmpty(nmplStaticRoutes)){
            throw new SystemException(ErrorMessageContants.DEVICE_IS_ASSOCIATED);
        }

    }

    @Override
    public BelongInformationResponse selectBelongInformation() {
        //查询运营商
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andParentCodeIsNull();
        List<NmplCompanyInfo> nmplCompanyInfos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
        BelongInformationResponse belongInformationResponse = new BelongInformationResponse();

        List<RegionBelongVo> operatorList = new ArrayList<>();
        for(NmplCompanyInfo nmplCompanyInfo: nmplCompanyInfos){
            nmplCompanyInfo.getCompanyId();
            NmplCompanyInfoExample nmplCompanyInfoExample1 = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria1 = nmplCompanyInfoExample1.createCriteria();
            criteria1.andParentCodeEqualTo(nmplCompanyInfo.getCompanyId().toString());
            criteria1.andIsExistEqualTo(true);
            //查询运营商下的大区
            List<NmplCompanyInfo> nmplCompanyInfos1 = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample1);
            List<CommunityBelongVo> list = new ArrayList<>();
            RegionBelongVo regionBelongVo = new RegionBelongVo();
            for(NmplCompanyInfo nmplCompanyInfo1: nmplCompanyInfos1){
                NmplCompanyInfoExample nmplCompanyInfoExample2 = new NmplCompanyInfoExample();
                NmplCompanyInfoExample.Criteria criteria2 = nmplCompanyInfoExample2.createCriteria();
                criteria2.andParentCodeEqualTo(nmplCompanyInfo1.getCompanyId().toString());
                criteria2.andIsExistEqualTo(true);
                List<NmplCompanyInfo> nmplCompanyInfos2 = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample2);
                CommunityBelongVo communityBelongVo = new CommunityBelongVo();
                //查询大区下面的小区
                if(CollectionUtils.isEmpty(nmplCompanyInfos2)){
                    communityBelongVo.setRelationOperatorId(nmplCompanyInfo1.getCompanyId().toString());
                    communityBelongVo.setName(nmplCompanyInfo1.getCompanyName());
                }else {
                    communityBelongVo = getCommunity(nmplCompanyInfos2);
                }
                list.add(communityBelongVo);
            }
            regionBelongVo.setRelationOperatorId(nmplCompanyInfo.getCompanyId().toString());
            regionBelongVo.setName(nmplCompanyInfo.getCompanyName());
            regionBelongVo.setChildren(list);
            operatorList.add(regionBelongVo);
        }
        belongInformationResponse.setList(operatorList);
        return belongInformationResponse;
    }

    @Override
    public CountBaseStationResponse countBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        if(!StringUtils.isEmpty(baseStationInfoRequest.getStationType())){
            criteria.andStationTypeEqualTo(baseStationInfoRequest.getStationType());
        }
        if(!StringUtils.isEmpty(baseStationInfoRequest.getRelationOperatorId())){
            criteria.andRelationOperatorIdEqualTo(baseStationInfoRequest.getRelationOperatorId());
        }
        criteria.andIsExistEqualTo(true);
        CountBaseStationResponse countBaseStationResponse = new CountBaseStationResponse();
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            int currentConnectCount = 0;
            for(int i = 0;i< nmplBaseStationInfos.size();i++){
                if(StringUtils.isEmpty(nmplBaseStationInfos.get(i).getCurrentConnectCount())){
                    nmplBaseStationInfos.get(i).setCurrentConnectCount("0");
                }
                currentConnectCount = currentConnectCount +
                        Integer.parseInt(nmplBaseStationInfos.get(i).getCurrentConnectCount());
            }
            countBaseStationResponse.setCountBaseStation(nmplBaseStationInfos.size());
            countBaseStationResponse.setUserCount(currentConnectCount);
        }
        return countBaseStationResponse;
    }

    @Override
    public int updateConnectCount(BaseStationCountRequest baseStationCountRequest) {
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andStationIdEqualTo(baseStationCountRequest.getStationId());
        NmplBaseStationInfo nmplBaseStationInfo = new NmplBaseStationInfo();
        BeanUtils.copyProperties(baseStationCountRequest,nmplBaseStationInfo);
        return nmplBaseStationInfoMapper.updateByExampleSelective(nmplBaseStationInfo,nmplBaseStationInfoExample);
    }

    @Override
    public List<CommunityBaseStationVo> selectPhysicalDevice(BaseStationInfoRequest baseStationInfoRequest) {
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria1 = baseStationInfoExample.createCriteria();
        criteria1.andIsExistEqualTo(true);
        criteria1.andRelationOperatorIdEqualTo(baseStationInfoRequest.getRelationOperatorId());
        List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
        //获取物理基站
        List<CommunityBaseStationVo> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(baseStationInfos)){
            List<String> ipList = new ArrayList<>();
            for(NmplBaseStationInfo baseStationInfo: baseStationInfos){
                ipList.add(baseStationInfo.getLanIp());
            }
            for(NmplBaseStationInfo baseStationInfo: baseStationInfos){
                CommunityBaseStationVo communityBaseStationVo = new CommunityBaseStationVo();
                BeanUtils.copyProperties(baseStationInfo,communityBaseStationVo);
                list.add(communityBaseStationVo);
            }
            //过滤重复ip的基站和密钥分发机
            List<NmplDeviceInfo> deviceInfoList = deviceExtMapper.deduplicationLanIp(ipList);
            for(int i = 0;i<deviceInfoList.size();i++){
                CommunityBaseStationVo communityBaseStationVo = new CommunityBaseStationVo();
                BeanUtils.copyProperties(deviceInfoList.get(i),communityBaseStationVo);
                list.add(communityBaseStationVo);
            }
        }
        return list;
    }

    @Override
    public int updateCurrentConnectCount(CurrentCountRequest currentCountRequest) {
        int i = 0;
        if(!ObjectUtils.isEmpty(currentCountRequest.getBaseStationCurrentRequest())){
            NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
            NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
            criteria.andStationIdEqualTo(currentCountRequest.getBaseStationCurrentRequest().getStationId());
            NmplBaseStationInfo nmplBaseStationInfo = new NmplBaseStationInfo();
            BeanUtils.copyProperties(currentCountRequest.getBaseStationCurrentRequest(),nmplBaseStationInfo);
            i = nmplBaseStationInfoMapper.updateByExampleSelective(nmplBaseStationInfo,nmplBaseStationInfoExample);
        }
        if(!ObjectUtils.isEmpty(currentCountRequest.getDeviceCurrentRequest())){
            NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
            NmplDeviceCountExample.Criteria criteria = nmplDeviceCountExample.createCriteria();
            criteria.andDeviceIdEqualTo(currentCountRequest.getDeviceCurrentRequest().getDeviceId());
            NmplDeviceCount nmplDeviceCount = new NmplDeviceCount();
            BeanUtils.copyProperties(currentCountRequest.getDeviceCurrentRequest(),nmplDeviceCount);
            i = nmplDeviceCountMapper.updateByExampleSelective(nmplDeviceCount,nmplDeviceCountExample);
        }
        return i;
    }

    /**
     * 获取大区下面的小区
     * @param nmplCompanyInfos
     * @return
     */
    private CommunityBelongVo getCommunity(List<NmplCompanyInfo> nmplCompanyInfos){
        CommunityBelongVo communityBelongVo = new CommunityBelongVo();
        if(!CollectionUtils.isEmpty(nmplCompanyInfos)){
            List<BaseStationBelongVo> list = new ArrayList<>();
            for(int i = 0;i< nmplCompanyInfos.size();i++){
                NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
                NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
                criteria.andIsExistEqualTo(true);
                criteria.andRelationOperatorIdEqualTo(nmplCompanyInfos.get(i).getCompanyId().toString());
                criteria.andStationTypeEqualTo(DeviceTypeEnum.BASE_STATION.getCode());
                List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
                //获取小区下面基站信息
                BaseStationBelongVo baseStationBelongVo = new BaseStationBelongVo();
                List<CommunityBaseStationVo> baseStationInfoVoList = new ArrayList<>();
                for(NmplBaseStationInfo nmplBaseStationInfo: nmplBaseStationInfos){
                    CommunityBaseStationVo communityBaseStationVo = new CommunityBaseStationVo();
                    BeanUtils.copyProperties(nmplBaseStationInfo,communityBaseStationVo);
                    baseStationInfoVoList.add(communityBaseStationVo);
                }
                baseStationBelongVo.setRelationOperatorId(nmplCompanyInfos.get(i).getCompanyId().toString());
                baseStationBelongVo.setName(nmplCompanyInfos.get(i).getCompanyName());
                baseStationBelongVo.setChildren(baseStationInfoVoList);
                list.add(baseStationBelongVo);
            }
            NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
            criteria.andCompanyIdEqualTo(Long.parseLong(nmplCompanyInfos.get(0).getParentCode()));
            List<NmplCompanyInfo> nmplCompanyInfos1 = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
            if(!CollectionUtils.isEmpty(nmplCompanyInfos1)){
                communityBelongVo.setRelationOperatorId(nmplCompanyInfos.get(0).getParentCode());
                communityBelongVo.setName(nmplCompanyInfos1.get(0).getCompanyName());
            }
            communityBelongVo.setChildren(list);
        }
        return communityBelongVo;
    }


}