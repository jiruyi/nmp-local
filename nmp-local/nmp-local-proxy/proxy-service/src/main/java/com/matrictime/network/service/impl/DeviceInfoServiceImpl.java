package com.matrictime.network.service.impl;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.DeviceInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.service.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_LOCAL_DEVICE_INFO;
import static com.matrictime.network.base.constant.DataConstants.NMPL_NMPL_DEVICE_INFO;
import static com.matrictime.network.constant.DataConstants.*;


@Service
@Slf4j
public class DeviceInfoServiceImpl extends SystemBaseService implements DeviceInfoService {

    @Autowired
    private DeviceInfoDomainService deviceInfoDomainService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Resource
    private NmplUpdateInfoKeycenterMapper nmplUpdateInfoKeycenterMapper;

    @Resource
    private NmplUpdateInfoGeneratorMapper nmplUpdateInfoGeneratorMapper;

    @Resource
    private NmplUpdateInfoCacheMapper nmplUpdateInfoCacheMapper;

    @Resource
    private NmplLocalDeviceInfoMapper nmplLocalDeviceInfoMapper;


    @Override
    @Transactional
    public Result addDeviceInfo(DeviceInfoVo infoVo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            infoVo.setUpdateTime(createTime);

            if (infoVo.getIsLocal()){
                NmplLocalDeviceInfo deviceInfo = new NmplLocalDeviceInfo();
                BeanUtils.copyProperties(infoVo,deviceInfo);
                int local = nmplLocalDeviceInfoMapper.insertSelective(deviceInfo);

                NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
                updateInfo.setTableName(NMPL_LOCAL_DEVICE_INFO);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);

                int updateLocal = nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
                log.info("DeviceInfoServiceImpl.addDeviceInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            // 插入通知表通知device表更新
            int addTable = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_ADD);

            NmplDeviceInfo deviceInfo = new NmplDeviceInfo();
            BeanUtils.copyProperties(infoVo,deviceInfo);
            int addDevice = deviceInfoDomainService.insert(deviceInfo);
            log.info("DeviceInfoServiceImpl.addDeviceInfo：addTable:{},addDeivce:{}",addTable,addDevice);
        }catch (Exception e){
            log.error("DeviceInfoServiceImpl.addDeviceInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateDeviceInfo(DeviceInfoVo infoVo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            infoVo.setUpdateTime(createTime);

            if (infoVo.getIsLocal()){
                NmplLocalDeviceInfo stationInfo = new NmplLocalDeviceInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int local = nmplLocalDeviceInfoMapper.updateByPrimaryKeySelective(stationInfo);

                NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
                updateInfo.setTableName(NMPL_LOCAL_DEVICE_INFO);
                updateInfo.setOperationType(EDIT_TYPE_UPD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);

                int updateLocal = nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
                log.info("DeviceInfoServiceImpl.updateDeviceInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            // 插入通知表通知device表更新
            int updateTable = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_UPD);

            NmplDeviceInfo deviceInfo = new NmplDeviceInfo();
            BeanUtils.copyProperties(infoVo,deviceInfo);
            int updateDevice = deviceInfoDomainService.update(deviceInfo);
            log.info("DeviceInfoServiceImpl.updateDeviceInfo：updateTable:{},updateDevice:{}",updateTable,updateDevice);
        }catch (Exception e){
            log.error("DeviceInfoServiceImpl.updateDeviceInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Integer updateTable(String deviceType, Date createTime, String operationType) {

        if (DeviceTypeEnum.DISPENSER.getCode().equals(deviceType)){
            NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
            updateInfo.setTableName(NMPL_NMPL_DEVICE_INFO);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
        }

        if (DeviceTypeEnum.CACHE.getCode().equals(deviceType)){
            NmplUpdateInfoCache updateInfo = new NmplUpdateInfoCache();
            updateInfo.setTableName(NMPL_NMPL_DEVICE_INFO);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoCacheMapper.insertSelective(updateInfo);
        }

        if (DeviceTypeEnum.GENERATOR.getCode().equals(deviceType)){
            NmplUpdateInfoGenerator updateInfo = new NmplUpdateInfoGenerator();
            updateInfo.setTableName(NMPL_NMPL_DEVICE_INFO);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoGeneratorMapper.insertSelective(updateInfo);
        }

        throw new SystemException(ErrorMessageContants.OPER_TYPE_IS_ERROR_MSG);
    }


}