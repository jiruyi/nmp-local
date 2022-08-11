package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.AddLinkRelationRequest;
import com.matrictime.network.request.UpdateLinkRelationRequest;
import com.matrictime.network.service.LinkRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_LINK_RELATION;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class LinkRelationServiceImpl extends SystemBaseService implements LinkRelationService {

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Autowired
    private LinkRelationDomainService linkRelationDomainService;

    @Override
    @Transactional
    public Result<Integer> addLinkRelation(List<LinkRelationVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            for (LinkRelationVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
            }
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_LINK_RELATION);
            updateInfo.setOperationType(EDIT_TYPE_ADD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            int batchNum = linkRelationDomainService.insertLinkRelation(voList);
            log.info("LinkRelationServiceImpl.addLinkRelation：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.addLinkRelation：{}",e.getMessage());
            result = failResult(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> updateLinkRelation(List<LinkRelationVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            for (LinkRelationVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
            }
            NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
            updateInfo.setTableName(NMPL_LINK_RELATION);
            updateInfo.setOperationType(EDIT_TYPE_UPD);
            updateInfo.setCreateTime(createTime);
            updateInfo.setCreateUser(SYSTEM_NM);
            int addNum = nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
            int batchNum = linkRelationDomainService.updateLinkRelation(voList);
            log.info("LinkRelationServiceImpl.updateLinkRelation：addNum:{},batchNum:{}",addNum,batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.updateLinkRelation：{}",e.getMessage());
            result = failResult(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
