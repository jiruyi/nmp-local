package com.matrictime.network.service.impl;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.DeviceInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.request.AddBaseStationInfoRequest;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;
import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;


@Service
@Slf4j
public class BaseStationInfoServiceImpl extends SystemBaseService implements BaseStationInfoService {

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private DeviceInfoDomainService deviceInfoDomainService;

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Resource
    private NmplLocalBaseStationInfoMapper nmplLocalBaseStationInfoMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplRouteMapper nmplRouteMapper;

    @Resource
    private NmplLinkRelationMapper nmplLinkRelationMapper;



    @Override
    @Transactional
    public Result addBaseStationInfo(AddBaseStationInfoRequest req) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            BaseStationInfoVo infoVo = req.getLocalBaseInfo();
            infoVo.setUpdateTime(createTime);

            if (infoVo.getIsLocal()){
                /* 本机基站信息插入 */

                //删除已有信息
                int delLocal = nmplLocalBaseStationInfoMapper.deleteByExample(new NmplLocalBaseStationInfoExample());
                int delBase = nmplBaseStationInfoMapper.deleteByExample(new NmplBaseStationInfoExample());
                int delRoute = nmplRouteMapper.deleteByExample(new NmplRouteExample());
                int delLink = nmplLinkRelationMapper.deleteByExample(new NmplLinkRelationExample());

                // 插入本机基站信息
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int addlocal = nmplLocalBaseStationInfoMapper.insertSelective(stationInfo);

                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_LOCAL_BASE_STATION_INFO);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);

                int updateLocal = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);


                // 插入所有基站信息
                int addBase = 0;
                int updateBase = 0;
                List<BaseStationInfoVo> stationVos = req.getStationInfoVos();
                if (!CollectionUtils.isEmpty(stationVos)){
                    for (int i=0;i<stationVos.size();i++){
                        stationVos.get(i).setUpdateTime(createTime);
                    }
                    addBase = baseStationInfoDomainService.insertBaseStationInfo(stationVos);
                    // 插入通知表通知base表更新
                    updateInfo.setTableName(NMPL_BASE_STATION_INFO);
                    updateBase = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
                }


                int addDevice =0;
                int updateDevice =0;
                List<DeviceInfoVo> deviceInfoVos = req.getDeviceInfoVos();
                if (!CollectionUtils.isEmpty(deviceInfoVos)){
                    for (int i=0;i<deviceInfoVos.size();i++){
                        deviceInfoVos.get(i).setUpdateTime(createTime);
                    }
                    addDevice = deviceInfoDomainService.insertDeviceInfo(deviceInfoVos);
                    // 插入通知表通知base表更新
                    updateInfo.setTableName(NMPL_NMPL_DEVICE_INFO);

                    updateDevice = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
                }


                log.info("BaseStationInfoServiceImpl.addBaseStationInfo：" +
                                "delLocal:{},delBase:{},delRoute:{},delLink:{},addlocal:{},updateLocal:{},addBase:{},updateBase:{},addDevice:{},updateDevice:{}",
                        delLocal,delBase,delRoute,delLink,addlocal,updateLocal,addBase,updateBase,addDevice,updateDevice);


            }else {
                /* 其他基站的推送插入 */

                // 插入通知表通知base表更新
                NmplUpdateInfoBase updateTable = new NmplUpdateInfoBase();
                updateTable.setTableName(NMPL_BASE_STATION_INFO);
                updateTable.setOperationType(EDIT_TYPE_ADD);
                updateTable.setCreateTime(createTime);
                updateTable.setCreateUser(SYSTEM_NM);

                int addTable = nmplUpdateInfoBaseMapper.insertSelective(updateTable);

                NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
                BeanUtils.copyProperties(infoVo,baseStationInfo);
                int addStation = baseStationInfoDomainService.insert(baseStationInfo);
                log.info("BaseStationInfoServiceImpl.addBaseStationInfo：addTable:{},addStation:{}",addTable,addStation);
            }
        }catch (Exception e){
            log.error("BaseStationInfoServiceImpl.addBaseStationInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateBaseStationInfo(BaseStationInfoVo infoVo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            infoVo.setUpdateTime(createTime);

            if (infoVo.getIsLocal()){
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int local = nmplLocalBaseStationInfoMapper.updateByPrimaryKeySelective(stationInfo);

                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_LOCAL_BASE_STATION_INFO);
                updateInfo.setOperationType(EDIT_TYPE_UPD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);

                int updateLocal = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
                log.info("BaseStationInfoServiceImpl.updateBaseStationInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_BASE_STATION_INFO);
            updateInfo.setOperationType(EDIT_TYPE_UPD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int updateTable = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);

            NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
            BeanUtils.copyProperties(infoVo,baseStationInfo);
            int updateBase = baseStationInfoDomainService.update(baseStationInfo);
            log.info("BaseStationInfoServiceImpl.updateBaseStationInfo：updateTable:{},updateBase:{}",updateTable,updateBase);
        }catch (Exception e){
            log.error("BaseStationInfoServiceImpl.updateBaseStationInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result<Integer> deleteBaseStationInfo(DeleteBaseStationInfoRequest request) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(request.getIds())){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            int batchNum = baseStationInfoDomainService.deleteBaseStationInfo(request.getIds());
            log.info("BaseStationInfoServiceImpl.deleteBaseStationInfo：batchNum:{}",batchNum);
        }catch (Exception e){
            log.error("BaseStationInfoServiceImpl.deleteBaseStationInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}