package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplLinkRelationMapper;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationSendVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.LinkRelationResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LinkRelationService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LinkRelationServiceImpl implements LinkRelationService {

    @Resource
    private LinkRelationDomainService linkRelationDomainService;

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Resource
    private AsyncService asyncService;

    @Resource
    private NmplLinkRelationMapper nmplLinkRelationMapper;

    @Override
    public Result<Integer> insertLinkRelation(LinkRelationRequest linkRelationRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        try {
            if(!CommonCheckUtil.checkStringLength(linkRelationRequest.getLinkName(),null,100)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            linkRelationRequest.setCreateTime(getFormatDate(date));
            linkRelationRequest.setUpdateTime(getFormatDate(date));
            linkRelationRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            int i = linkRelationDomainService.insertLinkRelation(linkRelationRequest);
            if(i == 2){
                return new Result<>(false,"链路名称或线路重复");
            }
            if(i == 1){
                sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_INSERT);
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("insertLinkRelation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public Result<Integer> deleteLinkRelation(LinkRelationRequest linkRelationRequest) {
        Result<Integer> result = new Result<>();
        try {
            int i = linkRelationDomainService.deleteLinkRelation(linkRelationRequest);
            result.setResultObj(i);
            result.setSuccess(true);
            if(i >= 1){
                sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_UPDATE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public Result<Integer> updateLinkRelation(LinkRelationRequest linkRelationRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        try {
            if(!CommonCheckUtil.checkStringLength(linkRelationRequest.getLinkName(),null,100)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            List<LinkRelationVo> list = nmplLinkRelationMapper.query(linkRelationRequest);
            if(list.get(0).getFollowDeviceId().equals(linkRelationRequest.getFollowDeviceId())){
                return new Result<>(true,"修改成功");
            }
            linkRelationRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            int i = linkRelationDomainService.updateLinkRelation(linkRelationRequest);
            if(i == 2){
                return new Result<>(false,"链路名称或线路重复");
            }
            if(i == 1){
                NmplDeviceInfoExample followDeviceInfoExample = new NmplDeviceInfoExample();
                NmplDeviceInfoExample.Criteria deviceInfoExampleCriteria = followDeviceInfoExample.createCriteria();
                deviceInfoExampleCriteria.andDeviceIdEqualTo(list.get(0).getFollowDeviceId());
                List<NmplDeviceInfo> followDeviceList = deviceInfoMapper.selectByExample(followDeviceInfoExample);
                //推送被修改之前的设备
                if(followDeviceList.size() > 0){
                    for (NmplDeviceInfo deviceInfo : followDeviceList) {
                        LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                        list.get(0).setIsExist("0");
                        BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                        linkRelationSendVo.setNoticeDeviceType(deviceInfo.getDeviceType());
                        Map<String,String> map = new HashMap<>();
                        map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                        String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+DataConstants.URL_LINK_RELATION_UPDATE;
                        map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                        map.put(DataConstants.KEY_URL,url);
                        asyncService.httpPush(map);
                    }
                }
                BaseStationInfoRequest followBaseStationInfoRequest = new BaseStationInfoRequest();
                followBaseStationInfoRequest.setStationId(list.get(0).getFollowDeviceId());
                List<BaseStationInfoVo> followBaseStationList =
                        baseStationInfoDomainService.selectForRoute(followBaseStationInfoRequest);
                if(followBaseStationList.size() > 0){
                    for (BaseStationInfoVo baseStationInfoVo : followBaseStationList) {
                        LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                        list.get(0).setIsExist("0");
                        BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                        linkRelationSendVo.setNoticeDeviceType(StationTypeEnum.BASE.getCode());
                        Map<String,String> map = new HashMap<>();
                        map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                        String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+DataConstants.URL_LINK_RELATION_UPDATE;
                        map.put(DataConstants.KEY_URL,url);
                        map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                        asyncService.httpPush(map);
                    }
                }
                sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_UPDATE);
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("updateLinkRelation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public Result<PageInfo<LinkRelationVo>> selectLinkRelation(LinkRelationRequest linkRelationRequest) {
        Result<PageInfo<LinkRelationVo>> result = new Result<>();
        try {
            PageInfo<LinkRelationVo> pageInfo = linkRelationDomainService.selectLinkRelation(linkRelationRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    //查询基站所有信息
    @Override
    public Result<BaseStationInfoResponse> selectLinkRelationStation(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
            List<BaseStationInfoVo> baseStationInfoVoList = linkRelationDomainService.selectLinkRelationStation(baseStationInfoRequest);
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoVoList);
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    //查询所有设备信息
    @Override
    public Result<DeviceResponse> selectLinkRelationDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            List<DeviceInfoVo> deviceInfoVoList = linkRelationDomainService.selectLinkRelationDevice(deviceInfoRequest);
            deviceResponse.setDeviceInfoVos(deviceInfoVoList);
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 多线程推送
     * @param linkRelationRequest
     * @throws Exception
     */
    private void sendLinkRelation(LinkRelationRequest linkRelationRequest,String suffix) throws Exception {
        List<LinkRelationVo> list;
        if(suffix.equals(DataConstants.URL_LINK_RELATION_INSERT)){
            list = nmplLinkRelationMapper.query(linkRelationRequest);
        }else {
            list = nmplLinkRelationMapper.selectById(linkRelationRequest);
        }
        //获取基站信息
        BaseStationInfoRequest mainBaseStationInfoRequest = new BaseStationInfoRequest();
        mainBaseStationInfoRequest.setStationId(list.get(0).getMainDeviceId());

        BaseStationInfoRequest followBaseStationInfoRequest = new BaseStationInfoRequest();
        followBaseStationInfoRequest.setStationId(list.get(0).getFollowDeviceId());

        //获取设备信息
        NmplDeviceInfoExample mainDeviceInfoExample = new NmplDeviceInfoExample();
        NmplDeviceInfoExample.Criteria criteria = mainDeviceInfoExample.createCriteria();
        criteria.andDeviceIdEqualTo(linkRelationRequest.getMainDeviceId());

        NmplDeviceInfoExample followDeviceInfoExample = new NmplDeviceInfoExample();
        NmplDeviceInfoExample.Criteria deviceInfoExampleCriteria = followDeviceInfoExample.createCriteria();
        deviceInfoExampleCriteria.andDeviceIdEqualTo(linkRelationRequest.getFollowDeviceId());

        List<NmplDeviceInfo> mainDeviceList = deviceInfoMapper.selectByExample(mainDeviceInfoExample);

        List<NmplDeviceInfo> followDeviceList = deviceInfoMapper.selectByExample(followDeviceInfoExample);

        List<BaseStationInfoVo> mainBaseStationList =
                baseStationInfoDomainService.selectForRoute(mainBaseStationInfoRequest);
        List<BaseStationInfoVo> followBaseStationList =
                baseStationInfoDomainService.selectForRoute(followBaseStationInfoRequest);

        if(mainDeviceList.size() > 0 && followDeviceList.size() > 0){
            List<NmplDeviceInfo> deviceInfoList = ListUtils.union(mainDeviceList,followDeviceList);

            //开启多线程
            for (NmplDeviceInfo deviceInfo : deviceInfoList) {
                LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                linkRelationSendVo.setNoticeDeviceType(deviceInfo.getDeviceType());
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                asyncService.httpPush(map);
            }
            return;
        }
        if(mainBaseStationList.size() > 0 && followBaseStationList.size() > 0){
            List<BaseStationInfoVo> unionList = ListUtils.union(mainBaseStationList,followBaseStationList);
            LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
            BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
            linkRelationSendVo.setNoticeDeviceType(StationTypeEnum.BASE.getCode());
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : unionList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                asyncService.httpPush(map);
            }
            return;
        }
        //主设备是基站
        if(mainBaseStationList.size() > 0 && followDeviceList.size() > 0){
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : mainBaseStationList) {
                LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                linkRelationSendVo.setNoticeDeviceType(StationTypeEnum.BASE.getCode());
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                asyncService.httpPush(map);
            }
            //开启多线程
            for (NmplDeviceInfo deviceInfo : followDeviceList) {
                LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                linkRelationSendVo.setNoticeDeviceType(deviceInfo.getDeviceType());
                Map<String,String> map = new HashMap<>();
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("linkRelation",linkRelationSendVo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                asyncService.httpPush(map);
            }
            return;
        }

        //主设备是device
        if(mainDeviceList.size() > 0 && followBaseStationList.size() > 0){
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : followBaseStationList) {
                LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                linkRelationSendVo.setNoticeDeviceType(StationTypeEnum.BASE.getCode());
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                asyncService.httpPush(map);
            }
            //开启多线程
            for (NmplDeviceInfo deviceInfo : mainDeviceList) {
                LinkRelationSendVo linkRelationSendVo = new LinkRelationSendVo();
                BeanUtils.copyProperties(list.get(0),linkRelationSendVo);
                linkRelationSendVo.setNoticeDeviceType(deviceInfo.getDeviceType());
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(linkRelationSendVo));
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                asyncService.httpPush(map);
            }
        }
        return;

    }





    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }
}