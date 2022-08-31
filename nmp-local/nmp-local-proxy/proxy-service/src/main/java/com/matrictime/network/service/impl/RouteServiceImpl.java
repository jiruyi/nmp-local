package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.NmplRouteMapper;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.mapper.extend.RouteMapper;
import com.matrictime.network.dao.model.NmplRoute;
import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private NmplRouteMapper nmplRouteMapper;

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
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateRoute(RouteVo req) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            req.setUpdateTime(createTime);
            Long id = req.getId();
            NmplRoute nmplRoute = nmplRouteMapper.selectByPrimaryKey(id);

            int batchNum=0;
            int addNum=0;
            List<RouteVo> voList = new ArrayList<>(1);
            voList.add(req);
            if (nmplRoute != null){
                batchNum = routeDomainService.updateRoute(voList);

                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_ROUTE);
                updateInfo.setOperationType(EDIT_TYPE_UPD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            }else {
                batchNum = routeDomainService.insertRoute(voList);

                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_ROUTE);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            }

            log.info("RouteServiceImpl.updateRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.updateRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
