package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.DeviceInfoExtResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceExtraInfoService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DeviceExtraInfoServiceImpl extends SystemBaseService implements DeviceExtraInfoService {

    @Resource
    private DeviceExtraInfoDomainService deviceExtraInfoDomainService;
    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Override
    public Result<Integer> insert(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result = new Result<>();
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
//            Integer stationNetworkId = nmplBaseStationInfoMapper.getSequenceId();
//            deviceExtraInfoRequest.setStationNetworkId(stationNetworkId.toString());
            if(!CommonCheckUtil.checkStringLength(deviceExtraInfoRequest.getDeviceName(),null,16)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            NmplUser user = RequestContext.getUser();
            deviceExtraInfoRequest.setCreateUser(user.getNickName());
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(deviceExtraInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(deviceExtraInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            List<NmplDeviceInfoExtVo> list = deviceExtraInfoDomainService.query(nmplDeviceExtraInfo);
            if(list.size() > 0){
                return new Result<>(false,"入网id或ip重复");
            }
            result = buildResult(deviceExtraInfoDomainService.insert(nmplDeviceExtraInfo));
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> update(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result = new Result<>();
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
            if(!CommonCheckUtil.checkStringLength(deviceExtraInfoRequest.getDeviceName(),null,16)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            deviceExtraInfoRequest.setCreateUser(RequestContext.getUser().getNickName());
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(deviceExtraInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(deviceExtraInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            boolean publicPortReg = CommonCheckUtil.isPortLegal(deviceExtraInfoRequest.getPublicNetworkPort());
            boolean lanPortReg = CommonCheckUtil.isPortLegal(deviceExtraInfoRequest.getLanPort());
            if(publicPortReg == false || lanPortReg == false){
                return new Result<>(false,"端口格式不正确");
            }
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            result = buildResult(deviceExtraInfoDomainService.update(nmplDeviceExtraInfo));
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> delete(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result = new Result<>();
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
            deviceExtraInfoRequest.setUpdateUser(RequestContext.getUser().getNickName());
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            result = buildResult(deviceExtraInfoDomainService.delete(nmplDeviceExtraInfo));
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            result = buildResult(deviceExtraInfoDomainService.selectByCondition(deviceExtraInfoRequest));
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<DeviceInfoExtResponse> selectDevices(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<DeviceInfoExtResponse> result = new Result<>();
        DeviceInfoExtResponse deviceInfoExtResponse = new DeviceInfoExtResponse();
        deviceInfoExtResponse.setList(deviceExtraInfoDomainService.selectDevices(deviceExtraInfoRequest));
        result.setResultObj(deviceInfoExtResponse);
        return result;
    }
}























