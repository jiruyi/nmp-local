package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.NmplLoginDetailMapper;
import com.matrictime.network.dao.mapper.NmplOperateLogMapper;
import com.matrictime.network.dao.model.NmplAlarmInfoExample;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.dao.model.extend.NmplAlarmInfoExt;
import com.matrictime.network.modelVo.AlarmInfo;
import com.matrictime.network.modelVo.LoginDetail;
import com.matrictime.network.request.AlarmInfoRequest;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 17:12
 * @desc
 */
@Slf4j
@Service
public class LogDomainServiceImpl extends SystemBaseService implements LogDomainService {

    @Autowired
    private NmplOperateLogMapper operateLogMapper;

    @Autowired
    private NmplLoginDetailMapper loginDetailMapper;

    @Autowired
    private NmplAlarmInfoMapper alarmInfoMapper;


    @Override
    public int saveLog(NmplOperateLog operateLog) {
        if(ObjectUtils.isEmpty(operateLog)){
            return NumberUtils.INTEGER_ZERO;
        }
        return  operateLogMapper.insertSelective(operateLog);
    }

    /**
     * @title queryLogList
     * @param [logRequest]
     * @return com.matrictime.network.response.PageInfo<com.matrictime.network.dao.model.NmplOperateLog>
     * @description
     * @author jiruyi
     * @create 2022/3/4 0004 11:22
     */
    @Override
    public PageInfo<NmplOperateLog> queryLogList(LogRequest logRequest) {
        Page page = PageHelper.startPage(logRequest.getPageNo(),logRequest.getPageSize());
        List<NmplOperateLog> list = operateLogMapper.queryLogList(logRequest);
        PageInfo<NmplOperateLog> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }

    /**
     * @title queryLoinDetailList
     * @param [loginRequest]
     * @return com.matrictime.network.response.PageInfo<com.matrictime.network.dao.model.NmplLoginDetail>
     * @description 查询登录明细日志
     * @author jiruyi
     * @create 2022/3/4 0004 11:22
     */
    @Override
    public PageInfo<NmplLoginDetail> queryLoginDetailList(LoginDetail loginDetail) {
        Page page = PageHelper.startPage(loginDetail.getPageNo(),loginDetail.getPageSize());
        List<NmplLoginDetail> list = loginDetailMapper.queryLoginDetailList(loginDetail);
        PageInfo<NmplLoginDetail> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }

    /**
     * @title queryAlarmInfoList
     * @param [alarmAreaCode]
     * @return com.matrictime.network.response.PageInfo
     * @description
     * @author jiruyi
     * @create 2023/8/17 0017 16:52
     */
    @Override
    public PageInfo queryAlarmInfoList(AlarmInfoRequest alarmInfoRequest) {
        Page page = PageHelper.startPage(alarmInfoRequest.getPageNo(),alarmInfoRequest.getPageSize());
        NmplAlarmInfoExample alarmInfoExample = new NmplAlarmInfoExample();
        if(!ObjectUtils.isEmpty(alarmInfoRequest.getAlarmAreaCode())){
            alarmInfoExample.createCriteria().andAlarmAreaCodeEqualTo(alarmInfoRequest.getAlarmAreaCode());
        }
        alarmInfoExample.setOrderByClause("alarm_upload_time desc");
        //查询告警信息和小区名
        List<NmplAlarmInfoExt> list = alarmInfoMapper.selectListFromAlarmAndCompany(alarmInfoExample);
        PageInfo<NmplAlarmInfoExt> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }

    /**
     * @title batchInsertAlarmData
     * @param [alarmInfoList]
     * @return int
     * @description
     * @author jiruyi
     * @create 2023/8/29 0029 14:56
     */
    @Override
    public int batchInsertAlarmData(List<AlarmInfo> alarmInfoList) {
        return  alarmInfoMapper.batchInsert(alarmInfoList);
    }


}
