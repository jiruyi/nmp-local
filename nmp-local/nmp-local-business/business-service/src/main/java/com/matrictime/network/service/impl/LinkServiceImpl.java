package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.enums.LinkEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.PortModel;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkDevice;
import com.matrictime.network.modelVo.LinkVo;
import com.matrictime.network.request.EditLinkReq;
import com.matrictime.network.request.QueryLinkReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LinkService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.KEY_SPLIT;
import static com.matrictime.network.constant.DataConstants.SUCCESS_MSG;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Slf4j
@Service
public class LinkServiceImpl extends SystemBaseService implements LinkService {

    @Resource
    private NmplLinkMapper nmplLinkMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Value("${asynservice.isremote}")
    private Integer isremote;

    @Value("${proxy.context-path}")
    private String proxyPath;

    @Value("${proxy.port}")
    private String proxyPort;

    @Autowired
    private AsyncService asyncService;

    @Override
    public Result<PageInfo<LinkVo>> queryLink(QueryLinkReq req) {
        Result result;
        try {
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            NmplLinkExample linkExample = new NmplLinkExample();
            NmplLinkExample.Criteria criteria = linkExample.createCriteria();
            criteria.andIsExistEqualTo(IS_EXIST);
            if (!ParamCheckUtil.checkVoStrBlank(req.getLinkName())){
                criteria.andLinkNameLike(KEY_PERCENT+req.getLinkName()+KEY_PERCENT);
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getLinkRelation())){
                criteria.andLinkRelationEqualTo(req.getLinkRelation());
            }
            // 根据条件查询链路列表
            List<NmplLink> linkList = nmplLinkMapper.selectByExample(linkExample);
            PageInfo<LinkVo> pageResult =  new PageInfo<>();
            List<LinkVo> linkVos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(linkList)){
                for (NmplLink link:linkList){
                    LinkVo vo = new LinkVo();
                    BeanUtils.copyProperties(link,vo);
                    // 查询链路中源设备和宿设备的详细信息
                    vo = getLinkVoDetail(vo);
                    linkVos.add(vo);
                }
            }
            pageResult.setList(linkVos);
            pageResult.setCount((int) page.getTotal());
            pageResult.setPages(page.getPages());
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("LinkServiceImpl.queryLink Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result editLink(EditLinkReq req) {
        Result result;
        try {
            // check param is legal
            checkEditLinkParam(req);
            switch (req.getEditType()){
                // 批量插入
                case DataConstants.EDIT_TYPE_ADD:
                    for (LinkVo vo : req.getLinkVos()){
                        NmplLink link = new NmplLink();
                        BeanUtils.copyProperties(vo,link);
                        nmplLinkMapper.insertSelective(link);
                        // 同步代理
                        try {
                            syncLink(vo);
                        }catch (Exception e){
                            log.error("LinkServiceImpl.editLink Exception:{}",e);
                        }
                    }
                    break;

                case DataConstants.EDIT_TYPE_UPD://批量修改
                case DataConstants.EDIT_TYPE_DEL:// 逻辑删除
                    for (LinkVo vo : req.getLinkVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("LinkVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplLink localLink = nmplLinkMapper.selectByPrimaryKey(vo.getId());
                        if (localLink == null){
                            throw new Exception("link"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }
                        NmplLink link = new NmplLink();
                        BeanUtils.copyProperties(vo,link);
                        nmplLinkMapper.updateByPrimaryKeySelective(link);
                        // 同步代理
                        try{
                            syncLink(vo);
                        }catch (Exception e){
                            log.error("LinkServiceImpl.editLink Exception:{}",e);
                        }

                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("LinkServiceImpl.editLink Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    private LinkVo getLinkVoDetail(LinkVo vo){
        if (LinkEnum.BB.getCode().equals(vo.getLinkRelation())){
            LinkDevice mainDeviceInfo = getLinkDevice(DeviceTypeEnum.STATION_BASE.getCode(), vo.getMainDeviceId());
            vo.setMainDeviceInfo(mainDeviceInfo);
        }if (LinkEnum.AK.getCode().equals(vo.getLinkRelation()) || LinkEnum.BK.getCode().equals(vo.getLinkRelation())){
            LinkDevice mainDeviceInfo = getLinkDevice(DeviceTypeEnum.STATION_BASE.getCode(), vo.getMainDeviceId());
            LinkDevice followDeviceInfo = getLinkDevice(DeviceTypeEnum.DEVICE_BASE.getCode(), vo.getFollowDeviceId());
            vo.setMainDeviceInfo(mainDeviceInfo);
            vo.setFollowDeviceInfo(followDeviceInfo);
        }if (LinkEnum.DB.getCode().equals(vo.getLinkRelation())){
            LinkDevice mainDeviceInfo = getLinkDevice(DeviceTypeEnum.DATA_BASE.getCode(), vo.getMainDeviceId());
            LinkDevice followDeviceInfo = getLinkDevice(DeviceTypeEnum.STATION_BASE.getCode(), vo.getFollowDeviceId());
            vo.setMainDeviceInfo(mainDeviceInfo);
            vo.setFollowDeviceInfo(followDeviceInfo);
        }
        return vo;
    }

    private LinkDevice getLinkDevice(String deviceType,String deviceId){
        LinkDevice linkDevice = new LinkDevice();
        if(DeviceTypeEnum.STATION_BASE.getCode().equals(deviceType)){
            NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
            example.createCriteria().andStationIdEqualTo(deviceId);
            List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(stationInfos)){
                NmplBaseStationInfo info = stationInfos.get(0);
                linkDevice.setDeviceName(info.getStationName());
                linkDevice.setPublicNetworkIp(info.getPublicNetworkIp());
                if (DeviceTypeEnum.STATION_BOUNDARY.getCode().equals(info.getStationType())){
                    String networkPort = info.getPublicNetworkPort();
                    linkDevice.setPublicNetworkPort(getBoundaryPublicNetworkPort(networkPort));
                }else {
                    linkDevice.setPublicNetworkPort(info.getPublicNetworkPort());
                }
                linkDevice.setLanIp(info.getLanIp());
                linkDevice.setLanPort(info.getLanPort());
            }
        }else {
            NmplDeviceInfoExample example = new NmplDeviceInfoExample();
            example.createCriteria().andDeviceIdEqualTo(deviceId);
            List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(deviceInfos)){
                NmplDeviceInfo info = deviceInfos.get(0);
                BeanUtils.copyProperties(info,linkDevice);
            }
        }
        return linkDevice;
    }

    /**
     * 获取兼容的边界基站ip
     * @param networkPort
     * @return
     */
    private String getBoundaryPublicNetworkPort(String networkPort){
        if (!ParamCheckUtil.checkVoStrBlank(networkPort) && networkPort.contains(KEY_SPLIT)){
            PortModel portModel = JSONObject.parseObject(networkPort, PortModel.class);
            List<String> signalingPort = portModel.getSignalingPort();
            if (!CollectionUtils.isEmpty(signalingPort)){
                return signalingPort.get(NumberUtils.INTEGER_ZERO);
            }
        }
        return networkPort;
    }

    /**
     * 同步链路（同步所有节点）
     * @param vo
     * @throws Exception
     */
    private void syncLink(LinkVo vo) throws Exception{
        Set<String> lanIps = new HashSet<>();
        // 查询所有基站代理节点并过滤重复ip
        NmplBaseStationInfoExample baseExample = new NmplBaseStationInfoExample();
        baseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(stationInfos)){
            for (NmplBaseStationInfo info:stationInfos){
                lanIps.add(info.getLanIp());
            }
        }

        // 查询所有设备代理节点并过滤重复ip
        NmplDeviceInfoExample deviceExample = new NmplDeviceInfoExample();
        deviceExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceExample);
        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplDeviceInfo info:deviceInfos){
                lanIps.add(info.getLanIp());
            }
        }

        // 同步所有代理节点
        if (!CollectionUtils.isEmpty(lanIps)){
            for (String lanIp : lanIps){
                String url = HttpClientUtil.getUrl(lanIp, proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                Map<String,String> map = new HashMap<>();
                map.put(KEY_DATA,JSONObject.toJSONString(vo));
                map.put(KEY_URL,url);
                asyncService.syncLink(map);
            }
        }
    }

//    /**
//     * 同步链路(只同步相关节点)
//     * @param newLink
//     * @param oldLink
//     * @param editType
//     * @throws Exception
//     */
//    private void syncLink(LinkVo newLink, NmplLink oldLink, String editType)throws Exception{
//        if (DataConstants.EDIT_TYPE_ADD.equals(editType) || DataConstants.EDIT_TYPE_DEL.equals(editType)){// 新增和删除都是直接通知两个节点即可
//            String relation = newLink.getLinkRelation();
//            // 除了数据采集外所有情况源设备都通知
//            NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
//            example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
//            List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
//            if (!CollectionUtils.isEmpty(stationInfos)){
//                String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
//                Map<String,String> map = new HashMap<>();
//                map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                map.put(KEY_URL,url);
//                asyncService.syncLink(map);
//            }
//            if (LinkEnum.BK.getCode().equals(relation) || LinkEnum.BK.getCode().equals(relation)){
//                // 宿设备为密钥中心时通知密钥中心
//                NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
//                deviceInfoExample.createCriteria().andDeviceIdEqualTo(newLink.getFollowDeviceId());
//                List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
//                if (!CollectionUtils.isEmpty(deviceInfos)){
//                    String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                    newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
//                    Map<String,String> map = new HashMap<>();
//                    map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                    map.put(KEY_URL,url);
//                    asyncService.syncLink(map);
//                }
//            }
//        }else if (DataConstants.EDIT_TYPE_UPD.equals(editType)){// 修改要区分多种情况
//            String relation = newLink.getLinkRelation();
//            if (LinkEnum.BB.getCode().equals(relation)){// 新链路关系为边界-边界
//                if (LinkEnum.BK.getCode().equals(oldLink.getLinkRelation()) || LinkEnum.AK.getCode().equals(oldLink.getLinkRelation())){// 从边界-密钥，接入-密钥修改为边界-边界，通知之前的节点删除链路
//                    NmplBaseStationInfoExample stationInfoExample = new NmplBaseStationInfoExample();
//                    stationInfoExample.createCriteria().andStationIdEqualTo(oldLink.getMainDeviceId());
//                    List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(stationInfoExample);
//                    if (!CollectionUtils.isEmpty(baseStationInfos)){
//                        String url = HttpClientUtil.getUrl(baseStationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                        newLink.setNoticeDeviceType(baseStationInfos.get(0).getStationType());
//                        LinkVo tempLink = new LinkVo();
//                        tempLink.setId(newLink.getId());
//                        tempLink.setIsExist(IS_NOT_EXIST);
//                        Map<String,String> map = new HashMap<>();
//                        map.put(KEY_DATA,JSONObject.toJSONString(tempLink));
//                        map.put(KEY_URL,url);
//                        asyncService.syncLink(map);
//                    }
//                    NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
//                    deviceInfoExample.createCriteria().andDeviceIdEqualTo(oldLink.getFollowDeviceId());
//                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
//                    if (!CollectionUtils.isEmpty(deviceInfos)){
//                        String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                        newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
//                        LinkVo tempLink = new LinkVo();
//                        tempLink.setId(newLink.getId());
//                        tempLink.setIsExist(IS_NOT_EXIST);
//                        Map<String,String> map = new HashMap<>();
//                        map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                        map.put(KEY_URL,url);
//                        asyncService.syncLink(map);
//                    }
//                }
//                // 边界-边界新节点更新链路信息
//                NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
//                example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
//                List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
//                if (!CollectionUtils.isEmpty(stationInfos)){
//                    String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                    newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
//                    Map<String,String> map = new HashMap<>();
//                    map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                    map.put(KEY_URL,url);
//                    asyncService.syncLink(map);
//                }
//            }else if (LinkEnum.BK.getCode().equals(relation) || LinkEnum.AK.getCode().equals(relation)){
//                // 判断源设备是否更改，若更改，删除老节点的链路信息
//                if (!newLink.getMainDeviceId().equals(oldLink.getMainDeviceId())){// 源设备被更改，同步删除修改前设备的链路
//                    NmplBaseStationInfoExample stationInfoExample = new NmplBaseStationInfoExample();
//                    stationInfoExample.createCriteria().andStationIdEqualTo(oldLink.getMainDeviceId());
//                    List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(stationInfoExample);
//                    if (!CollectionUtils.isEmpty(baseStationInfos)){
//                        String url = HttpClientUtil.getUrl(baseStationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                        newLink.setNoticeDeviceType(baseStationInfos.get(0).getStationType());
//                        LinkVo tempLink = new LinkVo();
//                        tempLink.setId(newLink.getId());
//                        tempLink.setIsExist(IS_NOT_EXIST);
//                        Map<String,String> map = new HashMap<>();
//                        map.put(KEY_DATA,JSONObject.toJSONString(tempLink));
//                        map.put(KEY_URL,url);
//                        asyncService.syncLink(map);
//                    }
//                }
//                if (!newLink.getFollowDeviceId().equals(oldLink.getFollowDeviceId()) && LinkEnum.BB.getCode().equals(oldLink.getLinkRelation())){// 宿设备被更改，同步删除修改前设备的链路
//                    // 判断宿设备是否更改，且老类型不能为边界-边界，因为边界-边界的宿设备没有链路信息，若更改，删除老节点的链路信息
//                    NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
//                    deviceInfoExample.createCriteria().andDeviceIdEqualTo(oldLink.getFollowDeviceId());
//                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
//                    if (!CollectionUtils.isEmpty(deviceInfos)){
//                        String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                        newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
//                        LinkVo tempLink = new LinkVo();
//                        tempLink.setId(newLink.getId());
//                        tempLink.setIsExist(IS_NOT_EXIST);
//                        Map<String,String> map = new HashMap<>();
//                        map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                        map.put(KEY_URL,url);
//                        asyncService.syncLink(map);
//                    }
//                }
//                // 源设备更新链路信息
//                NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
//                example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
//                List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
//                if (!CollectionUtils.isEmpty(stationInfos)){
//                    String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                    newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
//                    Map<String,String> map = new HashMap<>();
//                    map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                    map.put(KEY_URL,url);
//                    asyncService.syncLink(map);
//                }
//                // 宿设备为密钥中心时通知密钥中心
//                NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
//                deviceInfoExample.createCriteria().andDeviceIdEqualTo(newLink.getFollowDeviceId());
//                List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
//                if (!CollectionUtils.isEmpty(deviceInfos)){
//                    String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
//                    newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
//                    Map<String,String> map = new HashMap<>();
//                    map.put(KEY_DATA,JSONObject.toJSONString(newLink));
//                    map.put(KEY_URL,url);
//                    asyncService.syncLink(map);
//                }
//            }
//        }else {
//            throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
//        }
//    }

    private void checkEditLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getLinkVos())){
                throw new Exception("LinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

    }
}
