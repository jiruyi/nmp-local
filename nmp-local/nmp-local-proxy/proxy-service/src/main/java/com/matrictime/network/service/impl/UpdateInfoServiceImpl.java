package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class UpdateInfoServiceImpl extends SystemBaseService implements UpdateInfoService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Resource
    private NmplUpdateInfoBoundaryMapper nmplUpdateInfoBoundaryMapper;

    @Resource
    private NmplUpdateInfoKeycenterMapper nmplUpdateInfoKeycenterMapper;

    @Resource
    private NmplLocalDeviceInfoMapper localDeviceInfoMapper;

    @Resource
    private NmplLocalBaseStationInfoMapper localBaseStationInfoMapper;

    @Override
    @Transactional
    public int updateInfo(String deviceType, String tableName, String operationType, String createUser, Date createTime) {
        int result = 0;
        if (DeviceTypeEnum.STATION_INSIDE.getCode().equals(deviceType)){
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(createUser);
            result = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
        }else if (DeviceTypeEnum.STATION_BOUNDARY.getCode().equals(deviceType)){
            NmplUpdateInfoBoundary updateInfo = new NmplUpdateInfoBoundary();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(createUser);
            result = nmplUpdateInfoBoundaryMapper.insertSelective(updateInfo);
        }else if (DeviceTypeEnum.DEVICE_DISPENSER.getCode().equals(deviceType)){
            NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(createUser);
            result = nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
        }
        return result;
    }

    /**
     * 获取本机设备类型列表
     * @return
     */
    @Override
    public Set<String> getNoticeDeviceTypes() {
        Set<String> resultSet = new HashSet<>();
        NmplLocalBaseStationInfoExample baseExample = new NmplLocalBaseStationInfoExample();
        baseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplLocalBaseStationInfo> stationInfos = localBaseStationInfoMapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(stationInfos)){
            for (NmplLocalBaseStationInfo info : stationInfos){
                resultSet.add(info.getStationType());
            }
        }

        NmplLocalDeviceInfoExample deviceExample = new NmplLocalDeviceInfoExample();
        deviceExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplLocalDeviceInfo> deviceInfos = localDeviceInfoMapper.selectByExample(deviceExample);
        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplLocalDeviceInfo info : deviceInfos){
                resultSet.add(info.getDeviceType());
            }
        }
        return resultSet;
    }
}
