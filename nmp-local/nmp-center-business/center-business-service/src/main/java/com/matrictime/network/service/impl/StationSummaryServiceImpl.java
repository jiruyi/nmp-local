package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.model.NmplStationSummary;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationSummaryVo;
import com.matrictime.network.request.StationSummaryRequest;
import com.matrictime.network.service.StationSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
@Slf4j
public class StationSummaryServiceImpl implements StationSummaryService {

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
            log.info("receiveStationSummary:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
