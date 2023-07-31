package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBoundaryMapper;
import com.matrictime.network.dao.mapper.NmplUpdateInfoKeycenterMapper;
import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.dao.model.NmplUpdateInfoBoundary;
import com.matrictime.network.dao.model.NmplUpdateInfoKeycenter;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class UpdateInfoServiceImpl extends SystemBaseService implements UpdateInfoService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Resource
    private NmplUpdateInfoBoundaryMapper nmplUpdateInfoBoundaryMapper;

    @Resource
    private NmplUpdateInfoKeycenterMapper nmplUpdateInfoKeycenterMapper;

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
}
