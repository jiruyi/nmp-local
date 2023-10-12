package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.modelVo.LoanVo;
import com.matrictime.network.modelVo.PercentageFlowVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.service.DataHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
@Slf4j
@Service("data_collect")
public class DataCollectServiceImpl implements DataCollectService, DataHandlerService {

    @Resource
    private DataCollectDomainService dataCollectDomainService;

    /**
     * 查询各个流量图
     * @return
     */
    @Override
    public Result<List<DataTimeVo>> selectLoadData(DataCollectRequest dataCollectRequest) {
        Result<List<DataTimeVo>> result = new Result<>();
        try {
            result.setResultObj(dataCollectDomainService.selectLoadData(dataCollectRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("selectLoadData:{}",e.getMessage());
        }

        return result;
    }

    /**
     * 查询各个流量总和
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Result<Double> sumDataValue(DataCollectRequest dataCollectRequest) {
        Result<Double> result = new Result<>();
        try {
            result.setResultObj(dataCollectDomainService.sumDataValue(dataCollectRequest));
            result.setSuccess(true);
        }catch (Exception e){
            log.info("sumDataValue:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }

        return result;
    }

    /**
     * 流量上班插入
     * @param dataCollectResponse
     * @return
     */
    @Override
    public Result<Integer> insertDataCollect(DataCollectResponse dataCollectResponse) {
        Result<Integer> result = new Result<>();
        try {
            if(CollectionUtils.isEmpty(dataCollectResponse.getList())){
                return new Result<>(false,"上传数据为空");
            }
            result.setSuccess(true);
            result.setResultObj(dataCollectDomainService.insertDataCollect(dataCollectResponse.getList()));
        }catch (Exception e){
            log.info("insertDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询单个小区流量占比
     * @param
     * @return
     */
    @Override
    public Result<List<PercentageFlowVo>> selectCompanyData() {
        Result<List<PercentageFlowVo>> result = new Result<>();
        try {
            List<PercentageFlowVo> list = dataCollectDomainService.selectCompanyData();
            result.setResultObj(list);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectCompanyData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询带宽
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Result<List<LoanVo>> selectLoan(DataCollectRequest dataCollectRequest) {
        Result<List<LoanVo>> result = new Result<>();
        try {
            List<LoanVo> list = dataCollectDomainService.selectLoan(dataCollectRequest);
            result.setResultObj(list);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectLoan:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
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
            dataCollectDomainService.insertDataCollect(JSONObject.parseObject(dataJsonStr,List.class));
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
