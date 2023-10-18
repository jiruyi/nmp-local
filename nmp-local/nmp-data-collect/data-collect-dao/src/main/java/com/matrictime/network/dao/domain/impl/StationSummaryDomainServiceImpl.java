package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplSystemHeartbeatExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.StationSummaryEnum;
import com.matrictime.network.modelVo.StationSummaryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<StationSummaryVo> selectSystemHeart() {
        List<NmplSystemHeartbeat> nmplSystemHeartbeats = systemHeartbeatExtMapper.selectSystemHeart();
        if(CollectionUtils.isEmpty(nmplSystemHeartbeats)){
            return null;
        }
        Set<String> stringSet = new HashSet<String>();
        for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
            String stationNetworkId = changeNetworkId(nmplSystemHeartbeats.get(0).getSourceId());
            //切割小区唯一标识符
            String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
            nmplSystemHeartbeat.setSourceId(networkIdString);
            stringSet.add(networkIdString);

        }
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String networkIdString: stringSet){
           int i = 0;
           for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
               if(networkIdString.equals(nmplSystemHeartbeat.getSourceId())){
                   i++;
               }
           }
           StationSummaryVo stationSummaryVo = new StationSummaryVo();
           stationSummaryVo.setCompanyNetworkId(networkIdString);
           stationSummaryVo.setSumType(StationSummaryEnum.TOTAL_NET_WORKS.getCode());
           stationSummaryVo.setSumNumber(String.valueOf(i));
           summaryVos.add(stationSummaryVo);
       }
       return summaryVos;
    }

    /**
     * 查询基站
     * @return
     */
    @Override
    public List<StationSummaryVo> selectStation() {
        //基站查询
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andStationTypeEqualTo(StationSummaryEnum.BASE_STATION.getCode());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            return null;
        }
        baseStationInfos.stream().forEach(item -> NetworkIdUtil.splitNetworkId(item.getStationNetworkId()));
        Map<String, List<NmplBaseStationInfo>> maps = baseStationInfos.stream().collect(
                Collectors.groupingBy(NmplBaseStationInfo::getStationNetworkId));
        //切割小区唯一标识符
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String netId :maps.keySet()){
            String networkIdString = NetworkIdUtil.splitNetworkId(netId);
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.BASE_STATION.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(maps.get(netId).size()));
            summaryVos.add(stationSummaryVo);
        }
        return summaryVos;
    }

    /**
     * 查询密钥中心
     * @return
     */
    @Override
    public List<StationSummaryVo> selectDevice() {
        //设备查询
        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        NmplDeviceInfoExample.Criteria criteria = deviceInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andDeviceTypeEqualTo(StationSummaryEnum.KET_CENTER.getCode());
        List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
        if(CollectionUtils.isEmpty(nmplDeviceInfoList)){
            return null;
        }
        nmplDeviceInfoList.stream().forEach(item -> NetworkIdUtil.splitNetworkId(item.getStationNetworkId()));
        Map<String, List<NmplDeviceInfo>> maps = nmplDeviceInfoList.stream().collect(
                Collectors.groupingBy(NmplDeviceInfo::getStationNetworkId));
        //切割小区唯一标识符
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String netId :maps.keySet()){
            String networkIdString = NetworkIdUtil.splitNetworkId(netId);
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.KET_CENTER.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(maps.get(netId).size()));
            summaryVos.add(stationSummaryVo);
        }
        return summaryVos;
    }

    /**
     * 查询小区边界基站
     * @return
     */
    @Override
    public List<StationSummaryVo> selectBorderStation() {
        //边界基站查询
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        criteria.andStationTypeEqualTo(StationSummaryEnum.BORDER_BASE_STATION.getCode());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            return null;
        }
        baseStationInfos.stream().forEach(item -> NetworkIdUtil.splitNetworkId(item.getStationNetworkId()));
        Map<String, List<NmplBaseStationInfo>> maps = baseStationInfos.stream().collect(
                Collectors.groupingBy(NmplBaseStationInfo::getStationNetworkId));
        //切割小区唯一标识符
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String netId :maps.keySet()){
            String networkIdString = NetworkIdUtil.splitNetworkId(netId);
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.BORDER_BASE_STATION.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(maps.get(netId).size()));
            summaryVos.add(stationSummaryVo);
        }
        return summaryVos;
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
        return networkStr.substring(0,networkStr.length() - 3);
    }
}
