package com.matrictime.network.dao.domain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
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
import com.matrictime.network.model.PortModel;
import com.matrictime.network.model.PublicNetworkIp;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BorderBaseStationInfoRequest;
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
import java.util.*;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;


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
//
//    @Resource
//    private NmplLinkRelationMapper nmplLinkRelationMapper;

    @Resource
    private NmplLinkMapper nmplLinkMapper;


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

    /**
     * 插入边界基站校验
     * @param borderBaseStationInfoRequest
     */
    public void insertCheckBorder(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        //同设备ip不可相同
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp()).andIsExistEqualTo(true);
        nmplBaseStationInfoExample.or().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp()).andIsExistEqualTo(true).andDeviceTypeEqualTo("00");
        nmplDeviceExtraInfoExample.or().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("同设备ip或入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(borderBaseStationInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplDeviceInfoExample.or().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(borderBaseStationInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo("00");
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


    /**
     * 边界基站更新校验
     * @param borderBaseStationInfoRequest
     */
    public void updateCheckBorder(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        //修改时不能修改设备号 以及ip
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.or().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true).andDeviceTypeEqualTo("00");
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            //如果被修改的设备是自己
            if(!nmplBaseStationInfos.get(0).getStationId().equals(borderBaseStationInfoRequest.getStationId())){
                throw new SystemException("基站入网码重复");
            }
        }
        if(!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("备用设备入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(borderBaseStationInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplDeviceInfoExample.or().andStationNetworkIdEqualTo(borderBaseStationInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(borderBaseStationInfoRequest.getLanIp())
                .andLanPortEqualTo(borderBaseStationInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo("00");
        nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("不同设备ip+端口或入网码重复");
        }
    }


    @Override
    public void deleteCheck(BaseStationInfoRequest baseStationInfoRequest) {

        String stationId = baseStationInfoRequest.getStationId();

        NmplLinkExample nmplLinkExample = new NmplLinkExample();
        NmplLinkExample.Criteria criteria = nmplLinkExample.createCriteria();
        criteria.andMainDeviceIdEqualTo(stationId).andIsExistEqualTo(IS_EXIST);
        nmplLinkExample.or().andFollowDeviceIdEqualTo(stationId).andIsExistEqualTo(IS_EXIST);

        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        nmplStaticRouteExample.createCriteria().andStationIdEqualTo(stationId).andIsExistEqualTo(IS_EXIST);

        List<NmplLink> nmplLinks = nmplLinkMapper.selectByExample(nmplLinkExample);
        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);

        if(!CollectionUtils.isEmpty(nmplLinks)||!CollectionUtils.isEmpty(nmplStaticRoutes)){
            throw new SystemException(ErrorMessageContants.DEVICE_IS_ASSOCIATED);
        }

    }

    /**
     * 查询归属信息
     * @return
     */
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
            NmplCompanyInfoExample nmplCompanyInfoExample1 = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria1 = nmplCompanyInfoExample1.createCriteria();
            criteria1.andParentCodeEqualTo(nmplCompanyInfo.getCompanyId().toString());
            criteria1.andIsExistEqualTo(true);
            //查询运营商下的大区
            List<NmplCompanyInfo> companyInfos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample1);
            List<CommunityBelongVo> list;
            RegionBelongVo regionBelongVo = new RegionBelongVo();
            //获取大区下面的小区
            list = getCommunityBelong(companyInfos);
            //数据结构拼接
            regionBelongVo.setRelationOperatorId(nmplCompanyInfo.getCompanyId().toString());
            regionBelongVo.setName(nmplCompanyInfo.getCompanyName());
            regionBelongVo.setChildren(list);
            operatorList.add(regionBelongVo);
        }
        belongInformationResponse.setList(operatorList);
        return belongInformationResponse;
    }

    /**
     * 查询出小区下面的所有小区
     * @param list 运营商下面的大区
     * @return
     */
    private List<CommunityBelongVo> getCommunityBelong(List<NmplCompanyInfo> list){
        List<CommunityBelongVo> communityBelongVoList = new ArrayList<>();
        for(NmplCompanyInfo nmplCompanyInfo: list){
            NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
            NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
            criteria.andParentCodeEqualTo(nmplCompanyInfo.getCompanyId().toString());
            criteria.andIsExistEqualTo(true);
            //查询大区下面的小区
            List<NmplCompanyInfo> companyInfos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
            CommunityBelongVo communityBelongVo = new CommunityBelongVo();
            if(CollectionUtils.isEmpty(companyInfos)){
                communityBelongVo.setRelationOperatorId(nmplCompanyInfo.getCompanyId().toString());
                communityBelongVo.setName(nmplCompanyInfo.getCompanyName());
            }else {
                communityBelongVo = getCommunity(companyInfos);
            }
            communityBelongVoList.add(communityBelongVo);
        }
        return communityBelongVoList;
    }

    /**
     * 查询基站总数
     * @param baseStationInfoRequest
     * @return
     */
    @Override
    public CountBaseStationResponse countBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        //更加参数来判断查询那种基站
        if(!StringUtils.isEmpty(baseStationInfoRequest.getStationType())){
            criteria.andStationTypeEqualTo(baseStationInfoRequest.getStationType());
        }
        if(!StringUtils.isEmpty(baseStationInfoRequest.getRelationOperatorId())){
            criteria.andRelationOperatorIdEqualTo(baseStationInfoRequest.getRelationOperatorId());
        }
        criteria.andIsExistEqualTo(true);
        CountBaseStationResponse countBaseStationResponse = new CountBaseStationResponse();
        //查询小区下面的所有基站
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            int currentConnectCount = 0;
            //累计基站下面当前用户数
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

    /**
     * 查询不同Ip物理设备
     * @param baseStationInfoRequest
     * @return
     */
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

    /**
     * 更新当前用户数
     * @param currentCountRequest
     * @return
     */
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
     * 获取归属信息
     * @return
     */
    @Override
    public BelongInformationResponse selectAllBelongInformation() {
        BelongInformationResponse belongInformationResponse = new BelongInformationResponse();
        //运营商数据查询
        NmplCompanyInfoExample nmplCompanyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = nmplCompanyInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        List<NmplCompanyInfo> nmplCompanyInfos = nmplCompanyInfoMapper.selectByExample(nmplCompanyInfoExample);
        if(CollectionUtils.isEmpty(nmplCompanyInfos)){
            return belongInformationResponse;
        }
        //查询所有基站
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria stationCriteria = nmplBaseStationInfoExample.createCriteria();
        stationCriteria.andIsExistEqualTo(true);
        stationCriteria.andStationTypeEqualTo(DeviceTypeEnum.BASE_STATION.getCode());
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        //运营商
        List<NmplCompanyInfo> companyList = new ArrayList<>();
        List<RegionBelongVo> regionBelongVoList = new ArrayList<>();
        //获取运营商
        for(NmplCompanyInfo nmplCompanyInfo: nmplCompanyInfos){
            if(nmplCompanyInfo.getParentCode() == null){
                companyList.add(nmplCompanyInfo);
            }
        }
        for(NmplCompanyInfo company: companyList){
            List<NmplCompanyInfo> regionList = new ArrayList<>();
            //获取大区
            for(NmplCompanyInfo companyInfo: nmplCompanyInfos){
                if(company.getCompanyId().toString().
                        equals(companyInfo.getParentCode())){
                    regionList.add(companyInfo);
                }
            }
            //大区构建
            RegionBelongVo region = getRegion(regionList, nmplCompanyInfos, company, nmplBaseStationInfos);
            regionBelongVoList.add(region);
        }
        belongInformationResponse.setList(regionBelongVoList);
        return belongInformationResponse;
    }

    /**
     * 插入边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public int insertBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        //插入校验
        insertCheckBorder(borderBaseStationInfoRequest);
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
        if(!CollectionUtils.isEmpty(baseStationInfoVoList)){
            if(!checkIpPort(borderBaseStationInfoRequest, baseStationInfoVoList)){
                throw new RuntimeException("端口ip不唯一");
            }
        }
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andPublicNetworkIpEqualTo(borderBaseStationInfoRequest.getPublicNetworkIp().getCommunicationIP());
        criteria.andIsExistEqualTo(true);
        List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(!CollectionUtils.isEmpty(baseStationInfos) &&
                !"".equals(borderBaseStationInfoRequest.getPublicNetworkIp().getCommunicationIP())){
            throw new RuntimeException("公共ip不唯一");
        }
        //数据插入
        NmplBaseStation nmplbaseStation = new NmplBaseStation();
        BeanUtils.copyProperties(borderBaseStationInfoRequest,nmplbaseStation);
        PortModel portModel = new PortModel();
        PublicNetworkIp publicNetworkIp = borderBaseStationInfoRequest.getPublicNetworkIp();
        portModel.setEphemeralPort(publicNetworkIp.getEphemeralPort());
        portModel.setSignalingPort(publicNetworkIp.getSignalingPort());
        portModel.setTrunkPort(publicNetworkIp.getTrunkPort());
        //将端口转换成字符串
        nmplbaseStation.setPublicNetworkPort(JSON.toJSONString(portModel));
        nmplbaseStation.setPublicNetworkIp(publicNetworkIp.getCommunicationIP());
        return nmplbaseStationMapper.insertSelective(nmplbaseStation);

    }

    /**
     * 更新边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public int updateBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {

        //更新校验
        updateCheckBorder(borderBaseStationInfoRequest);
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
        for(BaseStationInfoVo baseStationInfoVo: baseStationInfoVoList){
            if(baseStationInfoVo.getStationId().equals(borderBaseStationInfoRequest.getStationId())){
                baseStationInfoVoList.remove(baseStationInfoVo);
                break;
            }
        }
        if(!CollectionUtils.isEmpty(baseStationInfoVoList)){
            //校验唯一性
            if(!checkIpPort(borderBaseStationInfoRequest, baseStationInfoVoList)){
                throw new RuntimeException("端口ip不唯一");
            }
        }
        //校验公共ip
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andPublicNetworkIpEqualTo(borderBaseStationInfoRequest.getPublicNetworkIp().getCommunicationIP());
        criteria.andStationIdNotEqualTo(borderBaseStationInfoRequest.getStationId());
        List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(!CollectionUtils.isEmpty(baseStationInfos)){
            throw new RuntimeException("公共ip不唯一");
        }

        //数据更新
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfo nmplBaseStationInfo = new NmplBaseStationInfo();
        BeanUtils.copyProperties(borderBaseStationInfoRequest,nmplBaseStationInfo);
        PublicNetworkIp publicNetworkIp = borderBaseStationInfoRequest.getPublicNetworkIp();
        PortModel portModel = new PortModel();
        portModel.setEphemeralPort(publicNetworkIp.getEphemeralPort());
        portModel.setSignalingPort(publicNetworkIp.getSignalingPort());
        portModel.setTrunkPort(publicNetworkIp.getTrunkPort());
        //将端口转换成字符串
        nmplBaseStationInfo.setPublicNetworkPort(JSON.toJSONString(portModel));
        nmplBaseStationInfo.setPublicNetworkIp(publicNetworkIp.getCommunicationIP());
        nmplBaseStationInfoExample.clear();
        nmplBaseStationInfoExample.createCriteria().andStationIdEqualTo(nmplBaseStationInfo.getStationId());
        return nmplBaseStationInfoMapper.updateByExampleSelective(nmplBaseStationInfo,nmplBaseStationInfoExample);


    }

    /**
     * 删除边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public int deleteBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        baseStationInfoRequest.setStationId(borderBaseStationInfoRequest.getStationId());
        return nmplBaseStationInfoMapper.deleteBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public PageInfo<BorderBaseStationInfoVo> selectBorderBaseStationInfo(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        //构建查询条件
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        if(!StringUtils.isEmpty(borderBaseStationInfoRequest.getRelationOperatorId())){
            baseStationInfoRequest.setRelationOperatorId(borderBaseStationInfoRequest.getRelationOperatorId());
        }
        if(!StringUtils.isEmpty(borderBaseStationInfoRequest.getStationId())){
            baseStationInfoRequest.setStationId(borderBaseStationInfoRequest.getStationId());
        }
        if(!StringUtils.isEmpty(borderBaseStationInfoRequest.getStationName())){
            baseStationInfoRequest.setStationName(borderBaseStationInfoRequest.getStationName());
        }
        baseStationInfoRequest.setStationType(StationTypeEnum.BOUNDARY.getCode());
        //进行分页查询
        Page page = PageHelper.startPage(borderBaseStationInfoRequest.getPageNo(),borderBaseStationInfoRequest.getPageSize());
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBorderBaseStationList(baseStationInfoRequest);
        List<BorderBaseStationInfoVo> list = new ArrayList<>();
        for(BaseStationInfoVo baseStationInfoVo: baseStationInfoVoList){
            BorderBaseStationInfoVo borderBaseStationInfoVo = new BorderBaseStationInfoVo();
            BeanUtils.copyProperties(baseStationInfoVo,borderBaseStationInfoVo);
            String publicNetworkPort = baseStationInfoVo.getPublicNetworkPort();
            if(publicNetworkPort.contains("{")){
                //数据转换
                PortModel portModel = JSONObject.parseObject(baseStationInfoVo.getPublicNetworkPort(), PortModel.class);
                //构建ip返回体
                PublicNetworkIp publicNetworkIp = new PublicNetworkIp();
                publicNetworkIp.setCommunicationIP(baseStationInfoVo.getPublicNetworkIp());
                publicNetworkIp.setEphemeralPort(portModel.getEphemeralPort());
                publicNetworkIp.setSignalingPort(portModel.getSignalingPort());
                publicNetworkIp.setTrunkPort(portModel.getTrunkPort());
                borderBaseStationInfoVo.setPublicNetworkIp(publicNetworkIp);
                borderBaseStationInfoVo.setPublicNetworkPort(null);
            }else {
                PublicNetworkIp publicNetworkIp = new PublicNetworkIp();
                publicNetworkIp.setCommunicationIP(baseStationInfoVo.getPublicNetworkIp());
                borderBaseStationInfoVo.setPublicNetworkIp(publicNetworkIp);
            }
            list.add(borderBaseStationInfoVo);
        }
        PageInfo<BorderBaseStationInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(list);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    /**
     * 校验插入端口ip
     * @param borderBaseStationInfoRequest
     * @param baseStationInfoVoList
     * @return
     */
    private boolean checkIpPort(BorderBaseStationInfoRequest borderBaseStationInfoRequest, List<BaseStationInfoVo> baseStationInfoVoList){
        //做唯一校验
//        List<String> portList = new ArrayList<>();
        List<String> insertPortList = new ArrayList<>();
//        for(BaseStationInfoVo baseStationInfoVo: baseStationInfoVoList){
//            //判断是不是边界基站
//            if(StationTypeEnum.BOUNDARY.getCode().equals(baseStationInfoVo.getStationType())){
//                PortModel portModel = JSONObject.parseObject(baseStationInfoVo.getPublicNetworkPort(), PortModel.class);
//                if(borderBaseStationInfoRequest.getPublicNetworkIp().getCommunicationIP().
//                        equals(baseStationInfoVo.getPublicNetworkIp())){
//                    return false;
//                }
//                //将所有数据库中公共的ip用一个list收集
//                portList.addAll(portModel.getEphemeralPort());
//                portList.addAll(portModel.getSignalingPort());
//                portList.addAll(portModel.getTrunkPort());
//                portList.add(baseStationInfoVo.getLanPort());
//            }else {
//                portList.add(baseStationInfoVo.getPublicNetworkPort());
//            }
//        }
        //将所有要插入的ip用一个list收集
        List<String> trunkPort = borderBaseStationInfoRequest.getPublicNetworkIp().getTrunkPort();
        if(!StringUtils.isEmpty(trunkPort.get(0))){
            insertPortList.addAll(borderBaseStationInfoRequest.getPublicNetworkIp().getTrunkPort());
        }
        List<String> signalingPort = borderBaseStationInfoRequest.getPublicNetworkIp().getSignalingPort();
        if(!StringUtils.isEmpty(signalingPort.get(0))){
            insertPortList.addAll(borderBaseStationInfoRequest.getPublicNetworkIp().getSignalingPort());
        }
        List<String> ephemeralPort = borderBaseStationInfoRequest.getPublicNetworkIp().getEphemeralPort();
        if(!StringUtils.isEmpty(ephemeralPort.get(0))){
            insertPortList.addAll(borderBaseStationInfoRequest.getPublicNetworkIp().getEphemeralPort());
        }
        //判断端口是否重复插入
        return checkInsertPort(insertPortList);
    }

    /**
     * 判断插入的端口有没有重复的数据
     * @param insertPortList
     * @return
     */
    private boolean checkInsertPort(List<String> insertPortList){
        Set<String> stringSet=new HashSet<>(insertPortList);
        if (insertPortList.size() == stringSet.size()) {
            return true;
        }
        return false;
    }

    /**
     * 判断端口是否重复
     * @param portList
     * @param insertPortList
     * @return
     */
