package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.*;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;

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







    @Override
    public PageInfo<DataCollectVo> queryByConditions(DataCollectReq dataCollectReq) {
        Page page = null;
        List<DataCollectVo> dataCollectVos = new ArrayList<>();
        //性能考虑 如果不传时间范围 默认查最近一个月的时间
        if(dataCollectReq.getStartTime()==null&&dataCollectReq.getEndTime()==null){
            dataCollectReq.setStartTime(LocalDateTime.now().minusDays(90).toString());
            dataCollectReq.setEndTime(LocalDateTime.now().toString());
        }
        if(dataCollectReq.getDeviceType().equals(StationTypeEnum.BASE.getCode())){
            dataCollectReq.setAccessType(StationTypeEnum.INSIDE.getCode());
            dataCollectReq.setBorderType(StationTypeEnum.BOUNDARY.getCode());
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

//    /**
//     * 统计小区下在线人数
//     * @param monitorReq
//     * @return
//     */
//    @Override
//    public Integer countDeviceNumber(MonitorReq monitorReq) {
//        return nmplPcDataExtMapper.countDeviceNumber(monitorReq);
//    }

//    /**
//     * 统计带宽总量
//     * @param monitorReq
//     * @return
//     */
//    @Override
//    public Double sumDataItemValue(MonitorReq monitorReq) {
//
//        return nmplPcDataExtMapper.sumDataItemValue(monitorReq);
//    }


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

        List<DataCollectVo> resultList=  new ArrayList<>();
        if(DataCollectEnum.USED_KEY.getCode().equals(monitorReq.getDataItemCode())){
            NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
            nmplDataCollectExample.createCriteria().andDeviceIdIn(ids).andDataItemCodeEqualTo(monitorReq.getDataItemCode());
            List<NmplDataCollect> nmplDataCollectList = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
            Map<String,Double> map = new HashMap<>();
            for (NmplDataCollect nmplDataCollect : nmplDataCollectList) {
                if(map.get(nmplDataCollect.getDeviceId())==null){
                    map.put(nmplDataCollect.getDeviceId(), Double.valueOf(nmplDataCollect.getDataItemValue()));
                }else {
                    double value = map.get(nmplDataCollect.getDeviceId())+Double.valueOf(nmplDataCollect.getDataItemValue());
                    map.put(nmplDataCollect.getDeviceId(),value);
                }
            }
            List<Map.Entry<String,Double>> lstEntry=new ArrayList<>(map.entrySet());
            Collections.sort(lstEntry,((o1, o2) -> {
                return o2.getValue().compareTo(o1.getValue());
            }));
            int num = lstEntry.size()<TEN?lstEntry.size():TEN;
            for (int i=0;i<num;i++){
                DataCollectVo dataCollectVo = new DataCollectVo();
                BeanUtils.copyProperties(nmplDataCollectList.get(0),dataCollectVo);
                dataCollectVo.setDataItemValue(String.valueOf(lstEntry.get(i).getValue()));
                dataCollectVo.setDeviceId(lstEntry.get(i).getKey());
                resultList.add(dataCollectVo);
            }
        }else {
            resultList = nmplDataCollectExtMapper.selectTopTenAsc(ids,monitorReq.getDataItemCode(),monitorReq.getCurrentTime());
        }

        for (DataCollectVo vo : resultList){
            BigDecimal bigDecimal = new BigDecimal(vo.getDataItemValue());
            double value = 0.0;
            if(bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER),2,BigDecimal.ROUND_HALF_UP).doubleValue()<MAX_SIZE){
                value = bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                vo.setUnit("MB");
            }else if(bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*BASE_NUMBER),2,BigDecimal.ROUND_HALF_UP).doubleValue()<MAX_SIZE){
                value = bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*BASE_NUMBER),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                vo.setUnit("GB");
            }else {
                value = bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*BASE_NUMBER*BASE_NUMBER),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                vo.setUnit("TB");
            }
            vo.setDeviceName(deviceInfo.get(vo.getDeviceId()));
            vo.setDataItemValue(String.valueOf(value));
        }
        return resultList;
    }
}
