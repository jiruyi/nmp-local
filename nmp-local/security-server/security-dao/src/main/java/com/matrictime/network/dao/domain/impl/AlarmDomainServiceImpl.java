package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.mapper.extend.NmplAlarmInfoExtMapper;
import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.AlarmDataListReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:18
 * @desc 告警信息数据库表
 */
@Slf4j
@Service
public class AlarmDomainServiceImpl implements AlarmDomainService {

    @Autowired
    private NmplAlarmInfoExtMapper alarmInfoExtMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
      * @title queryAlarmList
      * @param []
      * @return java.util.List<com.matrictime.network.dao.model.NmplAlarmInfo>
      * @description  查询告警表信息
      * @author jiruyi
      * @create 2023/4/19 0019 11:19
      */
    @Override
    public PageInfo<AlarmAndServerInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq) {
        //分页
        Page<AlarmAndServerInfo> page = PageHelper.startPage(alarmDataListReq.getPageNo(), alarmDataListReq.getPageSize());
        List<AlarmAndServerInfo> alarmAndServerInfos = alarmInfoExtMapper.queryAlarmDataList(alarmDataListReq);
        log.info("AlarmDataDomainService  queryAlarmDataList page:{},nmplAlarmInfos：{}", page, alarmAndServerInfos);
        return new PageInfo<>((int) page.getTotal(), page.getPages(), alarmAndServerInfos);
    }

    /**
      * @title acceptAlarmData
      * @param [alarmInfoList, cpuId]
      * @return int
      * @description
      * @author jiruyi
      * @create 2023/11/13 0013 11:08
      */
    @Override
    public int acceptAlarmData(List<NmpsAlarmInfo> alarmInfoList, String redisKey) {
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return NumberUtils.INTEGER_ZERO;
        }
        //mysql 插入
        int batchCount = alarmInfoExtMapper.batchInsert(alarmInfoList);
        /**ip*/
        Long maxId = alarmInfoList.stream().max(Comparator.comparingLong(NmpsAlarmInfo::getAlarmId)).get().getAlarmId();
        log.info("this time acceptAlarmData cpuId:{},maxId:{}",redisKey,maxId);
        redisTemplate.opsForValue().set(redisKey,String.valueOf(maxId));
        return batchCount;
    }


}
