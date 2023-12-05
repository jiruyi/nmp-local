package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplBaseStationInfoExtMapper;
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
    private NmplBaseStationInfoExtMapper baseStationInfoExtMapper;

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
        List<NmplSystemHeartbeat> heartbeatList = new ArrayList<>();
        //sourceId、targetId 数据转换
        for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
            String sourceNetworkId = nmplSystemHeartbeat.getSourceId();
            String targetNetworkId = nmplSystemHeartbeat.getTargetId();
//            List<NmplBaseStationInfo> sourceStation = getStation(sourceNetworkId);
//            List<NmplBaseStationInfo> targetStation = getStation(targetNetworkId);
//            List<NmplDeviceInfo> sourceDevice = getDevice(sourceNetworkId);
//            List<NmplDeviceInfo> targetDevice = getDevice(targetNetworkId);
            List<NmplBaseStationInfo> source = baseStationInfoExtMapper.selectAllDevice(sourceNetworkId);
            List<NmplBaseStationInfo> target = baseStationInfoExtMapper.selectAllDevice(targetNetworkId);
//            if((!CollectionUtils.isEmpty(sourceStation) || !CollectionUtils.isEmpty(sourceDevice)) &&
//                    (!CollectionUtils.isEmpty(targetStation) || !CollectionUtils.isEmpty(targetDevice))){
//                heartbeatList.add(nmplSystemHeartbeat);
//            }
            if(!CollectionUtils.isEmpty(source) && !CollectionUtils.isEmpty(target)){
                heartbeatList.add(nmplSystemHeartbeat);
            }

        }

        Set<String> stringSet = new HashSet<String>();
        for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
            //切割小区唯一标识符
            String s = nmplSystemHeartbeat.getSourceId();
            nmplSystemHeartbeat.setSourceId(s);
            stringSet.add(s);

        }
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String networkIdString: stringSet){
           int i = 0;
           for(NmplSystemHeartbeat nmplSystemHeartbeat: heartbeatList){
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
        Set<String> stringSet = new HashSet<String>();
        for(NmplBaseStationInfo nmplBaseStationInfo: baseStationInfos){
            //切割小区唯一标识符
            String networkIdString = NetworkIdUtil.splitNetworkId(nmplBaseStationInfo.getStationNetworkId());
            stringSet.add(networkIdString);
        }
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String networkIdString: stringSet){
            int i = 0;
            for(NmplBaseStationInfo nmplBaseStationInfo: baseStationInfos){
                if(networkIdString.equals(NetworkIdUtil.splitNetworkId(nmplBaseStationInfo.getStationNetworkId()))){
                    i++;
                }
            }
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.BASE_STATION.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(i));
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
        Set<String> stringSet = new HashSet<String>();
        for(NmplDeviceInfo nmplDeviceInfo: nmplDeviceInfoList){
            //切割小区唯一标识符
            String networkIdString = NetworkIdUtil.splitNetworkId(nmplDeviceInfo.getStationNetworkId());
            stringSet.add(networkIdString);
        }
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String networkIdString: stringSet){
            int i = 0;
            for(NmplDeviceInfo nmplDeviceInfo: nmplDeviceInfoList){
                if(networkIdString.equals(NetworkIdUtil.splitNetworkId(nmplDeviceInfo.getStationNetworkId()))){
                    i++;
                }
            }
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.KET_CENTER.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(i));
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

        Set<String> stringSet = new HashSet<String>();
        for(NmplBaseStationInfo nmplBaseStationInfo: baseStationInfos){
            //切割小区唯一标识符
            String networkIdString = NetworkIdUtil.splitNetworkId(nmplBaseStationInfo.getStationNetworkId());
            stringSet.add(networkIdString);
        }
        List<StationSummaryVo> summaryVos = new ArrayList<>();
        for(String networkIdString: stringSet){
            int i = 0;
            for(NmplBaseStationInfo nmplBaseStationInfo: baseStationInfos){
                if(networkIdString.equals(NetworkIdUtil.splitNetworkId(nmplBaseStationInfo.getStationNetworkId()))){
                    i++;
                }
            }
            StationSummaryVo stationSummaryVo = new StationSummaryVo();
            stationSummaryVo.setCompanyNetworkId(networkIdString);
            stationSummaryVo.setSumType(StationSummaryEnum.BORDER_BASE_STATION.getCode());
            stationSummaryVo.setSumNumber(String.valueOf(i));
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
     * 过滤基站
     * @param networkId
     * @return
     */
    private List<NmplBaseStationInfo> getStation(String networkId){
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andStationNetworkIdEqualTo(networkId);
        criteria.andIsExistEqualTo(true);
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        return baseStationInfos;
    }

    /**
     * 过滤设备
     * @param networkId
     * @return
     */
    private List<NmplDeviceInfo> getDevice(String networkId){
        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        NmplDeviceInfoExample.Criteria criteria = deviceInfoExample.createCriteria();
        criteria.andStationNetworkIdEqualTo(networkId);
        criteria.andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
        return nmplDeviceInfoList;
    }

    /**
     * 入网id1 16转10 进制
     * @param networkId
     * @return
     */
//    private String changeNetworkId(String networkId){
//        String[] split = networkId.split("-");
//        String networkIdString = "";
//        for(int i = 0;i < 4;i++){
//            Integer change = Integer.parseInt(split[i],16);
//            networkIdString = networkIdString + change + "-";
//        }
//        return networkIdString.substring(0,networkIdString.length() - 1);
//    }

    /**
     * 将要networkId 转换
     * @param networkId
     * @return
     */
//    private String getNetworkId(String networkId){
//        String[] split = networkId.split("-");
//        String networkIdString = "";
//        for(int i = 0;i < 5;i++){
//            Integer change = Integer.parseInt(split[i],16);
//            networkIdString = networkIdString + change + "-";
//        }
//        return networkIdString.substring(0,networkIdString.length() - 1);
//    }
}
