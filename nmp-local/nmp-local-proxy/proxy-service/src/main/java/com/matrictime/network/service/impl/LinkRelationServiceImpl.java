package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.LinkDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.service.LinkRelationService;
import com.matrictime.network.service.UpdateInfoService;
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

import static com.matrictime.network.base.constant.DataConstants.NMPL_LINK;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class LinkRelationServiceImpl extends SystemBaseService implements LinkRelationService {

    @Autowired
    private LinkDomainService linkDomainService;

    @Resource
    private NmplLinkMapper nmplLinkMapper;

    @Autowired
    private UpdateInfoService updateInfoService;

    private final Integer ADD_NUM = 1;

    private final Integer UPDATE_NUM = 2;

    /**
     * 新增链路
     * @param voList
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> addLink(List<LinkVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            Date createTime = new Date();
            Set<String> set = new HashSet();
            for (LinkVo infoVo : voList){
                infoVo.setUpdateTime(createTime);
                set.add(infoVo.getNoticeDeviceType());
            }

            // 插入链路关系
            int batchNum = linkDomainService.insertLink(voList);
            log.info("LinkRelationServiceImpl.addLinkRelation：batchNum:{}",batchNum);

            // 通知接入基站
            if (set.contains(DeviceTypeEnum.STATION_INSIDE.getCode())){
                int insideNum = updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                log.info("LinkRelationServiceImpl.addLinkRelation：insideNum:{}",insideNum);
            }

            // 通知边界基站
            if (set.contains(DeviceTypeEnum.STATION_BOUNDARY.getCode())){
                int boundaryNum = updateInfoService.updateInfo(DeviceTypeEnum.STATION_BOUNDARY.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                log.info("LinkRelationServiceImpl.addLinkRelation：boundaryNum:{}",boundaryNum);
            }

            // 通知密钥中心
            if (set.contains(DeviceTypeEnum.DEVICE_DISPENSER.getCode())){
                int keycenterNum = updateInfoService.updateInfo(DeviceTypeEnum.DEVICE_DISPENSER.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                log.info("LinkRelationServiceImpl.addLinkRelation：keycenterNum:{}",keycenterNum);
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
     * @param voList
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateLink(List<LinkVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            List<LinkVo> addVos = new ArrayList<>();
            List<LinkVo> updateVos = new ArrayList<>();
            Date createTime = new Date();
            Map<String,Integer> opMap = new HashMap<>();
            Set<Long> ids = new HashSet<>();
            for (int i=0; i<voList.size();i++){
                LinkVo infoVo = voList.get(i);
                Long id = infoVo.getId();
                infoVo.setUpdateTime(createTime);
                NmplLink link = nmplLinkMapper.selectByPrimaryKey(infoVo.getId());
                if (link != null){
                    if (!ids.contains(id)){
                        updateVos.add(infoVo);
                    }
                    if (opMap.containsKey(infoVo.getNoticeDeviceType())){
                        opMap.put(infoVo.getNoticeDeviceType(),opMap.get(infoVo.getNoticeDeviceType())+UPDATE_NUM);
                    }else {
                        opMap.put(infoVo.getNoticeDeviceType(),UPDATE_NUM);
                    }
                }else {
                    if (!ids.contains(id)) {
                        addVos.add(infoVo);
                    }
                    if (opMap.containsKey(infoVo.getNoticeDeviceType())){
                        opMap.put(infoVo.getNoticeDeviceType(),opMap.get(infoVo.getNoticeDeviceType())+ADD_NUM);
                    }else {
                        opMap.put(infoVo.getNoticeDeviceType(),ADD_NUM);
                    }
                }
                ids.add(id);
            }

            // 更新链路关系
            Integer batchNum=0;
            if (!CollectionUtils.isEmpty(addVos)){
                batchNum = batchNum + linkDomainService.insertLink(addVos);
            }
            if (!CollectionUtils.isEmpty(updateVos)){
                batchNum = batchNum + linkDomainService.updateLink(updateVos);
            }
            log.info("LinkRelationServiceImpl.updateLink：batchNum:{}",batchNum);

            // 通知接入基站
            if (opMap.containsKey(DeviceTypeEnum.STATION_INSIDE.getCode())){
                Integer opValue = opMap.get(DeviceTypeEnum.STATION_INSIDE.getCode());
                int insideNum = 0;
                if (opValue.equals(UPDATE_NUM)){
                    insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }else if (opValue.equals(ADD_NUM)){
                    insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                }else if (opValue.equals(UPDATE_NUM+ADD_NUM)){
                    insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                    insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }
                log.info("LinkRelationServiceImpl.updateLink：insideNum:{}",insideNum);
            }

            // 通知边界基站
            if (opMap.containsKey(DeviceTypeEnum.STATION_BOUNDARY.getCode())){
                Integer opValue = opMap.get(DeviceTypeEnum.STATION_BOUNDARY.getCode());
                int boundaryNum = 0;
                if (opValue.equals(UPDATE_NUM)){
                    boundaryNum = boundaryNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_BOUNDARY.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }else if (opValue.equals(ADD_NUM)){
                    boundaryNum = boundaryNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_BOUNDARY.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                }else if (opValue.equals(UPDATE_NUM+ADD_NUM)){
                    boundaryNum = boundaryNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_BOUNDARY.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                    boundaryNum = boundaryNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_BOUNDARY.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }
                log.info("LinkRelationServiceImpl.updateLink：boundaryNum:{}",boundaryNum);
            }

            // 通知密钥中心
            if (opMap.containsKey(DeviceTypeEnum.DEVICE_DISPENSER.getCode())){
                Integer opValue = opMap.get(DeviceTypeEnum.DEVICE_DISPENSER.getCode());
                int keycenterNum = 0;
                if (opValue.equals(UPDATE_NUM)){
                    keycenterNum = keycenterNum + updateInfoService.updateInfo(DeviceTypeEnum.DEVICE_DISPENSER.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }else if (opValue.equals(ADD_NUM)){
                    keycenterNum = keycenterNum + updateInfoService.updateInfo(DeviceTypeEnum.DEVICE_DISPENSER.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                }else if (opValue.equals(UPDATE_NUM+ADD_NUM)){
                    keycenterNum = keycenterNum + updateInfoService.updateInfo(DeviceTypeEnum.DEVICE_DISPENSER.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                    keycenterNum = keycenterNum + updateInfoService.updateInfo(DeviceTypeEnum.DEVICE_DISPENSER.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }
                log.info("LinkRelationServiceImpl.updateLink：keycenterNum:{}",keycenterNum);
            }
            buildResult(batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.updateLink：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 链路初始化
     * @param linkVos
     */
    @Override
    @Transactional
    public void initInfo(List<LinkVo> linkVos) {
        List<NmplLink> nmplLinks = nmplLinkMapper.selectByExample(new NmplLinkExample());
        if (CollectionUtils.isEmpty(nmplLinks)){// 链路表为空，直接插入数据
            updateLink(linkVos);
        }else {// 链路表不为空，更新数据
            List<Long> ids = new ArrayList<>();
            List<Long> linkVoIds = new ArrayList<>();
            for (LinkVo vo: linkVos){
                linkVoIds.add(vo.getId());
            }
            for (NmplLink vo:nmplLinks){
                if (linkVoIds.contains(vo.getId())){
                    ids.add(vo.getId());
                }
            }
            NmplLinkExample example = new NmplLinkExample();
            example.createCriteria().andIdNotIn(ids);
            List<NmplLink> localDelLinks = nmplLinkMapper.selectByExample(example);
            for (NmplLink link : localDelLinks){
                LinkVo temp = new LinkVo();
                BeanUtils.copyProperties(link,temp);
                temp.setIsExist(IS_NOT_EXIST);
                linkVos.add(temp);
            }
            updateLink(linkVos);
        }
    }
}
