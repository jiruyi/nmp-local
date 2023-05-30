package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.LocalBaseStationDomainService;
import com.matrictime.network.dao.mapper.NmplLocalBaseStationMapper;
import com.matrictime.network.dao.mapper.NmplLocalDeviceMapper;
import com.matrictime.network.dao.model.NmplLocalBaseStation;
import com.matrictime.network.dao.model.NmplLocalBaseStationExample;
import com.matrictime.network.dao.model.NmplLocalDevice;
import com.matrictime.network.dao.model.NmplLocalDeviceExample;
import com.matrictime.network.request.BaseStationCurrentRequest;
import com.matrictime.network.request.CurrentCountRequest;
import com.matrictime.network.request.DeviceCurrentRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/5/24.
 */
@Service
public class LocalBaseStationDomainServiceImpl implements LocalBaseStationDomainService {

    @Resource
    private NmplLocalBaseStationMapper localBaseStationMapper;

    @Resource
    private NmplLocalDeviceMapper localDeviceMapper;

    @Override
    public CurrentCountRequest selectLocalBaseStation() {
        //查询基站表
        NmplLocalBaseStationExample localBaseStationExample = new NmplLocalBaseStationExample();
        NmplLocalBaseStationExample.Criteria criteria = localBaseStationExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        List<NmplLocalBaseStation> localBaseStations = localBaseStationMapper.selectByExample(localBaseStationExample);
        //查询设备表
        NmplLocalDeviceExample localDeviceExample = new NmplLocalDeviceExample();
        NmplLocalDeviceExample.Criteria criteria1 = localDeviceExample.createCriteria();
        criteria1.andIsExistEqualTo(true);
        List<NmplLocalDevice> localDevices = localDeviceMapper.selectByExample(localDeviceExample);
        //返回结果
        BaseStationCurrentRequest baseStationCurrentRequest = new BaseStationCurrentRequest();
        DeviceCurrentRequest deviceCurrentRequest = new DeviceCurrentRequest();
        CurrentCountRequest currentCountRequest = new CurrentCountRequest();
        if(!CollectionUtils.isEmpty(localBaseStations)){
            BeanUtils.copyProperties(localBaseStations.get(0),baseStationCurrentRequest);
            currentCountRequest.setBaseStationCurrentRequest(baseStationCurrentRequest);
        }
        if(!CollectionUtils.isEmpty(localDevices)){
            BeanUtils.copyProperties(localDevices.get(0),deviceCurrentRequest);
            currentCountRequest.setDeviceCurrentRequest(deviceCurrentRequest);
        }
        return currentCountRequest;
    }

}
