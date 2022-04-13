package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.MonitorResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.util.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.concurrent.Future;

@Service
@Slf4j
public class DataCollectServiceImpl extends SystemBaseService implements DataCollectService {
    @Autowired
    DataCollectDomainService dataCollectDomainService;


    @Override
    public Result<PageInfo> queryByConditon(DataCollectReq dataCollectReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<DataCollectVo> pageResult = new PageInfo<>();
            pageResult = dataCollectDomainService.queryByConditions(dataCollectReq);
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("统计数据查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Async("taskExecutor")
    @Override
    public Future<Result> save(DataCollectReq dataCollectReq) {
        Result<Integer> result;
        try {
            if(dataCollectReq.getDataCollectVoList()!=null){
                Map<String,String> map = PropertiesUtil.paramMap;
                for (DataCollectVo dataCollectVo : dataCollectReq.getDataCollectVoList()) {
                    String name = "data."+dataCollectVo.getDataItemCode()+".name";
                    String unit = "data."+dataCollectVo.getDataItemCode()+".unit";
                    dataCollectVo.setDataItemName(map.get(name));
                    dataCollectVo.setUnit(map.get(unit));
                }
            }
            result = buildResult(dataCollectDomainService.save(dataCollectReq));
        }catch (Exception e){
            log.info("存储统计数据异常",e.getMessage());
            result = failResult(e);
        }
        return new AsyncResult<>(result);
    }

    @Override
    public Result monitorData(MonitorReq monitorReq) {
        Result result= null;
        try {
            //1.根据小区id和当前时间找到最近的统计数据
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(15)));
            List<NmplDataCollect> dataCollectList = dataCollectDomainService.queryMonitorData(monitorReq);
            //2.根据设备类型分组
            Integer userNumber = 0;
            Integer totalBandwidth=0;
            Integer dispenserSecretKey=0;
            Integer generatorSecretKey=0;
            Integer cacheSecretKey=0;
            for (NmplDataCollect nmplDataCollect : dataCollectList) {
                switch (nmplDataCollect.getDeviceType()){
                    case "01":
                        if(nmplDataCollect.getDataItemCode().equals("userNumber")){
                            userNumber+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("bandwidth")){
                            totalBandwidth+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        break;
                    case "02":
                        if(nmplDataCollect.getDataItemCode().equals("secretKeysRemainder")){
                            dispenserSecretKey+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("bandwidth")){
                            totalBandwidth+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        break;
                    case "03":
                        if(nmplDataCollect.getDataItemCode().equals("secretKeysRemainder")){
                            generatorSecretKey+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("bandwidth")){
                            totalBandwidth+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        break;
                    case "04":
                        if(nmplDataCollect.getDataItemCode().equals("secretKeysRemainder")){
                            cacheSecretKey+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("bandwidth")){
                            totalBandwidth+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        break;
                    default:
                }
            }
            MonitorResp monitorResp = new MonitorResp
                    (userNumber,totalBandwidth,dispenserSecretKey,generatorSecretKey,cacheSecretKey,new ArrayList<>());
            result = buildResult(monitorResp);
        } catch (Exception e) {
            log.info("查询监控数据异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result monitorDataTopTen(MonitorReq monitorReq) {
        Result result= null;
        try {
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(15)));
            result = buildResult(dataCollectDomainService.queryTopTen(monitorReq));
        }catch (Exception e){
            log.info("查询排行前10数据异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    public Date LocalTimeToUdate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
