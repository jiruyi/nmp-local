package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplBaseStationMapper;
import com.matrictime.network.dao.mapper.NmplDeviceExtraInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


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

    @Override
    public int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        InsertCheckUnique(baseStationInfoRequest);
        NmplBaseStation nmplbaseStation = new NmplBaseStation();
        BeanUtils.copyProperties(baseStationInfoRequest,nmplbaseStation);
//        return nmplBaseStationInfoMapper.insertBaseStationInfo(baseStationInfoRequest);
        return nmplbaseStationMapper.insertSelective(nmplbaseStation);
    }

    @Override
    public int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        //查询bid是否重复
        UpdateCheckUnique(baseStationInfoRequest);
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
    public void InsertCheckUnique(BaseStationInfoRequest baseStationInfoRequest) {
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
    public void UpdateCheckUnique(BaseStationInfoRequest baseStationInfoRequest) {
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
}