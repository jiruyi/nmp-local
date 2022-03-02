package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.model.BaseStationInfoVo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class BaseStationInfoServiceImpl implements BaseStationInfoService {

    @Autowired
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Override
    public Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer insertFlag = null;
        try {
            baseStationInfoRequest.setCreateTime(getFormatDate(date));
            baseStationInfoRequest.setExist(true);
            baseStationInfoRequest.setStationStatus("01");
            insertFlag = baseStationInfoDomainService.insertBaseStationInfo(baseStationInfoRequest);
            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
            }else {
                result.setResultObj(insertFlag);
                result.setSuccess(false);
            }
        }catch (Exception e){
            log.error("基站新增{}新增异常：{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer updateFlag = null;
        try {
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            updateFlag = baseStationInfoDomainService.updateBaseStationInfo(baseStationInfoRequest);
            if(updateFlag == 1){
                result.setSuccess(true);
                result.setResultObj(updateFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer deleteFlag = null;
        try {
            deleteFlag = baseStationInfoDomainService.deleteBaseStationInfo(baseStationInfoRequest);
            if(deleteFlag == 1){
                result.setSuccess(true);
                result.setResultObj(deleteFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<BaseStationInfoResponse> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
            List<BaseStationInfoVo> baseStationInfos = baseStationInfoDomainService.selectBaseStationInfo(baseStationInfoRequest);
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfos);
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }


    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }
}