package com.matrictime.network.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.util.CommonCheckUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        DeviceInfoRequest infoRequest = new DeviceInfoRequest();
        try {
            deviceInfoRequest.setCreateTime(getFormatDate(date));
            deviceInfoRequest.setUpdateTime(getFormatDate(date));
            deviceInfoRequest.setDeviceId(SnowFlake.nextId_String());
            deviceInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            boolean publicPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getPublicNetworkPort());
            boolean lanPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getLanPort());
            if(publicPortReg == false || lanPortReg == false){
                return new Result<>(false,"端口格式不正确");
            }
            //判断小区是否正确
            String preBID = companyInfoDomainService.getPreBID(deviceInfoRequest.getRelationOperatorId());
            if(StringUtil.isEmpty(preBID)){
                return new Result<>(false,"运营商不存在");
            }
            String NetworkId = preBID + "-" + deviceInfoRequest.getStationNetworkId();
            deviceInfoRequest.setStationNetworkId(NetworkId);
            infoRequest.setStationNetworkId(deviceInfoRequest.getStationNetworkId());
            infoRequest.setPublicNetworkIp(deviceInfoRequest.getPublicNetworkIp());
            infoRequest.setLanIp(deviceInfoRequest.getLanIp());
            List<DeviceInfoVo> devices = deviceDomainService.getDevices(infoRequest);
            if(devices.size() > 0){
                return new Result<>(false,"入网id或ip重复");
            }
            insertFlag = deviceDomainService.insertDevice(deviceInfoRequest);
            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
            }
        }catch (Exception e){
            result.setErrorCode("参数异常");
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
            result.setErrorCode("参数异常");
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
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            boolean publicPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getPublicNetworkPort());
            boolean lanPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getLanPort());
            if(publicPortReg == false || lanPortReg == false){
                return new Result<>(false,"端口格式不正确");
            }
            updateFlag = deviceDomainService.updateDevice(deviceInfoRequest);
            if(updateFlag == 1){
                result.setResultObj(updateFlag);
                result.setSuccess(true);
            }
        }catch (Exception e){
            result.setErrorCode("参数异常");
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
            result.setErrorMsg("参数异常");
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
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectActiveDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            if (ParamCheckUtil.checkVoStrBlank(deviceInfoRequest.getDeviceType())){
                log.info("DeviceServiceImpl.selectActiveDevice 接口异常,DeviceType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                result.setErrorMsg("系统异常，请稍后再试");
                result.setSuccess(false);
                return result;
            }
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectActiveDevice(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
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

    @Override
    public Result<PageInfo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = deviceDomainService.selectDeviceALl(deviceInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
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