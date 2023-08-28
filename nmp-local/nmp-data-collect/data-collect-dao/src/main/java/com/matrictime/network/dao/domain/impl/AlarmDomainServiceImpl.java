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
        Long startAlarmId = 0l;
        Long endAlarmId = startAlarmId+DataConstants.ALARM_INFO_EVERY_COUNT;
        //1.0 查询上次推送到的位置
        NmplDataPushRecordExample pushRecordExample = new NmplDataPushRecordExample();
        pushRecordExample.createCriteria().andTableNameEqualTo(DataConstants.NMPL_ALARM_INFO);
        pushRecordExample.setOrderByClause("id desc");
        List<NmplDataPushRecord> dataPushRecords = dataPushRecordMapper.selectByExample(pushRecordExample);
        //2.0 配置最新的起止id
        if(!CollectionUtils.isEmpty(dataPushRecords)){
            Long lastAlramId = dataPushRecords.get(0).getDataId();
            startAlarmId= lastAlramId;
            endAlarmId = endAlarmId +startAlarmId;
        }
        //3.0 根据起止id 查询告警数据
        NmplAlarmInfoExample example = new NmplAlarmInfoExample();
        example.createCriteria().andAlarmIdGreaterThan(startAlarmId).andAlarmIdLessThanOrEqualTo(endAlarmId);
        List<NmplAlarmInfo> infoList =  alarmInfoMapper.selectByExample(example);
        return infoList;
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
