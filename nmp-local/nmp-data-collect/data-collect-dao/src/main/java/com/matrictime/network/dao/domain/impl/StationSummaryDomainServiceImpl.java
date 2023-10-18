package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplSystemHeartbeatExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.StationSummaryEnum;
import com.matrictime.network.modelVo.StationSummaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class StationSummaryDomainServiceImpl implements StationSummaryDomainService {
    @Resource
    private NmplSystemHeartbeatExtMapper systemHeartbeatExtMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Resource
    private NmplDataPushRecordMapper dataPushRecordMapper;

    /**
     * 查询总网络数
     * @return
     */
    @Override
    public StationSummaryVo selectSystemHeart() {
        List<NmplSystemHeartbeat> nmplSystemHeartbeats = systemHeartbeatExtMapper.selectSystemHeart();
        if(CollectionUtils.isEmpty(nmplSystemHeartbeats)){
            return null;
        }
        String stationNetworkId = changeNetworkId(nmplSystemHeartbeats.get(0).getSourceId());
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        StationSummaryVo stationSummaryVo = new StationSummaryVo();
        stationSummaryVo.setSumNumber(String.valueOf(nmplSystemHeartbeats.size()));
        stationSummaryVo.setSumType(StationSummaryEnum.TOTAL_NET_WORKS.getCode());
        stationSummaryVo.setUploadTime(nmplSystemHeartbeats.get(0).getUploadTime());
        stationSummaryVo.setCompanyNetworkId(networkIdString);
        return stationSummaryVo;
    }

    /**
     * 查询基站
     * @return
     */
    @Override
    public StationSummaryVo selectStation() {
        //基站查询
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andStationTypeEqualTo(StationSummaryEnum.BASE_STATION.getCode());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            return null;
        }
        String stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        StationSummaryVo stationSummaryVo = new StationSummaryVo();
        stationSummaryVo.setCompanyNetworkId(networkIdString);
        stationSummaryVo.setSumType(StationSummaryEnum.BASE_STATION.getCode());
        stationSummaryVo.setSumNumber(String.valueOf(baseStationInfos.size()));
        return stationSummaryVo;
    }

    /**
     * 查询密钥中心
     * @return
     */
    @Override
    public StationSummaryVo selectDevice() {
        //设备查询
        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        NmplDeviceInfoExample.Criteria criteria = deviceInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andDeviceTypeEqualTo(StationSummaryEnum.KET_CENTER.getCode());
        List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
        if(CollectionUtils.isEmpty(nmplDeviceInfoList)){
            return null;
        }
        String stationNetworkId = nmplDeviceInfoList.get(0).getStationNetworkId();
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        StationSummaryVo stationSummaryVo = new StationSummaryVo();
        stationSummaryVo.setCompanyNetworkId(networkIdString);
        stationSummaryVo.setSumType(StationSummaryEnum.KET_CENTER.getCode());
        stationSummaryVo.setSumNumber(String.valueOf(nmplDeviceInfoList.size()));
        return stationSummaryVo;
    }

    /**
     * 查询小区边界基站
     * @return
     */
    @Override
    public StationSummaryVo selectBorderStation() {
        //边界基站查询
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andStationTypeEqualTo(StationSummaryEnum.BORDER_BASE_STATION.getCode());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            return null;
        }
        String stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        StationSummaryVo stationSummaryVo = new StationSummaryVo();
        stationSummaryVo.setCompanyNetworkId(networkIdString);
        stationSummaryVo.setSumType(StationSummaryEnum.BORDER_BASE_STATION.getCode());
        stationSummaryVo.setSumNumber(String.valueOf(baseStationInfos.size()));
        return stationSummaryVo;
    }

    /**
     * 插入记录表
     * @param maxId
     * @param businessDataEnum
     * @return
     */
    @Override
    public int insertDataPushRecord(Long maxId, String businessDataEnum) {
        NmplDataPushRecord record = new NmplDataPushRecord();
        record.setDataId(maxId);
        record.setTableName(businessDataEnum);
        return  dataPushRecordMapper.insertSelective(record);
    }

    /**
     * 获取小区唯一标识符
     * @param nmplSystemHeartbeat
     * @return
     */
    public String getStationNetworkId(NmplSystemHeartbeat nmplSystemHeartbeat){
        String stationNetworkId = "";
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andStationIdEqualTo(nmplSystemHeartbeat.getSourceId());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(!CollectionUtils.isEmpty(baseStationInfos)){
            stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
        }else {
            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
            NmplDeviceInfoExample.Criteria criteria1 = deviceInfoExample.createCriteria();
            criteria1.andDeviceIdEqualTo((nmplSystemHeartbeat.getSourceId()));
            List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
            stationNetworkId = nmplDeviceInfoList.get(0).getStationNetworkId();
        }
        return stationNetworkId;
    }

    /**
     * 入网id1 16转10 进制
     * @param networkId
     * @return
     */
    private String changeNetworkId(String networkId){
        String[] split = networkId.split("-");
        String networkStr = "";
        for(int i = 0; i <= split.length -1;i++){
            Integer change = Integer.parseInt(split[i],16);
            networkStr = networkStr + change + "-";
        }
        return networkStr.substring(0,networkStr.length() - 1);
    }
}
