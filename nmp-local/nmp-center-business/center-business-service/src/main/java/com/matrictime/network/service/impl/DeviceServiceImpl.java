package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.model.NmplStationSummary;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.StationSummaryVo;
import com.matrictime.network.request.StationSummaryRequest;
import com.matrictime.network.service.DataHandlerService;
import com.matrictime.network.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/9/4.
 */

@Service("device")
@Slf4j
public class DeviceServiceImpl implements DeviceService, DataHandlerService {
    @Resource
    private StationSummaryDomainService summaryDomainService;

    @Override
    public Result<Integer> receiveStationSummary(StationSummaryVo stationSummaryVo) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            StationSummaryRequest stationSummaryRequest = new StationSummaryRequest();
            BeanUtils.copyProperties(stationSummaryVo,stationSummaryRequest);
            List<NmplStationSummary> nmplStationSummaries = summaryDomainService.selectStationSummary(stationSummaryRequest);
            if(CollectionUtils.isEmpty(nmplStationSummaries)){
                i = summaryDomainService.insertStationSummary(stationSummaryRequest);
            }else {
                i = summaryDomainService.updateStationSummary(stationSummaryRequest);
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("receiveDevice:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public void handlerData(DataPushBody dataPushBody) {
        try {
            if(ObjectUtils.isEmpty(dataPushBody)){
                return;
            }
            String dataJsonStr = dataPushBody.getBusiDataJsonStr();
            List<StationSummaryVo> list = JSONArray.parseArray(dataJsonStr, StationSummaryVo.class);
            for(StationSummaryVo stationSummaryVo: list){
                StationSummaryRequest stationSummaryRequest = new StationSummaryRequest();
                BeanUtils.copyProperties(stationSummaryVo,stationSummaryRequest);
                List<NmplStationSummary> nmplStationSummaries = summaryDomainService.selectStationSummary(stationSummaryRequest);
                stationSummaryRequest.setUploadTime(new Date());
                if(CollectionUtils.isEmpty(nmplStationSummaries)){
                    summaryDomainService.insertStationSummary(stationSummaryRequest);
                }else {
                    summaryDomainService.updateStationSummary(stationSummaryRequest);
                }
            }
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
