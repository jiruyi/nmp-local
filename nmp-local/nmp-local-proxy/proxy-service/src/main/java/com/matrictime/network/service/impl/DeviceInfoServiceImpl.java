package com.matrictime.network.service.impl;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.DeviceInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterDeviceInfoVo;
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
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
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

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;


    /**
     * 新增设备
     * @param infoVo
     * @return
     */
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

                int updateLocal = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_ADD, NMPL_LOCAL_DEVICE_INFO);
                log.info("DeviceInfoServiceImpl.addDeviceInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            // 插入通知表通知device表更新
            int addTable = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_ADD, NMPL_DEVICE_INFO);

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

    /**
     * 更新设备
     * @param infoVo
     * @return
     */
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

                int updateLocal = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_UPD, NMPL_LOCAL_DEVICE_INFO);
                log.info("DeviceInfoServiceImpl.updateDeviceInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            // 插入通知表通知device表更新
            int updateTable = deviceInfoService.updateTable(infoVo.getDeviceType(), createTime, EDIT_TYPE_UPD, NMPL_DEVICE_INFO);

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

    /**
     * 更新通知表
     * @param deviceType
     * @param createTime
     * @param operationType
     * @return
     */
    @Override
    @Transactional
    public Integer updateTable(String deviceType, Date createTime, String operationType, String tableName) {

        if (DeviceTypeEnum.DISPENSER.getCode().equals(deviceType)){
            NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
        }

        if (DeviceTypeEnum.CACHE.getCode().equals(deviceType)){
            NmplUpdateInfoCache updateInfo = new NmplUpdateInfoCache();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoCacheMapper.insertSelective(updateInfo);
        }

        if (DeviceTypeEnum.GENERATOR.getCode().equals(deviceType)){
            NmplUpdateInfoGenerator updateInfo = new NmplUpdateInfoGenerator();
            updateInfo.setTableName(tableName);
            updateInfo.setOperationType(operationType);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            return nmplUpdateInfoGeneratorMapper.insertSelective(updateInfo);
        }

        throw new SystemException(ErrorMessageContants.OPER_TYPE_IS_ERROR_MSG);
    }

    /**
     * 初始化本机设备
     * @param deviceInfoVos
     */
    @Override
    @Transactional
    public void initLocalInfo(List<CenterDeviceInfoVo> deviceInfoVos) {
        // 处理集合方便遍历操作
        Map<String, List<CenterDeviceInfoVo>> infoVoMap = new HashMap<>();
        List<CenterDeviceInfoVo> keycenter = new ArrayList<>();
        List<CenterDeviceInfoVo> cache = new ArrayList<>();
        List<CenterDeviceInfoVo> generator = new ArrayList<>();
        for (CenterDeviceInfoVo vo:deviceInfoVos){
            if (DeviceTypeEnum.DISPENSER.getCode().equals(vo.getDeviceType())){
                keycenter.add(vo);
            }
            if (DeviceTypeEnum.GENERATOR.getCode().equals(vo.getDeviceType())){
                generator.add(vo);
            }
            if (DeviceTypeEnum.CACHE.getCode().equals(vo.getDeviceType())){
                cache.add(vo);
            }
        }

        infoVoMap.put(DeviceTypeEnum.DISPENSER.getCode(),keycenter);
        infoVoMap.put(DeviceTypeEnum.GENERATOR.getCode(),generator);
        infoVoMap.put(DeviceTypeEnum.CACHE.getCode(),cache);

        // 遍历初始化数据
        for(Map.Entry<String, List<CenterDeviceInfoVo>> entry : infoVoMap.entrySet()){
            String deviceType = entry.getKey();
            List<CenterDeviceInfoVo> infoVos = entry.getValue();
            if (!CollectionUtils.isEmpty(infoVos)){
                // 查询本机有没有相关设备配置
                NmplLocalDeviceInfoExample  nmplLocalDeviceInfoExample = new NmplLocalDeviceInfoExample();
                nmplLocalDeviceInfoExample.createCriteria().andDeviceTypeEqualTo(deviceType);
                List<NmplLocalDeviceInfo> nmplLocalDeviceInfos = nmplLocalDeviceInfoMapper.selectByExample(nmplLocalDeviceInfoExample);

                if (CollectionUtils.isEmpty(nmplLocalDeviceInfos)){// 如果本机没有数据直接插入即可
                    List<DeviceInfoVo> deviceInfos = new ArrayList<>(infoVos.size());
                    Date createTime = new Date();
                    for (CenterDeviceInfoVo vo : infoVos){
                        DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                        BeanUtils.copyProperties(vo,deviceInfoVo);
                        deviceInfoVo.setUpdateTime(createTime);
                        deviceInfos.add(deviceInfoVo);
                    }
                    int addDevice = deviceInfoDomainService.localInsertDeviceInfo(deviceInfos);
                    int updateLocal = deviceInfoService.updateTable(deviceType, createTime, EDIT_TYPE_ADD, NMPL_LOCAL_DEVICE_INFO);
                }else {// 如果本机有数据
                    List<Long> ids = new ArrayList<>();
                    for (NmplLocalDeviceInfo deviceInfo:nmplLocalDeviceInfos){
                        if (DeviceStatusEnum.NORMAL.getCode().equals(deviceInfo.getStationStatus())){
                            nmplLocalDeviceInfoMapper.deleteByPrimaryKey(deviceInfo.getId());
                        }else {
                            ids.add(deviceInfo.getId());
                        }
                    }
                    for (CenterDeviceInfoVo vo:infoVos){
                        Date createTime = new Date();
                        if (ids.contains(vo.getDeviceId())){// 数据库已有本机信息则更新
                            NmplLocalDeviceInfo nmplLocalDeviceInfo = new NmplLocalDeviceInfo();
                            BeanUtils.copyProperties(vo,nmplLocalDeviceInfo);
                            nmplLocalDeviceInfo.setUpdateTime(createTime);
                            int updDevice = nmplLocalDeviceInfoMapper.updateByPrimaryKeySelective(nmplLocalDeviceInfo);
                            int updateLocal = deviceInfoService.updateTable(deviceType, createTime, EDIT_TYPE_UPD, NMPL_LOCAL_DEVICE_INFO);
                        }else {// 数据库没有相关数据则插入
                            NmplLocalDeviceInfo nmplLocalDeviceInfo = new NmplLocalDeviceInfo();
                            BeanUtils.copyProperties(vo,nmplLocalDeviceInfo);
                            nmplLocalDeviceInfo.setUpdateTime(createTime);
                            int addDevice = nmplLocalDeviceInfoMapper.insertSelective(nmplLocalDeviceInfo);
                            int updateLocal = deviceInfoService.updateTable(deviceType, createTime, EDIT_TYPE_ADD, NMPL_LOCAL_DEVICE_INFO);
                        }
                    }
                }
            }
        }


    }

    /**
     * 初始化设备列表
     * @param deviceInfoVos
     */
    @Override
    @Transactional
    public void initInfo(List<CenterDeviceInfoVo> deviceInfoVos) {
        int delDevice = nmplDeviceInfoMapper.deleteByExample(new NmplDeviceInfoExample());

        Date createTime = new Date();
        List<DeviceInfoVo> deviceInfos = new ArrayList<>(deviceInfoVos.size());
        for (CenterDeviceInfoVo vo : deviceInfoVos){
            DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
            BeanUtils.copyProperties(vo,deviceInfoVo);
            deviceInfoVo.setUpdateTime(createTime);
            deviceInfos.add(deviceInfoVo);
        }
        int addCount = deviceInfoDomainService.insertDeviceInfo(deviceInfos);

    }


}