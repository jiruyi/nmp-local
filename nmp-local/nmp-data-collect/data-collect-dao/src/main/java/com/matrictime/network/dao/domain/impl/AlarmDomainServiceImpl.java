package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.dao.model.NmplAlarmInfoExample;
import com.matrictime.network.dao.model.NmplDataPushRecord;
import com.matrictime.network.dao.model.NmplDataPushRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:18
 * @desc 告警信息数据库表
 */
@Service
public class AlarmDomainServiceImpl implements AlarmDomainService {

    @Autowired
    private NmplAlarmInfoMapper alarmInfoMapper;

    @Autowired
    private NmplDataPushRecordMapper dataPushRecordMapper;



    /**
      * @title queryAlarmList
      * @param []
      * @return java.util.List<com.matrictime.network.dao.model.NmplAlarmInfo>
      * @description  查询告警表信息
      * @author jiruyi
      * @create 2023/4/19 0019 11:19
      */
    @Override
    public List<NmplAlarmInfo> queryAlarmList() {
        //起止id
        Long startAlarmId =alarmInfoMapper.selectMinAlarmId();
        //1.0 查询上次推送到的位置
        NmplDataPushRecordExample pushRecordExample = new NmplDataPushRecordExample();
        pushRecordExample.createCriteria().andTableNameEqualTo(DataConstants.NMPL_ALARM_INFO);
        pushRecordExample.setOrderByClause("id desc");
        List<NmplDataPushRecord> dataPushRecords = dataPushRecordMapper.selectByExample(pushRecordExample);
        //2.0 配置最新的起止id
        if(!CollectionUtils.isEmpty(dataPushRecords)){
            Long lastAlramId = dataPushRecords.get(0).getDataId();
            startAlarmId= lastAlramId;
        }
        //3.0 根据起止id 查询告警数据
        NmplAlarmInfoExample example = new NmplAlarmInfoExample();
        example.createCriteria().andAlarmIdGreaterThan(startAlarmId);
        List<NmplAlarmInfo> infoList =  alarmInfoMapper.selectByExample(example);
        //5.0 设置小区入网码
        setAreaCode(infoList);
        return infoList;
    }

    /**
      * @title setAreaCode
      * @param [nmplAlarmInfos]
      * @return void
      * @description 
      * @author jiruyi
      * @create 2023/9/7 0007 15:48
      */
    public void setAreaCode(List<NmplAlarmInfo> nmplAlarmInfos){
        if(CollectionUtils.isEmpty(nmplAlarmInfos)){
            return;
        }
        //收集ip
        List<String> ips = nmplAlarmInfos.stream().map(NmplAlarmInfo::getAlarmSourceIp).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(ips)){
            return;
        }
        //ip对应小区id
        List<Map<String,String>>  maps =  alarmInfoMapper.selectAreaNetworkIdByIps(ips);
        //对入网码去除设备编号
        Map<String,String> ipKeyMap =  maps.stream().collect(Collectors.toMap(entry -> entry.get("lanIp"),entry -> {
                    String netWorkId =  entry.get("networkId");
                    netWorkId = netWorkId.substring(0, netWorkId.lastIndexOf("-"));
                    return  netWorkId;},(v1,v2) ->v2));
        //放入
        nmplAlarmInfos.stream().forEach(nmplAlarmInfo -> {
            nmplAlarmInfo.setAlarmAreaCode(ipKeyMap.get(nmplAlarmInfo.getAlarmSourceIp()));
        });
    }

    /**
      * @title insertDataPushRecord
      * @param [maxAlarmId]
      * @return int
      * @description
      * @author jiruyi
      * @create 2023/8/28 0028 15:12
      */
    @Override
    public int insertDataPushRecord(Long maxAlarmId) {
        NmplDataPushRecord record = new NmplDataPushRecord();
        record.setDataId(maxAlarmId);
        record.setTableName(DataConstants.NMPL_ALARM_INFO);
        return  dataPushRecordMapper.insertSelective(record);
    }


}
