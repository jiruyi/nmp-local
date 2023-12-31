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
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    private NmplLocalDeviceInfoMapper localDeviceInfoMapper;

    @Resource
    private  NmplLocalBaseStationInfoMapper localBaseStationInfoMapper;

    private final Integer ADD_NUM = 1;

    private final Integer UPDATE_NUM = 2;

    @Override
    @Transactional
    public Result<Integer>
    updateLink(List<ProxyLinkVo> voList) {
        Result result = new Result<>();
        try {
            if (CollectionUtils.isEmpty(voList)){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }

            Date createTime = new Date();
            Map<String,Integer> opMap = new HashMap<>();
            Integer batchNum=0;
            // 更新本机链路表
            batchNum = updateLocalLink(voList,batchNum,opMap,createTime);

            // 插入各设备的通知表
            noticeUpdate(opMap,createTime);
            buildResult(batchNum);
        }catch (Exception e){
            log.error("LinkRelationServiceImpl.updateLink：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 更新本机链路表
     * @param voList
     * @param batchNum
     * @param opMap
     * @param createTime
     * @return
     */
    private Integer updateLocalLink(List<ProxyLinkVo> voList, Integer batchNum, Map<String,Integer> opMap, Date createTime){
        List<ProxyLinkVo> addVos = new ArrayList<>();
        List<ProxyLinkVo> updateVos = new ArrayList<>();
        Integer noticeType = ADD_NUM;
        for (int i=0; i<voList.size();i++){
            ProxyLinkVo infoVo = voList.get(i);
            infoVo.setUpdateTime(createTime);
            NmplLink link = nmplLinkMapper.selectByPrimaryKey(infoVo.getId());
            if (link != null){// 链路已存在，则做更新通知
                updateVos.add(infoVo);
                // 逻辑删除的时候可能没有mainDeviceId和followDeviceId
//                if (ParamCheckUtil.checkVoStrBlank(infoVo.getMainDeviceId())){
//                    infoVo.setMainDeviceId(link.getMainDeviceId());
//                }
//                if (ParamCheckUtil.checkVoStrBlank(infoVo.getFollowDeviceId())){
//                    infoVo.setFollowDeviceId(link.getFollowDeviceId());
//                }
//                Set<String> noticeDeviceType = getNoticeDeviceType(infoVo);
//                for (String notice : noticeDeviceType){
//                    if (!opMap.containsKey(notice)) {
//                        opMap.put(notice, UPDATE_NUM);
//                    }
//                }
                if (ADD_NUM.equals(noticeType)){
                    noticeType = UPDATE_NUM;
                }
            }else {// 链路不存在，则做新增通知
                addVos.add(infoVo);
//                Set<String> noticeDeviceType = getNoticeDeviceType(infoVo);
//                for (String notice : noticeDeviceType){
//                    if (!opMap.containsKey(notice)){
//                        opMap.put(notice,ADD_NUM);
//                    }
//                }
            }
        }

        // 更新链路关系
        if (!CollectionUtils.isEmpty(addVos)){
            batchNum = batchNum + linkDomainService.insertLink(addVos);
        }
        if (!CollectionUtils.isEmpty(updateVos)){
            batchNum = batchNum + linkDomainService.updateLink(updateVos);
        }
        log.info("LinkRelationServiceImpl.updateLocalLink：batchNum:{}",batchNum);

        // 获取变更通知列表信息
        Set<String> noticeDeviceTypes = updateInfoService.getNoticeDeviceTypes();
        if (!CollectionUtils.isEmpty(noticeDeviceTypes)){
            Iterator<String> iterator = noticeDeviceTypes.iterator();
            while (iterator.hasNext()){
                String noticeDeviceType = iterator.next();
                opMap.put(noticeDeviceType,noticeType);
            }
        }

        return batchNum;
    }

    /**
     * 插入各设备的通知表
     * @param opMap
     */
    private void noticeUpdate(Map<String,Integer> opMap,Date createTime){
        // 通知接入基站
        if (opMap.containsKey(DeviceTypeEnum.STATION_INSIDE.getCode())){
            Integer opValue = opMap.get(DeviceTypeEnum.STATION_INSIDE.getCode());
            int insideNum = 0;
            if (opValue.equals(UPDATE_NUM)){
                insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
            }else if (opValue.equals(ADD_NUM)){
                insideNum = insideNum + updateInfoService.updateInfo(DeviceTypeEnum.STATION_INSIDE.getCode(),NMPL_LINK,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
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
            }
            log.info("LinkRelationServiceImpl.updateLink：keycenterNum:{}",keycenterNum);
        }
    }

    /**
     * 根据链路获取通知设备类型列表
     * @param proxyLinkVo
     * @return
     */
    private Set<String> getNoticeDeviceType(ProxyLinkVo proxyLinkVo){
        Set<String> resultSet = new HashSet<>();
        NmplLocalBaseStationInfoExample baseExample = new NmplLocalBaseStationInfoExample();
        baseExample.createCriteria().andStationIdEqualTo(proxyLinkVo.getMainDeviceId()).andIsExistEqualTo(IS_EXIST);
        List<NmplLocalBaseStationInfo> stationInfos = localBaseStationInfoMapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(stationInfos)){
            for (NmplLocalBaseStationInfo info : stationInfos){
                resultSet.add(info.getStationType());
            }
        }
        return resultSet;
    }

    /**
     * 链路初始化(只同步相关节点)
     * @param linkVos
     */
//    @Override
//    @Transactional
//    public void initInfo(List<LinkVo> linkVos) {
//        List<NmplLink> nmplLinks = nmplLinkMapper.selectByExample(new NmplLinkExample());
//        if (CollectionUtils.isEmpty(nmplLinks)){// 链路表为空，直接插入数据
//            updateLink(linkVos);
//        }else {// 链路表不为空，更新数据
//            List<Long> ids = new ArrayList<>();
//            List<Long> linkVoIds = new ArrayList<>();
//            for (LinkVo vo: linkVos){
//                linkVoIds.add(vo.getId());
//            }
//            for (NmplLink vo:nmplLinks){
//                if (linkVoIds.contains(vo.getId())){
//                    ids.add(vo.getId());
//                }
//            }
//            NmplLinkExample example = new NmplLinkExample();
//            example.createCriteria().andIdNotIn(ids);
//            List<NmplLink> localDelLinks = nmplLinkMapper.selectByExample(example);
//            for (NmplLink link : localDelLinks){
//                LinkVo temp = new LinkVo();
//                BeanUtils.copyProperties(link,temp);
//                temp.setIsExist(IS_NOT_EXIST);
//                linkVos.add(temp);
//            }
//            updateLink(linkVos);
//        }
//    }

    /**
     * 链路初始化（全量同步，只通知本地相关设备）
     * @param ProxyLinkVos
     */
    @Override
    @Transactional
    public void initInfo(List<ProxyLinkVo> ProxyLinkVos) {
        if (!CollectionUtils.isEmpty(ProxyLinkVos)){
            updateLink(ProxyLinkVos);
        }
    }
}
