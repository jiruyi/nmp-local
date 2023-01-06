package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplPcDataExtMapper;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.INTERNET_BROADBAND_LOAD_CODE;
import static com.matrictime.network.base.constant.DataConstants.INTRANET_BROADBAND_LOAD_CODE;

@Service
@Slf4j
public class DataCollectDomainServiceImpl implements DataCollectDomainService {
    @Resource
    NmplDataCollectExtMapper nmplDataCollectExtMapper;
    @Resource
    NmplDataCollectMapper nmplDataCollectMapper;
    @Resource
    NmplDeviceInfoMapper nmplDeviceInfoMapper;
    @Resource
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;
    @Resource
    NmplDataCollectForLoadMapper nmplDataCollectForLoadMapper;

    @Resource
    private NmplPcDataExtMapper nmplPcDataExtMapper;





    @Override
    public PageInfo<DataCollectVo> queryByConditions(DataCollectReq dataCollectReq) {
        Page page = null;
        List<DataCollectVo> dataCollectVos = new ArrayList<>();
        if(dataCollectReq.getDeviceType().equals(StationTypeEnum.BASE.getCode())){
            page = PageHelper.startPage(dataCollectReq.getPageNo(),dataCollectReq.getPageSize());
            dataCollectVos = nmplDataCollectExtMapper.stationLinkQuery(dataCollectReq);
        }else {
            page = PageHelper.startPage(dataCollectReq.getPageNo(),dataCollectReq.getPageSize());
            dataCollectVos = nmplDataCollectExtMapper.linkQueryByCondition(dataCollectReq);
        }
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
            if (INTRANET_BROADBAND_LOAD_CODE.equals(dataCollect.getDataItemCode()) || INTERNET_BROADBAND_LOAD_CODE.equals(dataCollect.getDataItemCode())){
                NmplDataCollectForLoad nmplDataCollectForLoad = new NmplDataCollectForLoad();
                BeanUtils.copyProperties(dataCollectReq,nmplDataCollectForLoad);
                nmplDataCollectForLoadMapper.insertSelective(nmplDataCollectForLoad);
            }
            return nmplDataCollectMapper.insertSelective(dataCollect);
        }else {
            if (!CollectionUtils.isEmpty(dataCollectReq.getDataCollectVoLoadList())){
                nmplDataCollectExtMapper.batchInsertLoad(dataCollectReq.getDataCollectVoLoadList());
            }
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
        nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyCode()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            ids.add(nmplBaseStationInfo.getStationId());
        }

        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyCode()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {

            ids.add(nmplDeviceInfo.getDeviceId());
        }
        if(CollectionUtils.isEmpty(ids)){
            log.info("该区域下无设备");
            return new ArrayList<>();
        }
        NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
        nmplDataCollectExample.createCriteria().andDeviceIdIn(ids).andUploadTimeGreaterThan(monitorReq.getCurrentTime());
        return nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
    }

    /**
     * 统计小区下在线人数
     * @param monitorReq
     * @return
     */
    @Override
    public Integer countDeviceNumber(MonitorReq monitorReq) {
        return nmplPcDataExtMapper.countDeviceNumber(monitorReq);
    }

    /**
     * 统计带宽总量
     * @param monitorReq
     * @return
     */
    @Override
    public Double sumDataItemValue(MonitorReq monitorReq) {

        return nmplPcDataExtMapper.sumDataItemValue(monitorReq);
    }


    @Override
    public List<DataCollectVo> queryTopTen(MonitorReq monitorReq) {
        List<String> ids = new ArrayList<>();
        Map<String,String> deviceInfo = new HashMap<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyCode()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            ids.add(nmplBaseStationInfo.getStationId());
            deviceInfo.put(nmplBaseStationInfo.getStationId(),nmplBaseStationInfo.getStationName());
        }
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(monitorReq.getCompanyCode()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            ids.add(nmplDeviceInfo.getDeviceId());
            deviceInfo.put(nmplDeviceInfo.getDeviceId(),nmplDeviceInfo.getDeviceName());
        }
        if(CollectionUtils.isEmpty(ids)){
            log.info("该区域下无设备");
            return new ArrayList<>();
        }

        List<DataCollectVo> resultList;
        if(DataCollectEnum.USED_KEY.getCode().equals(monitorReq.getDataItemCode())){
            resultList = nmplDataCollectExtMapper.selectTopTenDesc(ids,monitorReq.getDataItemCode(),monitorReq.getCurrentTime());
        }else {
            resultList = nmplDataCollectExtMapper.selectTopTenAsc(ids,monitorReq.getDataItemCode(),monitorReq.getCurrentTime());
        }
        for (DataCollectVo vo : resultList){
            vo.setUnit(DataConstants.DATA_COLLECT_CONST.get(monitorReq.getDataItemCode()));
            vo.setDeviceName(deviceInfo.get(vo.getDeviceId()));
        }
        return resultList;
    }
}
