package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.AddRouteRequest;
import com.matrictime.network.request.UpdateRouteRequest;
import com.matrictime.network.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_ROUTE;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class RouteServiceImpl  extends SystemBaseService implements RouteService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Autowired
    private RouteDomainService routeDomainService;

    @Override
    @Transactional
    public Result<Integer> addRoute(List<RouteVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            for (RouteVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
            }
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_ROUTE);
            updateInfo.setOperationType(EDIT_TYPE_ADD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            int batchNum = routeDomainService.insertRoute(voList);
            log.info("RouteServiceImpl.addRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.addRoute：{}",e.getMessage());
            result = failResult(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateRoute(List<RouteVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            for (RouteVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
            }
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_ROUTE);
            updateInfo.setOperationType(EDIT_TYPE_UPD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            int batchNum = routeDomainService.updateRoute(voList);
            log.info("RouteServiceImpl.updateRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.updateRoute：{}",e.getMessage());
            result = failResult(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
