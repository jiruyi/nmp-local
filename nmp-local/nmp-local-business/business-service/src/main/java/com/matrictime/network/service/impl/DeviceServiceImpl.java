package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceDomainService deviceDomainService;

    @Override
    public Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer insertFlag = null;
        Date date = new Date();
        try {
            deviceInfoRequest.setCreateTime(getFormatDate(date));
            insertFlag = deviceDomainService.insertDevice(deviceInfoRequest);
            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
            }
        }catch (Exception e){
            result.setErrorCode(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> deleteDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer deleteFlag = null;
        try {
            deleteFlag = deviceDomainService.deleteDevice(deviceInfoRequest);
            if(deleteFlag == 1){
                result.setResultObj(deleteFlag);
                result.setSuccess(true);
            }
        }catch (Exception e){
            result.setErrorCode(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> updateDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer updateFlag = null;
        Date date = new Date();
        try {
            deviceInfoRequest.setUpdateTime(getFormatDate(date));
            updateFlag = deviceDomainService.updateDevice(deviceInfoRequest);
            if(updateFlag == 1){
                result.setResultObj(updateFlag);
                result.setSuccess(true);
            }
        }catch (Exception e){
            result.setErrorCode(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        DeviceResponse deviceResponse = new DeviceResponse();
        try {
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectDevice(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorCode(e.getMessage());
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