package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class DataCollectDomainServiceImpl implements DataCollectDomainService {
    @Autowired
    NmplDataCollectExtMapper nmplDataCollectExtMapper;
    @Autowired
    NmplDataCollectMapper nmplDataCollectMapper;
    @Autowired
    NmplDeviceInfoMapper nmplDeviceInfoMapper;
    @Autowired
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;


    @Override
    public PageInfo<DataCollectVo> queryByConditions(DataCollectReq dataCollectReq) {
        Page page = PageHelper.startPage(dataCollectReq.getPageNo(),dataCollectReq.getPageSize());
        List<DataCollectVo> dataCollectVos = nmplDataCollectExtMapper.linkQueryByCondition(dataCollectReq);
        PageInfo<DataCollectVo> pageResult =  new PageInfo<>();
        pageResult.setList(dataCollectVos);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

    @Override
    public Integer save(DataCollectReq dataCollectReq) {
        if (CollectionUtils.isEmpty(dataCollectReq.getDataCollectVoList())){
            NmplDataCollect dataCollect = new NmplDataCollect();
            BeanUtils.copyProperties(dataCollectReq,dataCollect);
            return nmplDataCollectMapper.insert(dataCollect);
        }else {
            return nmplDataCollectExtMapper.batchInsert(dataCollectReq.getDataCollectVoList());
        }
    }


    /**
     * 查询某个时间段之前某个小区内所有设备的数据
     * @param monitorReq
     * @return
     * @throws Exception
     */
    @Override
    public List<NmplDataCollect> queryMonitorData(MonitorReq monitorReq) throws Exception {
        List<String> ids = new ArrayList<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyId());
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            ids.add(nmplBaseStationInfo.getStationId());
        }

        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyId());
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            ids.add(nmplDeviceInfo.getDeviceId());
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sf.parse(monitorReq.getCurrentTime());

        NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
        nmplDataCollectExample.createCriteria().andDeviceIdIn(ids).andCreateTimeGreaterThan(date);
        return nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
    }


    @Override
    public List<DataCollectVo> queryTopTen(MonitorReq monitorReq) {
        List<String> ids = new ArrayList<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            ids.add(nmplBaseStationInfo.getStationId());
        }
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            ids.add(nmplDeviceInfo.getDeviceId());
        }
        return nmplDataCollectExtMapper.selectTopTen(ids,monitorReq.getDataItemCode(),monitorReq.getCurrentTime());
    }
}
