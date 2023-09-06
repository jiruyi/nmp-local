package com.matrictime.network.service.impl;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
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
                        syncLink(vo,null,req.getEditType());
                    }
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
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
                        syncLink(vo,localLink,req.getEditType());
                    }
                    break;
                // 批量删除(暂未使用)
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplLinkMapper.deleteByPrimaryKey(id);
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
                linkDevice.setPublicNetworkPort(info.getPublicNetworkPort());
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

    private void syncLink(LinkVo newLink, NmplLink oldLink, String editType)throws Exception{
        if (DataConstants.EDIT_TYPE_ADD.equals(editType) || DataConstants.EDIT_TYPE_DEL.equals(editType)){
            String relation = newLink.getLinkRelation();
            // 除了数据采集外所有情况源设备都通知
            NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
            example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
            List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(stationInfos)){
                String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
                syncProxy(JSONObject.toJSONString(newLink),url);
            }
            if (LinkEnum.BK.getCode().equals(relation) || LinkEnum.BK.getCode().equals(relation)){
                // 宿设备为密钥中心时通知密钥中心
                NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
                deviceInfoExample.createCriteria().andDeviceIdEqualTo(newLink.getMainDeviceId());
                List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
                if (!CollectionUtils.isEmpty(deviceInfos)){
                    String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                    newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
                    syncProxy(JSONObject.toJSONString(newLink),url);
                }
            }
        }else if (DataConstants.EDIT_TYPE_UPD.equals(editType)){
            String relation = newLink.getLinkRelation();
            if (LinkEnum.BB.getCode().equals(relation)){
                if (LinkEnum.BK.getCode().equals(oldLink.getLinkRelation()) || LinkEnum.BK.getCode().equals(oldLink.getLinkRelation())){// 从边界-密钥，接入-密钥修改为边界-边界，通知之前的节点删除链路
                    NmplBaseStationInfoExample stationInfoExample = new NmplBaseStationInfoExample();
                    stationInfoExample.createCriteria().andStationIdEqualTo(oldLink.getMainDeviceId());
                    List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(stationInfoExample);
                    if (!CollectionUtils.isEmpty(baseStationInfos)){
                        String url = HttpClientUtil.getUrl(baseStationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                        newLink.setNoticeDeviceType(baseStationInfos.get(0).getStationType());
                        LinkVo tempLink = new LinkVo();
                        tempLink.setId(newLink.getId());
                        tempLink.setIsExist(IS_NOT_EXIST);
                        syncProxy(JSONObject.toJSONString(tempLink),url);
                    }
                    NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
                    deviceInfoExample.createCriteria().andDeviceIdEqualTo(oldLink.getFollowDeviceId());
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
                    if (!CollectionUtils.isEmpty(deviceInfos)){
                        String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                        newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
                        LinkVo tempLink = new LinkVo();
                        tempLink.setId(newLink.getId());
                        tempLink.setIsExist(IS_NOT_EXIST);
                        syncProxy(JSONObject.toJSONString(newLink),url);
                    }
                }
                // 边界-边界新节点更新链路信息
                NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
                example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
                List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(stationInfos)){
                    String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                    newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
                    syncProxy(JSONObject.toJSONString(newLink),url);
                }
            }else if (LinkEnum.BK.getCode().equals(relation) || LinkEnum.BK.getCode().equals(relation)){
                // 判断源设备是否更改，若更改，删除老节点的链路信息
                if (!newLink.getMainDeviceId().equals(oldLink.getMainDeviceId())){// 源设备被更改，同步删除修改前设备的链路
                    NmplBaseStationInfoExample stationInfoExample = new NmplBaseStationInfoExample();
                    stationInfoExample.createCriteria().andStationIdEqualTo(oldLink.getMainDeviceId());
                    List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(stationInfoExample);
                    if (!CollectionUtils.isEmpty(baseStationInfos)){
                        String url = HttpClientUtil.getUrl(baseStationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                        newLink.setNoticeDeviceType(baseStationInfos.get(0).getStationType());
                        LinkVo tempLink = new LinkVo();
                        tempLink.setId(newLink.getId());
                        tempLink.setIsExist(IS_NOT_EXIST);
                        syncProxy(JSONObject.toJSONString(tempLink),url);
                    }
                }
                if (!newLink.getFollowDeviceId().equals(oldLink.getFollowDeviceId()) && LinkEnum.BB.getCode().equals(oldLink.getLinkRelation())){// 宿设备被更改，同步删除修改前设备的链路
                    // 判断宿设备是否更改，且老类型不能为边界-边界，因为边界-边界的宿设备没有链路信息，若更改，删除老节点的链路信息
                    NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
                    deviceInfoExample.createCriteria().andDeviceIdEqualTo(oldLink.getFollowDeviceId());
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
                    if (!CollectionUtils.isEmpty(deviceInfos)){
                        String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                        newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
                        LinkVo tempLink = new LinkVo();
                        tempLink.setId(newLink.getId());
                        tempLink.setIsExist(IS_NOT_EXIST);
                        syncProxy(JSONObject.toJSONString(newLink),url);
                    }
                }
                // 源设备更新链路信息
                NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
                example.createCriteria().andStationIdEqualTo(newLink.getMainDeviceId());
                List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(stationInfos)){
                    String url = HttpClientUtil.getUrl(stationInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                    newLink.setNoticeDeviceType(stationInfos.get(0).getStationType());
                    syncProxy(JSONObject.toJSONString(newLink),url);
                }
                // 宿设备为密钥中心时通知密钥中心
                NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
                deviceInfoExample.createCriteria().andDeviceIdEqualTo(newLink.getMainDeviceId());
                List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
                if (!CollectionUtils.isEmpty(deviceInfos)){
                    String url = HttpClientUtil.getUrl(deviceInfos.get(0).getLanIp(), proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                    newLink.setNoticeDeviceType(deviceInfos.get(0).getDeviceType());
                    syncProxy(JSONObject.toJSONString(newLink),url);
                }
            }
        }else {
            throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
        }
    }

    private boolean syncProxy(String jsonString,String url){
        boolean flag = false;
        String post = "";
        if (isremote == 1){
            try {
                post = HttpClientUtil.post(url, jsonString);
            } catch (IOException e) {
                log.warn("LinkServiceImpl.syncProxy IOException:{}",e.getMessage());
            }
        }else {
            Result tempResult = new Result(true,"");
            post = JSONObject.toJSONString(tempResult);
        }
        log.info("LinkServiceImpl.syncProxy result url:{},req:{},post:{}",url,jsonString,post);
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject != null && jsonObject.containsKey(SUCCESS_MSG)){
            flag = (Boolean) jsonObject.get(SUCCESS_MSG);
        }
        return flag;
    }

    private void checkEditLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getLinkVos())){
                throw new Exception("LinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("DelIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
