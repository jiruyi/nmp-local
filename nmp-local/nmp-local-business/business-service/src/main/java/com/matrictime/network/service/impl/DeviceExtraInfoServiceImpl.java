package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceExtraVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceExtraInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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


}























