package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.service.DataCollectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
@Slf4j
@Service
public class DataCollectServiceImpl implements DataCollectService {

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
            result.setResultObj(dataCollectDomainService.insertDataCollect(dataCollectResponse));
        }catch (Exception e){
            log.info("insertDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询单个小区流量占比
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Result<String> selectCompanyData(DataCollectRequest dataCollectRequest) {
        Result<String> result = new Result<>();
        try {
            Double companyData = dataCollectDomainService.selectCompanyData(dataCollectRequest);
            Double sumData = dataCollectDomainService.sumDataValue(dataCollectRequest);
            result.setSuccess(true);
            result.setResultObj(avgData(companyData / sumData));
        }catch (Exception e){
            log.info("selectCompanyData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询单个小区折线图
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Result<List<DataTimeVo>> selectCompanyLoadData(DataCollectRequest dataCollectRequest) {
        Result<List<DataTimeVo>> result = new Result<>();
        try {
            result.setResultObj(dataCollectDomainService.selectCompanyLoadData(dataCollectRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("selectCompanyLoadData:{}",e.getMessage());
        }

        return result;
    }

    /**
     * 保留小数点后两位
     * @param value
     * @return
     */
    public String avgData(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

}
