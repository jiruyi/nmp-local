package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.RouteMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.service.RouteService;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
public class RouteServiceImpl  extends SystemBaseService implements RouteService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Autowired
    private RouteDomainService routeDomainService;

    @Resource
    private NmplRouteMapper nmplRouteMapper;

    @Resource
    private NmplBusinessRouteMapper nmplBusinessRouteMapper;

    @Resource
    private NmplInternetRouteMapper nmplInternetRouteMapper;

    @Resource
    private NmplStaticRouteMapper nmplStaticRouteMapper;

    @Autowired
    private UpdateInfoService updateInfoService;

//    /**
//     * 新增路由
//     * @param voList
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result<Integer> addRoute(List<RouteVo> voList) {
//        Result result = new Result<>();
//        try {
//            if (CollectionUtils.isEmpty(voList)){
//                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
//            }
//            Date createTime = new Date();
//            for (RouteVo infoVo : voList){
//                infoVo.setUpdateTime(createTime);
//            }
//            // 新增路由信息
//            int batchNum = routeDomainService.insertRoute(voList);
//
//            // 通知基站有路由信息变更
//            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
//            updateInfo.setTableName(NMPL_ROUTE);
//            updateInfo.setOperationType(EDIT_TYPE_ADD);
//            updateInfo.setCreateTime(createTime);
//            updateInfo.setCreateUser(SYSTEM_NM);
//            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
//
//            log.info("RouteServiceImpl.addRoute：addNum:{},batchNum:{}",addNum,batchNum);
//        }catch (Exception e){
//            log.error("RouteServiceImpl.addRoute：{}",e.getMessage());
//            result = failResult("");
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//        return result;
//    }
//
//    /**
//     * 更新路由
//     * @param req
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result<Integer> updateRoute(RouteVo req) {
//        Result result = new Result<>();
//        try {
//            Date createTime = new Date();
//            req.setUpdateTime(createTime);
//            Long id = req.getId();
//            NmplRoute nmplRoute = nmplRouteMapper.selectByPrimaryKey(id);
//
//            int batchNum=0;
//            int addNum=0;
//            List<RouteVo> voList = new ArrayList<>(1);
//            voList.add(req);
//
//            // 判断路由信息是否存在
//            if (nmplRoute != null){// 路由已存在，则更新路由信息
//                batchNum = routeDomainService.updateRoute(voList);
//
//                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
//                updateInfo.setTableName(NMPL_ROUTE);
//                updateInfo.setOperationType(EDIT_TYPE_UPD);
//                updateInfo.setCreateTime(createTime);
//                updateInfo.setCreateUser(SYSTEM_NM);
//                addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
//            }else {// 路由不存在在插入路由信息
//                batchNum = routeDomainService.insertRoute(voList);
//
//                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
//                updateInfo.setTableName(NMPL_ROUTE);
//                updateInfo.setOperationType(EDIT_TYPE_ADD);
//                updateInfo.setCreateTime(createTime);
//                updateInfo.setCreateUser(SYSTEM_NM);
//                addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
//            }
//
//            log.info("RouteServiceImpl.updateRoute：addNum:{},batchNum:{}",addNum,batchNum);
//        }catch (Exception e){
//            log.error("RouteServiceImpl.updateRoute：{}",e.getMessage());
//            result = failResult("");
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//        return result;
//    }

    /**
     * 新增业务路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> addBusinessRoute(NmplBusinessRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            List<NmplBusinessRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            // 新增路由信息
            int batchNum = routeDomainService.insertBusinessRoute(voList);

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_BUSINESS_ROUTE, EDIT_TYPE_ADD, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }

            log.info("RouteServiceImpl.addBusinessRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.addBusinessRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 修改业务路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateBusinessRoute(NmplBusinessRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            Long id = vo.getId();
            NmplBusinessRoute nmplBusinessRoute = nmplBusinessRouteMapper.selectByPrimaryKey(id);

            int batchNum=0;
            List<NmplBusinessRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            String editType = null;
            // 判断路由信息是否存在
            if (nmplBusinessRoute != null){// 路由已存在，则更新路由信息
                batchNum = routeDomainService.updateBusinessRoute(voList);

                editType = EDIT_TYPE_UPD;
            }else {// 路由不存在在插入路由信息
                batchNum = routeDomainService.insertBusinessRoute(voList);

                editType = EDIT_TYPE_ADD;
            }

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_BUSINESS_ROUTE, editType, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }

            log.info("RouteServiceImpl.updateBusinessRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.updateBusinessRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 新增出网路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> addInternetRoute(NmplInternetRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            List<NmplInternetRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            // 新增路由信息
            int batchNum = routeDomainService.insertInternetRoute(voList);

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_INTERNET_ROUTE, EDIT_TYPE_ADD, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }

            log.info("RouteServiceImpl.addInternetRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.addInternetRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 修改出网路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateInternetRoute(NmplInternetRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            Long id = vo.getId();
            NmplInternetRoute nmplInternetRoute = nmplInternetRouteMapper.selectByPrimaryKey(id);

            int batchNum=0;
            List<NmplInternetRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            String editType = null;
            // 判断路由信息是否存在
            if (nmplInternetRoute != null){// 路由已存在，则更新路由信息
                batchNum = routeDomainService.updateInternetRoute(voList);

                editType = EDIT_TYPE_UPD;
            }else {// 路由不存在在插入路由信息
                batchNum = routeDomainService.insertInternetRoute(voList);

                editType = EDIT_TYPE_ADD;
            }

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_INTERNET_ROUTE, editType, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }

            log.info("RouteServiceImpl.updateInternetRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.updateInternetRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 新增静态路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> addStaticRoute(NmplStaticRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            List<NmplStaticRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            // 新增路由信息
            int batchNum = routeDomainService.insertStaticRoute(voList);

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_STATIC_ROUTE, EDIT_TYPE_ADD, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }

            log.info("RouteServiceImpl.addStaticRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.addStaticRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 修改静态路由
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateStaticRoute(NmplStaticRouteVo vo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            vo.setUpdateTime(createTime);
            Long id = vo.getId();
            NmplStaticRoute nmplStaticRoute = nmplStaticRouteMapper.selectByPrimaryKey(id);

            int batchNum=0;
            List<NmplStaticRouteVo> voList = new ArrayList<>(1);
            voList.add(vo);

            String editType = null;
            List<String> editList = new ArrayList<>();
            // 判断路由信息是否存在
            if (nmplStaticRoute != null){
                // 路由已存在，则更新路由信息
                batchNum = routeDomainService.updateStaticRoute(voList);
                editType = EDIT_TYPE_UPD;
                for(int i = 0;i< batchNum; i++){
                    editList.add(editType);
                }
            }else {
                // 路由不存在在插入路由信息
                batchNum = routeDomainService.insertStaticRoute(voList);
                editType = EDIT_TYPE_ADD;
                for(int i = 0;i< batchNum; i++){
                    editList.add(editType);
                }
            }

            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    for(String editString: editList){
                        int flag = updateInfoService.updateInfo(next, NMPL_STATIC_ROUTE, editString, SYSTEM_NM, createTime);
                        addNum = addNum +flag;
                    }
                }
            }

            log.info("RouteServiceImpl.updateStaticRoute：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("RouteServiceImpl.updateStaticRoute：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

//    /**
//     * 初始化路由
//     * @param centerRouteVos
//     */
//    @Override
//    @Transactional
//    public void initInfo(List<CenterRouteVo> centerRouteVos) {
//        List<NmplRoute> nmplRoutes = nmplRouteMapper.selectByExample(new NmplRouteExample());
//        if (CollectionUtils.isEmpty(nmplRoutes)){// 路由表为空，直接插入数据
//            Date createTime = new Date();
//            List<RouteVo> voList = new ArrayList<>(centerRouteVos.size());
//            for (CenterRouteVo vo:centerRouteVos){
//                RouteVo routeVo = new RouteVo();
//                BeanUtils.copyProperties(vo,routeVo);
//                routeVo.setUpdateTime(createTime);
//                voList.add(routeVo);
//            }
//            routeDomainService.insertRoute(voList);
//
//            // 通知基站有路由插入
//            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
//            updateInfo.setTableName(NMPL_ROUTE);
//            updateInfo.setOperationType(EDIT_TYPE_ADD);
//            updateInfo.setCreateTime(createTime);
//            updateInfo.setCreateUser(SYSTEM_NM);
//            nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
//        }else {// 路由表不为空，更新数据
//            List<RouteVo> updateVoList = new ArrayList<>();
//            List<RouteVo> insertVoList = new ArrayList<>();
//
//            Date updateTime = new Date();
//            for (CenterRouteVo vo:centerRouteVos){
//                boolean flag = true;
//                for (NmplRoute route:nmplRoutes){
//                    if (vo.getId().equals(String.valueOf(route.getId()))){
//                        RouteVo temp = new RouteVo();
//                        BeanUtils.copyProperties(vo,temp);
//                        temp.setUpdateTime(updateTime);
//                        updateVoList.add(temp);
//                        flag = false;
//                        break;
//                    }
//                }
//                if (flag){
//                    RouteVo temp = new RouteVo();
//                    BeanUtils.copyProperties(vo,temp);
//                    insertVoList.add(temp);
//                }
//            }
//
//            // 更新路由列表
//            routeDomainService.updateRoute(updateVoList);
//
//            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
//            updateInfo.setTableName(NMPL_ROUTE);
//            updateInfo.setOperationType(EDIT_TYPE_UPD);
//            updateInfo.setCreateTime(updateTime);
//            updateInfo.setCreateUser(SYSTEM_NM);
//            nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
//
//            // 新增路由列表
//            Date insertTime = new Date();
//            for (RouteVo vo:insertVoList){
//                vo.setUpdateTime(insertTime);
//            }
//            routeDomainService.insertRoute(insertVoList);
//
//            NmplUpdateInfoBase updateInfo2 = new NmplUpdateInfoBase();
//            updateInfo2.setTableName(NMPL_ROUTE);
//            updateInfo2.setOperationType(EDIT_TYPE_ADD);
//            updateInfo2.setCreateTime(insertTime);
//            updateInfo2.setCreateUser(SYSTEM_NM);
//            nmplUpdateInfoBaseMapper.insertSelective(updateInfo2);
//        }
//    }

    @Transactional
    @Override
    public void businessRouteInitInfo(List<NmplBusinessRouteVo> nmplBusinessRouteVo) {
        if (!CollectionUtils.isEmpty(nmplBusinessRouteVo)){
            Date createTime = new Date();
            String editType = EDIT_TYPE_ADD;
            for (NmplBusinessRouteVo businessRouteVo: nmplBusinessRouteVo){
                NmplBusinessRoute nmplBusinessRoute = new NmplBusinessRoute();
                BeanUtils.copyProperties(businessRouteVo,nmplBusinessRoute);
                nmplBusinessRoute.setUpdateTime(createTime);
                NmplBusinessRouteExample nmplBusinessRouteExample = new NmplBusinessRouteExample();
                NmplBusinessRouteExample.Criteria criteria = nmplBusinessRouteExample.createCriteria();
                criteria.andIdEqualTo(businessRouteVo.getId());
                List<NmplBusinessRoute> nmplBusinessRoutes = nmplBusinessRouteMapper.selectByExample(nmplBusinessRouteExample);
                if(nmplBusinessRoutes.size() > NumberUtils.INTEGER_ZERO){
                    nmplBusinessRouteMapper.updateByExampleSelective(nmplBusinessRoute,nmplBusinessRouteExample);
                    if (EDIT_TYPE_ADD.equals(editType)){
                        editType = EDIT_TYPE_UPD;
                    }
                }else {
                    nmplBusinessRouteMapper.insertSelective(nmplBusinessRoute);
                }
            }
            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_BUSINESS_ROUTE, editType, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }
            log.info("businessRoute  插入通知:{}",addNum);
        }

    }

    @Transactional
    @Override
    public void internetRouteInitInfo(List<NmplInternetRouteVo> nmplInternetRouteVo) {
        if (!CollectionUtils.isEmpty(nmplInternetRouteVo)){
            Date createTime = new Date();
            String editType = EDIT_TYPE_ADD;
            for (NmplInternetRouteVo internetRouteVo: nmplInternetRouteVo){
                NmplInternetRoute nmplInternetRoute = new NmplInternetRoute();
                BeanUtils.copyProperties(internetRouteVo,nmplInternetRoute);
                nmplInternetRoute.setUpdateTime(createTime);
                NmplInternetRouteExample nmplInternetRouteExample = new NmplInternetRouteExample();
                NmplInternetRouteExample.Criteria criteria = nmplInternetRouteExample.createCriteria();
                criteria.andIdEqualTo(nmplInternetRoute.getId());
                List<NmplInternetRoute> nmplInternetRoutes = nmplInternetRouteMapper.selectByExample(nmplInternetRouteExample);
                if(nmplInternetRoutes.size() > NumberUtils.INTEGER_ZERO){
                    nmplInternetRouteMapper.updateByExampleSelective(nmplInternetRoute,nmplInternetRouteExample);
                    if (EDIT_TYPE_ADD.equals(editType)){
                        editType = EDIT_TYPE_UPD;
                    }
                }else {
                    nmplInternetRouteMapper.insertSelective(nmplInternetRoute);
                }
            }
            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_INTERNET_ROUTE, editType, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }
            log.info("InternetRoute  插入通知:{}",addNum);
        }

    }

    @Transactional
    @Override
    public void staticRouteInitInfo(List<NmplStaticRouteVo> nmplStaticRouteVo) {
        if (!CollectionUtils.isEmpty(nmplStaticRouteVo)){
            Date createTime = new Date();
            String editType = EDIT_TYPE_ADD;
            for (NmplStaticRouteVo staticRouteVo: nmplStaticRouteVo){
                NmplStaticRoute nmplStaticRoute = new NmplStaticRoute();
                BeanUtils.copyProperties(staticRouteVo,nmplStaticRoute);
                nmplStaticRoute.setUpdateTime(createTime);
                NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
                NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
                criteria.andIdEqualTo(nmplStaticRoute.getId());
                List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
                if(nmplStaticRoutes.size() > NumberUtils.INTEGER_ZERO){
                    nmplStaticRouteMapper.updateByExampleSelective(nmplStaticRoute,nmplStaticRouteExample);
                    if (EDIT_TYPE_ADD.equals(editType)){
                        editType = EDIT_TYPE_UPD;
                    }
                }else {
                    nmplStaticRouteMapper.insertSelective(nmplStaticRoute);
                }
            }
            // 通知有路由信息变更
            Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
            int addNum = 0;
            if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
                Iterator<String> iterator = noticeDeviceTypes.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    int flag = updateInfoService.updateInfo(next, NMPL_STATIC_ROUTE, editType, SYSTEM_NM, createTime);
                    addNum = addNum +flag;
                }
            }
            log.info("StaticRoute  插入通知:{}",addNum);
        }
    }

}
