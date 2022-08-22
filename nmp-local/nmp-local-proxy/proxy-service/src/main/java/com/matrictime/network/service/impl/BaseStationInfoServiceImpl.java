package com.matrictime.network.service.impl;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.NmplLocalBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfo;
import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;
import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_BASE_STATION_INFO;
import static com.matrictime.network.constant.DataConstants.*;


@Service
@Slf4j
public class BaseStationInfoServiceImpl extends SystemBaseService implements BaseStationInfoService {

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Resource
    private NmplLocalBaseStationInfoMapper nmplLocalBaseStationInfoMapper;


    @Override
    @Transactional
    public Result addBaseStationInfo(BaseStationInfoVo infoVo) {
        Result result = new Result<>();
        try {
            if (infoVo.getIsLocal()){
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int local = nmplLocalBaseStationInfoMapper.insertSelective(stationInfo);
                log.info("BaseStationInfoServiceImpl.addBaseStationInfo：local:{}",local);
            }

            Date createTime = new Date();
            infoVo.setUpdateTime(createTime);

            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_BASE_STATION_INFO);
            updateInfo.setOperationType(EDIT_TYPE_ADD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);

            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            List<BaseStationInfoVo> infoVos = new ArrayList<>();
            infoVos.add(infoVo);
            int batchNum = baseStationInfoDomainService.insertBaseStationInfo(infoVos);
            log.info("BaseStationInfoServiceImpl.addBaseStationInfo：addNum:{},batchNum:{}",addNum,batchNum);
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
            if (infoVo.getIsLocal()){
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int local = nmplLocalBaseStationInfoMapper.updateByPrimaryKeySelective(stationInfo);
                log.info("BaseStationInfoServiceImpl.updateBaseStationInfo：local:{}",local);
            }

            Date createTime = new Date();
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_BASE_STATION_INFO);
            updateInfo.setOperationType(EDIT_TYPE_UPD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            List<BaseStationInfoVo> infoVos = new ArrayList<>();
            infoVos.add(infoVo);
            int batchNum = baseStationInfoDomainService.updateBaseStationInfo(infoVos);
            log.info("BaseStationInfoServiceImpl.updateBaseStationInfo：addNum:{},batchNum:{}",addNum,batchNum);
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