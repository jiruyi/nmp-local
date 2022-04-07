package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.LinkRelationResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.service.LinkRelationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/linkRelation",method = RequestMethod.POST)
@Slf4j
public class LinkRelationController {
    @Resource
    private BaseStationInfoService baseStationInfoService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private LinkRelationService linkRelationService;


    @SystemLog(opermodul = "链路管理模块",operDesc = "链路查询基站设备",operType = "链路查询基站设备")
    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectLinkBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("链路查询基站设备异常:selectBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("链路查询基站设备异常");
        }
        return result;
    }

    @SystemLog(opermodul = "链路管理模块",operDesc = "链路查询设备",operType = "链路查询设备")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<DeviceResponse> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<DeviceResponse> result = new Result<>();
        try {
            result = deviceService.selectLinkDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("链路查询设备异常:selectDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("链路查询设备异常");
        }
        return result;
    }

    @RequiresPermissions("sys:link:save")
    @SystemLog(opermodul = "链路管理模块",operDesc = "插入链路信息",operType = "插入链路信息")
    @RequestMapping(value = "/insertLinkRelation",method = RequestMethod.POST)
    public Result<Integer> insertLinkRelation(@RequestBody LinkRelationRequest linkRelationRequest){
        Result<Integer> result = new Result<>();
        try {
            result = linkRelationService.insertLinkRelation(linkRelationRequest);
        }catch (Exception e){
            log.info("插入链路信息异常:insertLinkRelation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("插入链路信息异常");
        }
        return result;
    }

    @RequiresPermissions("sys:link:delete")
    @SystemLog(opermodul = "链路管理模块",operDesc = "删除链路信息",operType = "删除链路信息")
    @RequestMapping(value = "/deleteLinkRelation",method = RequestMethod.POST)
    public Result<Integer> deleteLinkRelation(@RequestBody LinkRelationRequest linkRelationRequest){
        Result<Integer> result = new Result<>();
        try {
            result = linkRelationService.deleteLinkRelation(linkRelationRequest);
        }catch (Exception e){
            log.info("删除链路信息异常:deleteLinkRelation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除链路信息异常");
        }
        return result;
    }

    @RequiresPermissions("sys:link:update")
    @SystemLog(opermodul = "链路管理模块",operDesc = "更新链路信息",operType = "更新链路信息")
    @RequestMapping(value = "/updateLinkRelation",method = RequestMethod.POST)
    public Result<Integer> updateLinkRelation(@RequestBody LinkRelationRequest linkRelationRequest){
        Result<Integer> result = new Result<>();
        try {
            result = linkRelationService.updateLinkRelation(linkRelationRequest);
        }catch (Exception e){
            log.info("更新链路信息异常:updateLinkRelation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新链路信息异常");
        }
        return result;
    }

    @RequiresPermissions("sys:link:query")
    @SystemLog(opermodul = "链路管理模块",operDesc = "查询链路信息",operType = "查询链路信息")
    @RequestMapping(value = "/selectLinkRelation",method = RequestMethod.POST)
    public Result<PageInfo<LinkRelationVo>> selectLinkRelation(@RequestBody LinkRelationRequest linkRelationRequest){
        Result<PageInfo<LinkRelationVo>> result = new Result<>();
        Result<PageInfo<LinkRelationVo>> linkRelationResult = new Result<>();
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        DeviceInfoRequest deviceInfoRequest = new DeviceInfoRequest();
        try {
            result = linkRelationService.selectLinkRelation(linkRelationRequest);
            Result<BaseStationInfoResponse> baseStationInfoResponseResult = linkRelationService.selectLinkRelationStation(baseStationInfoRequest);
            Result<DeviceResponse> deviceResponseResult = linkRelationService.selectLinkRelationDevice(deviceInfoRequest);
            Map<String, DeviceInfoVo> deviceMap = getDeviceMap(deviceResponseResult.getResultObj().getDeviceInfoVos());
            Map<String, BaseStationInfoVo> stationMap = getStationMap(baseStationInfoResponseResult.getResultObj().getBaseStationInfoList());
            PageInfo<LinkRelationVo> linkRelation = getLinkRelation(result, deviceMap, stationMap);
            linkRelationResult.setResultObj(linkRelation);
            linkRelationResult.setSuccess(true);
        }catch (Exception e){
            log.info("查询链路信息异常:selectLinkRelation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询链路信息异常");
        }
        return linkRelationResult;
    }

    /*
    * 基站数据做成Map映射
    * */
    private Map<String,BaseStationInfoVo> getStationMap(List<BaseStationInfoVo> stationList){
        Map stationMap = new HashMap<>();
        for(int stationIndex = 0;stationIndex < stationList.size();stationIndex++){
            stationMap.put(stationList.get(stationIndex).getStationId(),stationList.get(stationIndex));
        }
        return stationMap;
    }

    /**
     * 根据设备数据做成Map映射
     */
    private Map<String,DeviceInfoVo> getDeviceMap(List<DeviceInfoVo> deviceInfoVoList){
        Map deviceMap = new HashMap<>();
        for(int stationIndex = 0;stationIndex < deviceInfoVoList.size();stationIndex++){
            deviceMap.put(deviceInfoVoList.get(stationIndex).getDeviceId(),deviceInfoVoList.get(stationIndex));
        }
        return deviceMap;
    }

    /**
     * 返回带有端口，Ip的链路数据
     */
    private PageInfo<LinkRelationVo> getLinkRelation(Result<PageInfo<LinkRelationVo>> result,Map<String, DeviceInfoVo> deviceMap,Map<String, BaseStationInfoVo> stationMap){
        PageInfo<LinkRelationVo> pageInfo = new PageInfo<>();
        List<LinkRelationVo> linkRelationVos = new ArrayList<>();
        for (int linkRelationIndex = 0;linkRelationIndex < result.getResultObj().getList().size();linkRelationIndex++){
            LinkRelationVo linkRelationVo = result.getResultObj().getList().get(linkRelationIndex);
            DeviceInfoVo mainDeviceInfoVo = deviceMap.get(linkRelationVo.getMainDeviceId());
            DeviceInfoVo followDeviceInfoVo = deviceMap.get(linkRelationVo.getFollowDeviceId());
            BaseStationInfoVo mainBaseStationInfoVo = stationMap.get(linkRelationVo.getMainDeviceId());
            BaseStationInfoVo followBaseStationInfoVo = stationMap.get(linkRelationVo.getFollowDeviceId());
            if(mainDeviceInfoVo != null){
                linkRelationVo.setMainPublicNetworkIp(mainDeviceInfoVo.getPublicNetworkIp());
                linkRelationVo.setMainPublicNetworkPort(mainDeviceInfoVo.getPublicNetworkPort());
                linkRelationVo.setMainLanIp(mainDeviceInfoVo.getLanIp());
                linkRelationVo.setMainLanPort(mainDeviceInfoVo.getLanPort());
                linkRelationVo.setMainDeviceName(mainDeviceInfoVo.getDeviceName());
                linkRelationVos.add(linkRelationVo);
            }
            if(followDeviceInfoVo != null){
                linkRelationVo.setFollowPublicNetworkIp(followDeviceInfoVo.getPublicNetworkIp());
                linkRelationVo.setFollowPublicNetworkPort(followDeviceInfoVo.getPublicNetworkPort());
                linkRelationVo.setFollowLanPort(followDeviceInfoVo.getLanPort());
                linkRelationVo.setFollowLanIp(followDeviceInfoVo.getLanIp());
                linkRelationVo.setFollowDeviceName(followDeviceInfoVo.getDeviceName());
                linkRelationVos.add(linkRelationVo);
            }
            if(mainBaseStationInfoVo != null){
                linkRelationVo.setMainPublicNetworkIp(mainBaseStationInfoVo.getPublicNetworkIp());
                linkRelationVo.setMainPublicNetworkPort(mainBaseStationInfoVo.getPublicNetworkPort());
                linkRelationVo.setMainLanIp(mainBaseStationInfoVo.getLanIp());
                linkRelationVo.setMainLanPort(mainBaseStationInfoVo.getLanPort());
                linkRelationVo.setMainDeviceName(mainBaseStationInfoVo.getStationName());
                linkRelationVos.add(linkRelationVo);
            }
            if(followBaseStationInfoVo != null){
                linkRelationVo.setFollowPublicNetworkIp(followBaseStationInfoVo.getPublicNetworkIp());
                linkRelationVo.setFollowPublicNetworkPort(followBaseStationInfoVo.getPublicNetworkPort());
                linkRelationVo.setFollowLanPort(followBaseStationInfoVo.getLanPort());
                linkRelationVo.setFollowLanIp(followBaseStationInfoVo.getLanIp());
                linkRelationVo.setFollowDeviceName(followBaseStationInfoVo.getStationName());
                linkRelationVos.add(linkRelationVo);
            }
        }
        pageInfo.setList(linkRelationVos);
        return pageInfo;
    }










}