//    private boolean checkPort(List<String> portList,List<String> insertPortList){
//        if(checkInsertPort(insertPortList)){
//            return true;
//        }
//        for(String insertPort: insertPortList){
//            for(String port: portList){
//                if(insertPort.equals(port)){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }


    /**
     * 构建大区
     * @param regionList
     * @param nmplCompanyInfos
     * @param company
     * @param nmplBaseStationInfos
     * @return
     */
    private RegionBelongVo getRegion(List<NmplCompanyInfo> regionList,List<NmplCompanyInfo> nmplCompanyInfos,NmplCompanyInfo company,
                                     List<NmplBaseStationInfo> nmplBaseStationInfos){
        List<CommunityBelongVo> communityBelongVoList = new ArrayList<>();
        RegionBelongVo regionBelongVo = new RegionBelongVo();
        for(NmplCompanyInfo region: regionList){
            //大区下的小区
            List<NmplCompanyInfo> communityList = new ArrayList<>();
            for(NmplCompanyInfo communityInfo: nmplCompanyInfos){
                if(region.getCompanyId().toString().
                        equals(communityInfo.getParentCode())){
                    communityList.add(communityInfo);
                }
            }
            //构建小区
            CommunityBelongVo communityBelongVo = constructCommunity(region, communityList, nmplBaseStationInfos);
            communityBelongVoList.add(communityBelongVo);
        }
        regionBelongVo.setChildren(communityBelongVoList);
        regionBelongVo.setName(company.getCompanyName());
        regionBelongVo.setRelationOperatorId(company.getCompanyId().toString());
        return regionBelongVo;
    }

    /**
     * 小区构建
     * @param region
     * @param communityList
     * @param nmplBaseStationInfos
     * @return
     */
    private CommunityBelongVo constructCommunity(NmplCompanyInfo region,List<NmplCompanyInfo> communityList,
                                                 List<NmplBaseStationInfo> nmplBaseStationInfos){
        CommunityBelongVo communityBelongVo = new CommunityBelongVo();
        List<BaseStationBelongVo> list = new ArrayList<>();
        for(NmplCompanyInfo community: communityList){
            //小区基站返回
            BaseStationBelongVo station = getStation(community, nmplBaseStationInfos);
            list.add(station);
        }
        communityBelongVo.setChildren(list);
        communityBelongVo.setName(region.getCompanyName());
        communityBelongVo.setRelationOperatorId(region.getCompanyId().toString());
        return communityBelongVo;
    }

    /**
     * 获取小区下的基站
     * @param community
     * @param nmplBaseStationInfos
     * @return
     */
    private BaseStationBelongVo getStation(NmplCompanyInfo community,List<NmplBaseStationInfo> nmplBaseStationInfos){
        BaseStationBelongVo baseStationBelongVo = new BaseStationBelongVo();
        List<CommunityBaseStationVo> baseStationInfoVoList = new ArrayList<>();
        for(NmplBaseStationInfo baseStationInfo: nmplBaseStationInfos){
            if(community.getCompanyId().toString().equals(baseStationInfo.getRelationOperatorId())){
                CommunityBaseStationVo communityBaseStationVo = new CommunityBaseStationVo();
                BeanUtils.copyProperties(baseStationInfo,communityBaseStationVo);
                baseStationInfoVoList.add(communityBaseStationVo);
            }
        }
        baseStationBelongVo.setChildren(baseStationInfoVoList);
        baseStationBelongVo.setName(community.getCompanyName());
        baseStationBelongVo.setRelationOperatorId(community.getCompanyId().toString());
        return baseStationBelongVo;
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