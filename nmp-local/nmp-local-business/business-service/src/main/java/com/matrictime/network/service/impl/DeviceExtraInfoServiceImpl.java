package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.DeviceInfoExtResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceExtraInfoService;
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

    @Override
    public Result<Integer> insert(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result;
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
            NmplUser user = RequestContext.getUser();
            deviceExtraInfoRequest.setCreateUser(user.getNickName());
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            List<NmplDeviceInfoExtVo> list = deviceExtraInfoDomainService.query(nmplDeviceExtraInfo);
            if(list.size() > 0){
                return new Result<>(false,"入网id或ip重复");
            }
            result = buildResult(deviceExtraInfoDomainService.insert(nmplDeviceExtraInfo));
        }catch (Exception e){
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<Integer> update(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result;
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
            deviceExtraInfoRequest.setCreateUser(RequestContext.getUser().getNickName());
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            result = buildResult(deviceExtraInfoDomainService.update(nmplDeviceExtraInfo));
        }catch (Exception e){
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<Integer> delete(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<Integer> result;
        NmplDeviceExtraInfo nmplDeviceExtraInfo = new NmplDeviceExtraInfo();
        try {
            deviceExtraInfoRequest.setUpdateUser(RequestContext.getUser().getNickName());
            BeanUtils.copyProperties(deviceExtraInfoRequest,nmplDeviceExtraInfo);
            result = buildResult(deviceExtraInfoDomainService.delete(nmplDeviceExtraInfo));
        }catch (Exception e){
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        Result<PageInfo> result;
        try {
            result = buildResult(deviceExtraInfoDomainService.selectByCondition(deviceExtraInfoRequest));
        }catch (Exception e){
            result = failResult(e);
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























