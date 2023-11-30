package com.matrictime.network.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.DataChangeUtil;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.DeviceInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.request.AddBaseStationInfoRequest;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;
import com.matrictime.network.request.InitInfoReq;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.UpdateInfoService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.constant.DataConstants.KEY_SPLIT;
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

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired
    private UpdateInfoService updateInfoService;



    /**
     * 基站插入
     * @param req
     * @return
     */
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

                // 插入本机基站信息
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int addlocal = nmplLocalBaseStationInfoMapper.insertSelective(stationInfo);

                int updateLocal = updateInfoService.updateInfo(infoVo.getStationType(),NMPL_LOCAL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);

                log.info("BaseStationInfoServiceImpl.addBaseStationInfo：" +
                                "addlocal:{},updateLocal:{}",
                        addlocal,updateLocal);
            }

            NmplLocalBaseStationInfoExample localBaseExample = new NmplLocalBaseStationInfoExample();
            localBaseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplLocalBaseStationInfo> localBaseStationInfoList = nmplLocalBaseStationInfoMapper.selectByExample(localBaseExample);
            int addTable = 0;

            for (NmplLocalBaseStationInfo info : localBaseStationInfoList){
                // 插入通知表更新
                addTable = updateInfoService.updateInfo(info.getStationType(),NMPL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
            }

            NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
            BeanUtils.copyProperties(infoVo,baseStationInfo);
            int addStation = baseStationInfoDomainService.insert(baseStationInfo);
            log.info("BaseStationInfoServiceImpl.addBaseStationInfo：addTable:{},addStation:{}",addTable,addStation);

        }catch (Exception e){
            log.error("BaseStationInfoServiceImpl.addBaseStationInfo：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }


    /**
     * 基站更新
     * @param infoVo
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateBaseStationInfo(BaseStationInfoVo infoVo) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            infoVo.setUpdateTime(createTime);
            if (infoVo.getIsLocal()){
                /* 本机基站信息更新 */
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                int local = nmplLocalBaseStationInfoMapper.updateByPrimaryKeySelective(stationInfo);

                int updateLocal = updateInfoService.updateInfo(infoVo.getStationType(),NMPL_LOCAL_BASE_STATION_INFO,EDIT_TYPE_UPD,SYSTEM_NM,createTime);

                log.info("BaseStationInfoServiceImpl.updateBaseStationInfo：local:{},updateLocal:{}",local,updateLocal);
            }

            /* 其他基站的推送更新 */

            NmplLocalBaseStationInfoExample localBaseExample = new NmplLocalBaseStationInfoExample();
            localBaseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplLocalBaseStationInfo> localBaseStationInfoList = nmplLocalBaseStationInfoMapper.selectByExample(localBaseExample);
            int updateTable = 0;

            for (NmplLocalBaseStationInfo info : localBaseStationInfoList){
                // 插入通知表更新
                updateTable = updateInfoService.updateInfo(info.getStationType(),NMPL_BASE_STATION_INFO,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
            }

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


    /**
     * 基站删除
     * @param request
     * @return
     */
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


    /**
     * 初始化本机数据
     * @param infoVo
     */
    @Override
    @Transactional
    public void initLocalInfo(CenterBaseStationInfoVo infoVo){

        List<NmplLocalBaseStationInfo> stationInfos = nmplLocalBaseStationInfoMapper.selectByExample(new NmplLocalBaseStationInfoExample());

        if (CollectionUtils.isEmpty(stationInfos)){// 本机没有基站数据
            // 插入本机基站信息
            Date createTime = new Date();
            NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
            BeanUtils.copyProperties(infoVo,stationInfo);
            stationInfo.setUpdateTime(createTime);
            int addlocal = nmplLocalBaseStationInfoMapper.insertSelective(stationInfo);

            int updateLocal = updateInfoService.updateInfo(infoVo.getStationType(),NMPL_LOCAL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);

        }else {// 本机有基站数据
            NmplLocalBaseStationInfo tempLocalBase = stationInfos.get(0);
            String stationStatus = tempLocalBase.getStationStatus();
            if (DeviceStatusEnum.NORMAL.getCode().equals(stationStatus)){// 本机基站状态是未激活

                // 直接删除数据重新载入即可
                nmplLocalBaseStationInfoMapper.deleteByExample(new NmplLocalBaseStationInfoExample());
                Date createTime = new Date();
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                stationInfo.setUpdateTime(createTime);
                int addlocal = nmplLocalBaseStationInfoMapper.insertSelective(stationInfo);

                int updateLocal = updateInfoService.updateInfo(infoVo.getStationType(),NMPL_LOCAL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);

            }else {// 本机基站状态是除了未激活，此时只能编辑部分信息

                // 更新本机基站信息
                Date createTime = new Date();
                NmplLocalBaseStationInfo stationInfo = new NmplLocalBaseStationInfo();
                BeanUtils.copyProperties(infoVo,stationInfo);
                stationInfo.setUpdateTime(createTime);
                nmplLocalBaseStationInfoMapper.updateByPrimaryKeySelective(stationInfo);

                int updateLocal = updateInfoService.updateInfo(infoVo.getStationType(),NMPL_LOCAL_BASE_STATION_INFO,EDIT_TYPE_UPD,SYSTEM_NM,createTime);

            }
        }
    }

    /**
     * 初始化列表数据
     * @param baseStationInfoList
     */
    @Override
    @Transactional
    public void initInfo(List<CenterBaseStationInfoVo> baseStationInfoList) {
        int delBase = nmplBaseStationInfoMapper.deleteByExample(new NmplBaseStationInfoExample());

        Date createTime = new Date();
        List<BaseStationInfoVo> baseStationInfoVos = new ArrayList<>(baseStationInfoList.size());
        for (CenterBaseStationInfoVo vo : baseStationInfoList){
            BaseStationInfoVo baseStationInfoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(vo,baseStationInfoVo);
            baseStationInfoVo.setUpdateTime(createTime);
            baseStationInfoVos.add(baseStationInfoVo);
        }
        int addCount = baseStationInfoDomainService.insertBaseStationInfo(baseStationInfoVos);


        NmplLocalBaseStationInfoExample localBaseExample = new NmplLocalBaseStationInfoExample();
        localBaseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplLocalBaseStationInfo> localBaseStationInfoList = nmplLocalBaseStationInfoMapper.selectByExample(localBaseExample);
        int updateTable = 0;

        for (NmplLocalBaseStationInfo info : localBaseStationInfoList){
            // 插入通知表更新
            updateTable = updateInfoService.updateInfo(info.getStationType(),NMPL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
        }
    }

}