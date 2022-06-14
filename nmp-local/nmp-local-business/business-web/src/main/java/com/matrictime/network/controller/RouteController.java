package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.BaseRequest;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.RouteService;
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

/**
 * 路由管理模块
 * @author wangqiang
 */
@RestController
@RequestMapping(value = "/route",method = RequestMethod.POST)
@Slf4j
public class RouteController {
    @Resource
    private RouteService routeService;

    @Resource
    private BaseStationInfoService baseStationInfoService;

    /**
     * 路由查询边界基站设备
     * @param baseStationInfoRequest
     * @return
     */
    @SystemLog(opermodul = "路由管理模块",operDesc = "路由查询边界基站设备",operType = "路由查询边界基站设备")
    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            baseStationInfoRequest.setStationType(StationTypeEnum.BOUNDARY.getCode());
            result = baseStationInfoService.selectLinkBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("路由查询边界基站设备异常:selectBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("路由查询边界基站设备异常");
        }
        return result;
    }

    /**
     * 路由查询边界基站设备
     */
    @SystemLog(opermodul = "路由管理模块",operDesc = "路由查询边界基站设备",operType = "路由查询边界基站设备")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public List<NmplDeviceInfoExt> selectDevice(){
        List<NmplDeviceInfoExt> deviceInfoExtList;
        deviceInfoExtList = routeService.selectDevices();
        return deviceInfoExtList;
    }

    /**
     * 新增路由
     * @param routeRequest
     * @return
     */
    @RequiresPermissions("sys:route:save")
    @SystemLog(opermodul = "路由管理模块",operDesc = "新增路由",operType = "新增路由")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result<Integer> insertRoute(@RequestBody RouteRequest routeRequest){
        Result<Integer> result = new Result<>();
        try {
            result = routeService.insertRoute(routeRequest);
        }catch (Exception e){
            log.info("新增路由异常:insertRoute{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增路由异常");
        }
        return result;
    }

    /**
     * 删除路由
     * @param routeRequest
     * @return
     */
    @RequiresPermissions("sys:route:delete")
    @SystemLog(opermodul = "路由管理模块",operDesc = "删除路由",operType = "删除路由")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result<Integer> deleteRoute(@RequestBody RouteRequest routeRequest){
        Result<Integer> result = new Result<>();
        try {
            result = routeService.deleteRoute(routeRequest);
        }catch (Exception e){
            log.info("删除路由异常:deleteRoute{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除路由异常");
        }
        return result;
    }

    /**
     * 更新路由
     * @param routeRequest
     * @return
     */
    @RequiresPermissions("sys:route:update")
    @SystemLog(opermodul = "路由管理模块",operDesc = "更新路由",operType = "更新路由")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<Integer> updateRoute(@RequestBody RouteRequest routeRequest){
        Result<Integer> result = new Result<>();
        try {
            result = routeService.updateRoute(routeRequest);
        }catch (Exception e){
            log.info("更新路由异常:updateRoute{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新路由异常");
        }
        return result;
    }

    /**
     * 查询路由
     * @param routeRequest
     * @return
     */
    @RequiresPermissions("sys:route:query")
    @SystemLog(opermodul = "路由管理模块",operDesc = "查询路由",operType = "查询路由")
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Result<PageInfo<RouteVo>> selectRoute(@RequestBody RouteRequest routeRequest){
        Result<PageInfo<RouteVo>> resultRoute = new Result<>();
        Result<BaseStationInfoResponse> baseStationInfoResponseResult;
        Map<String,BaseStationInfoVo> baseStationInfoVoMap;
        List<String> list;
        try {
            BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
            baseStationInfoRequest.setStationNetworkId(routeRequest.getStationNetworkId());
            List<NmplDeviceInfoExt> deviceInfoExtList;
            deviceInfoExtList = routeService.selectDevices();
            Map<String, NmplDeviceInfoExt> deviceStationMap = getDeviceStationMap(deviceInfoExtList);
            BaseStationInfoVo baseStationInfoVo = baseStationInfoService.selectByNetworkId(baseStationInfoRequest);
            routeRequest.setAccessDeviceId(baseStationInfoVo.getStationId());
            Result<PageInfo<RouteVo>> pageInfoResult = routeService.selectRoute(routeRequest);
            List<RouteVo> routeVoList = pageInfoResult.getResultObj().getList();
            list = getStationIdList(routeVoList);
            if(list.size() <= 0){
                resultRoute.setResultObj(new PageInfo<>());
                resultRoute.setSuccess(true);
                return resultRoute;
            }
            baseStationInfoResponseResult = baseStationInfoService.selectBaseStationBatch(list);
            List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponseResult.getResultObj().getBaseStationInfoList();
            baseStationInfoVoMap = getBaseStationMap(baseStationInfoList);

            PageInfo<RouteVo> routeVoPageInfo = routeVoPageInfo(routeRequest,baseStationInfoVoMap,deviceStationMap, routeVoList);
            routeVoPageInfo.setCount(pageInfoResult.getResultObj().getCount());
            routeVoPageInfo.setPages(pageInfoResult.getResultObj().getPages());
            resultRoute.setResultObj(routeVoPageInfo);
            resultRoute.setSuccess(true);
        }catch (Exception e){
            log.info("查询路由异常:selectRoute{}",e.getMessage());
            resultRoute.setSuccess(false);
            resultRoute.setErrorMsg("查询路由异常");
        }
        return resultRoute;
    }

    /*
    * 生成批量查询条件
    * */
    private List<String> getStationIdList(List<RouteVo> routeVoList){
        List<String> list = new ArrayList<>();
        for (int routeIndex = 0;routeIndex < routeVoList.size();routeIndex++){
            list.add(routeVoList.get(routeIndex).getBoundaryDeviceId());
        }
        return list;
    }

    /*
    * 获取baseStationId映射的实体类
    * */
    private Map<String,BaseStationInfoVo> getBaseStationMap(List<BaseStationInfoVo> baseStationInfoList){
        Map<String,BaseStationInfoVo> map = new HashMap<>();
        for(int baseStationIndex = 0;baseStationIndex < baseStationInfoList.size();baseStationIndex++){
            map.put(baseStationInfoList.get(baseStationIndex).getStationId(),baseStationInfoList.get(baseStationIndex));
        }
        return map;
    }

    /*
     * 获取deviceStationId映射的实体类
     * */
    private Map<String,NmplDeviceInfoExt> getDeviceStationMap(List<NmplDeviceInfoExt> deviceInfoExtList){
        Map<String,NmplDeviceInfoExt> map = new HashMap<>();
        for(int baseStationIndex = 0;baseStationIndex < deviceInfoExtList.size();baseStationIndex++){
            map.put(deviceInfoExtList.get(baseStationIndex).getDeviceId(),deviceInfoExtList.get(baseStationIndex));
        }
        return map;
    }

    /*
    * 生成最终RouteVo结果
    * */
    private PageInfo<RouteVo> routeVoPageInfo(RouteRequest routeRequest,Map<String,BaseStationInfoVo> baseStationInfoVoMap,Map<String,
            NmplDeviceInfoExt> deviceStationMap, List<RouteVo> routeVoList){
        PageInfo<RouteVo> pageInfo = new PageInfo<>();
        List<RouteVo> routeResult = new ArrayList<>();
        for(int resultIndex = 0;resultIndex < routeVoList.size();resultIndex++){
            BaseStationInfoVo baseStationInfoVo = baseStationInfoVoMap.get(routeVoList.get(resultIndex).getBoundaryDeviceId());
            NmplDeviceInfoExt nmplDeviceInfoExt = deviceStationMap.get(routeVoList.get(resultIndex).getAccessDeviceId());
            if(baseStationInfoVo!= null && nmplDeviceInfoExt!= null) {
                if(routeRequest.getConditionType() == 1){
                    String[] split = nmplDeviceInfoExt.getStationNetworkId().split("-");
                    routeVoList.get(resultIndex).setStationNetworkId(split[split.length-1]);
                }else {
                    routeVoList.get(resultIndex).setStationNetworkId(nmplDeviceInfoExt.getStationNetworkId());
                }
                routeVoList.get(resultIndex).setBoundaryDevicePublicIp(baseStationInfoVo.getPublicNetworkIp());
                routeVoList.get(resultIndex).setBoundaryDevicePublicPort(baseStationInfoVo.getPublicNetworkPort());
                routeVoList.get(resultIndex).setBoundaryDeviceLanIp(baseStationInfoVo.getLanIp());
                routeVoList.get(resultIndex).setBoundaryDeviceLanPort(baseStationInfoVo.getLanPort());
                routeVoList.get(resultIndex).setStationName(baseStationInfoVo.getStationName());
                routeResult.add(routeVoList.get(resultIndex));
            }
        }
        pageInfo.setList(routeResult);
        return pageInfo;
    }




















}