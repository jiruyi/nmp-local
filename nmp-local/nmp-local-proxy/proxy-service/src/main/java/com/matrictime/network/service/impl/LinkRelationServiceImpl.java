package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterLinkRelationVo;
import com.matrictime.network.modelVo.CenterRouteVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.service.LinkRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    /**
     * 新增链路
     * @param voList
     * @return
     */
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

            // 插入链路关系
            int batchNum = linkRelationDomainService.insertLinkRelation(voList);
            log.info("LinkRelationServiceImpl.addLinkRelation：batchNum:{}",batchNum);

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
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.addLinkRelation：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 更新链路
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateLinkRelation(LinkRelationVo req) {
        Result result = new Result<>();
        try {
            Date createTime = new Date();
            req.setUpdateTime(createTime);
            String noticeDeviceType = req.getNoticeDeviceType();

            NmplLinkRelation nmplLinkRelation = nmplLinkRelationMapper.selectByPrimaryKey(req.getId());

            // 更新链路关系
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

            // 通知基站
            if (StationTypeEnum.BASE.getCode().equals(noticeDeviceType)){
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
            if (DeviceTypeEnum.DISPENSER.getCode().equals(noticeDeviceType)){
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
            if (DeviceTypeEnum.GENERATOR.getCode().equals(noticeDeviceType)){
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
            if (DeviceTypeEnum.CACHE.getCode().equals(noticeDeviceType)){
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
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.updateLinkRelation：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 链路初始化
     * @param linkRelationVos
     */
    @Override
    @Transactional
    public void initInfo(List<CenterLinkRelationVo> linkRelationVos) {
        List<NmplLinkRelation> nmplLinkRelations = nmplLinkRelationMapper.selectByExample(new NmplLinkRelationExample());
        if (CollectionUtils.isEmpty(nmplLinkRelations)){// 链路表为空，直接插入数据
            List<LinkRelationVo> voList = new ArrayList<>(linkRelationVos.size());
            for (CenterLinkRelationVo vo:linkRelationVos){
                LinkRelationVo temp = new LinkRelationVo();
                BeanUtils.copyProperties(vo,temp);
            }
            addLinkRelation(voList);
        }else {// 链路表不为空，更新数据
            for (CenterLinkRelationVo vo:linkRelationVos){
                LinkRelationVo req = new LinkRelationVo();
                BeanUtils.copyProperties(vo,req);
                updateLinkRelation(req);
            }
        }
    }
}
