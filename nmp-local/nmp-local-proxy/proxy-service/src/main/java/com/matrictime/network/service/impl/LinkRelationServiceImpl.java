package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.service.LinkRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.NMPL_LINK_RELATION;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class LinkRelationServiceImpl extends SystemBaseService implements LinkRelationService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Resource
    private NmplUpdateInfoKeycenterMapper nmplUpdateInfoKeycenterMapper;

    @Resource
    private NmplUpdateInfoGeneratorMapper nmplUpdateInfoGeneratorMapper;

    @Resource
    private NmplUpdateInfoCacheMapper nmplUpdateInfoCacheMapper;

    @Autowired
    private LinkRelationDomainService linkRelationDomainService;

    @Resource
    private NmplLinkRelationMapper nmplLinkRelationMapper;

    @Override
    @Transactional
    public Result<Integer> addLinkRelation(List<LinkRelationVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            Set<String> set = new HashSet();
            for (LinkRelationVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
                set.add(infoVo.getNoticeDeviceType());
            }

            // 通知基站
            if (set.contains(StationTypeEnum.BASE.getCode())){
                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int baseNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.addLinkRelation：baseNum:{}",baseNum);
            }

            // 通知秘钥中心
            if (set.contains(DeviceTypeEnum.DISPENSER.getCode())){
                NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int keycenterNum = nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.addLinkRelation：keycenterNum:{}",keycenterNum);
            }

            // 通知生成机
            if (set.contains(DeviceTypeEnum.GENERATOR.getCode())){
                NmplUpdateInfoGenerator updateInfo = new NmplUpdateInfoGenerator();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int generatorNum = nmplUpdateInfoGeneratorMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.addLinkRelation：generatorNum:{}",generatorNum);
            }

            // 通知缓存机
            if (set.contains(DeviceTypeEnum.CACHE.getCode())){
                NmplUpdateInfoCache updateInfo = new NmplUpdateInfoCache();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                updateInfo.setOperationType(EDIT_TYPE_ADD);
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int cacheNum = nmplUpdateInfoCacheMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.addLinkRelation：cacheNum:{}",cacheNum);
            }

            int batchNum = linkRelationDomainService.insertLinkRelation(voList);
            log.info("LinkRelationServiceImpl.addLinkRelation：batchNum:{}",batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.addLinkRelation：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateLinkRelation(LinkRelationVo req) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            Set<String> set = new HashSet();
            req.setUpdateTime(createTime);
            set.add(req.getNoticeDeviceType());

            NmplLinkRelation nmplLinkRelation = nmplLinkRelationMapper.selectByPrimaryKey(req.getId());



            // 通知基站
            if (set.contains(StationTypeEnum.BASE.getCode())){
                NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                if (nmplLinkRelation != null){
                    updateInfo.setOperationType(EDIT_TYPE_UPD);
                }else {
                    updateInfo.setOperationType(EDIT_TYPE_ADD);
                }

                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int baseNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.updateLinkRelation：baseNum:{}",baseNum);
            }

            // 通知秘钥中心
            if (set.contains(DeviceTypeEnum.DISPENSER.getCode())){
                NmplUpdateInfoKeycenter updateInfo = new NmplUpdateInfoKeycenter();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                if (nmplLinkRelation != null){
                    updateInfo.setOperationType(EDIT_TYPE_UPD);
                }else {
                    updateInfo.setOperationType(EDIT_TYPE_ADD);
                }
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int keycenterNum = nmplUpdateInfoKeycenterMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.updateLinkRelation：keycenterNum:{}",keycenterNum);
            }

            // 通知生成机
            if (set.contains(DeviceTypeEnum.GENERATOR.getCode())){
                NmplUpdateInfoGenerator updateInfo = new NmplUpdateInfoGenerator();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                if (nmplLinkRelation != null){
                    updateInfo.setOperationType(EDIT_TYPE_UPD);
                }else {
                    updateInfo.setOperationType(EDIT_TYPE_ADD);
                }
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int generatorNum = nmplUpdateInfoGeneratorMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.updateLinkRelation：generatorNum:{}",generatorNum);
            }

            // 通知缓存机
            if (set.contains(DeviceTypeEnum.CACHE.getCode())){
                NmplUpdateInfoCache updateInfo = new NmplUpdateInfoCache();
                updateInfo.setTableName(NMPL_LINK_RELATION);
                if (nmplLinkRelation != null){
                    updateInfo.setOperationType(EDIT_TYPE_UPD);
                }else {
                    updateInfo.setOperationType(EDIT_TYPE_ADD);
                }
                updateInfo.setCreateTime(createTime);
                updateInfo.setCreateUser(SYSTEM_NM);
                int cacheNum = nmplUpdateInfoCacheMapper.insertSelective(updateInfo);
                log.info("LinkRelationServiceImpl.updateLinkRelation：cacheNum:{}",cacheNum);
            }
            int batchNum=0;
            if (nmplLinkRelation != null){
                List<LinkRelationVo> voList = new ArrayList<>(1);
                voList.add(req);
                batchNum = linkRelationDomainService.updateLinkRelation(voList);
            }else {
                List<LinkRelationVo> voList = new ArrayList<>(1);
                voList.add(req);
                batchNum = linkRelationDomainService.insertLinkRelation(voList);
            }

            log.info("LinkRelationServiceImpl.updateLinkRelation：batchNum:{}",batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.updateLinkRelation：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
