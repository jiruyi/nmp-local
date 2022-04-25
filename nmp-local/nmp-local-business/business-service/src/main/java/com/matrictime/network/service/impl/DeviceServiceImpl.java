package com.matrictime.network.service.impl;

import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
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

    @Resource
    private CompanyInfoDomainService companyInfoDomainService;

    @Override
    public Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer insertFlag = null;
        Date date = new Date();
        try {
            deviceInfoRequest.setCreateTime(getFormatDate(date));
            deviceInfoRequest.setUpdateTime(getFormatDate(date));
            deviceInfoRequest.setDeviceId(SnowFlake.nextId_String());
            deviceInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            //判断小区是否正确

            String preBID = companyInfoDomainService.getPreBID(deviceInfoRequest.getRelationOperatorId());
            String NetworkId = preBID + "-" + deviceInfoRequest.getStationNetworkId();
            deviceInfoRequest.setStationNetworkId(NetworkId);
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
        Integer deleteFlag;
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
        Integer updateFlag;
        try {
            deviceInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
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
    public Result<PageInfo> selectDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = deviceDomainService.selectDevice(deviceInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectLinkDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectLinkDevice(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<StationVo> selectDeviceId(DeviceInfoRequest deviceInfoRequest) {
       Result<StationVo> result = new Result<>();
        try {
            StationVo stationVo = deviceDomainService.selectDeviceId(deviceInfoRequest);
            result.setResultObj(stationVo);
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