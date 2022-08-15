package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
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
            sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_INSERT);
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
            result.setResultObj(linkRelationDomainService.deleteLinkRelation(linkRelationRequest));
            result.setSuccess(true);
            //sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_DELETE);
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
            linkRelationRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            int i = linkRelationDomainService.updateLinkRelation(linkRelationRequest);
            if(i == 2){
                return new Result<>(false,"链路名称或线路重复");
            }
            sendLinkRelation(linkRelationRequest,DataConstants.URL_LINK_RELATION_UPDATE);
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
        //获取基站信息
        BaseStationInfoRequest mainBaseStationInfoRequest = new BaseStationInfoRequest();
        mainBaseStationInfoRequest.setStationId(linkRelationRequest.getMainDeviceId());

        BaseStationInfoRequest followBaseStationInfoRequest = new BaseStationInfoRequest();
        followBaseStationInfoRequest.setStationId(linkRelationRequest.getFollowDeviceId());

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
                baseStationInfoDomainService.selectLinkBaseStationInfo(mainBaseStationInfoRequest);
        List<BaseStationInfoVo> followBaseStationList =
                baseStationInfoDomainService.selectLinkBaseStationInfo(followBaseStationInfoRequest);

        if(mainDeviceList.size() > 0 && followDeviceList.size() > 0){
            List<NmplDeviceInfo> deviceInfoList = ListUtils.union(mainDeviceList,followDeviceList);
            //开启多线程
            for (NmplDeviceInfo deviceInfo : deviceInfoList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",deviceInfo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
            return;
        }
        if(mainBaseStationList.size() > 0 && followBaseStationList.size() > 0){
            List<BaseStationInfoVo> unionList = ListUtils.union(mainBaseStationList,followBaseStationList);
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : unionList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",baseStationInfoVo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
            return;
        }
        //主设备是基站
        if(mainBaseStationList.size() > 0 && followDeviceList.size() > 0){
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : mainBaseStationList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",baseStationInfoVo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
            //开启多线程
            for (NmplDeviceInfo deviceInfo : followDeviceList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",deviceInfo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
            return;
        }

        //主设备是device
        if(mainDeviceList.size() > 0 && followBaseStationList.size() > 0){
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : followBaseStationList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",baseStationInfoVo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
            //开启多线程
            for (NmplDeviceInfo deviceInfo : mainDeviceList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,deviceInfo.getDeviceId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",deviceInfo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+deviceInfo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
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