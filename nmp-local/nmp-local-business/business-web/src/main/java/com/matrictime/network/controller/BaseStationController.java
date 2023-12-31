package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 基站管理模块
 * @author wangqiang
 */
@RestController
@RequestMapping(value = "/baseStation",method = RequestMethod.POST)
@Slf4j
public class BaseStationController {

    @Resource
    private BaseStationInfoService baseStationInfoService;

    @Resource
    private DeviceService deviceService;


    @Value("${thread.batchMaxSize}")
    Integer maxSize;

    /**
     * 基站插入
     * @param baseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:station:save")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站插入",operType = "基站插入")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息插入")
    public Result<Integer> insertBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.insertBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("新增基站异常:insertBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增基站异常");
        }
        return result;
    }

    /**
     * 基站更新
     * @param baseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:station:update")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站更新",operType = "基站更新",operLevl = "2")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息更新")
    public Result<Integer> updateBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.updateBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("更新基站异常:updateBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新基站异常");
        }
        return result;
    }

    /**
     * 基站删除
     * @param baseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:station:delete")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站删除",operType = "基站删除",operLevl = "2")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息删除")
    public Result<Integer> deleteBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.deleteBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("更新基站异常:deleteBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除基站异常");
        }
        return result;
    }

    /**
     * 根据条件基站信息查询
     * @param baseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:station:query")
    @SystemLog(opermodul = "基站管理模块",operDesc = "根据条件基站信息查询",operType = "根据条件基站信息查询")
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "根据条件基站信息查询")
    public Result<PageInfo> selectBaseStationInfo(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            result = baseStationInfoService.selectBaseStationList(baseStationInfoRequest);
        }catch (Exception e){
            log.info("根据条件查询基站异常:selectBaseStationInfo{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("根据条件查询基站异常");
        }
        return result;
    }

    /**
     * 和基站进行路由交互
     * @param baseStationInfoRequest
     * @return
     */
    @SystemLog(opermodul = "基站管理模块",operDesc = "和基站进行路由交互",operType = "和基站进行路由交互")
    @RequestMapping(value = "/selectByOperatorId",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "根据条件基站信息查询")
    public Result<BaseStationInfoResponse> selectByOperatorId(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectByOperatorId(baseStationInfoRequest);
        }catch (Exception e){
            log.info("和基站进行路由交互:selectByOperatorId{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("和基站进行路由交互");
        }
        return result;
    }

    /**
     * 根据ip获取设备Id
     * @param deviceInfoRequest
     * @return
     */
    @SystemLog(opermodul = "基站管理模块",operDesc = "根据ip获取设备Id",operType = "根据ip获取设备信息")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<StationVo> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<StationVo> result = new Result<>();
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        try {
            if(deviceInfoRequest == null || deviceInfoRequest.getDeviceType() == null ||
                    deviceInfoRequest.getLanIp() == null){
                return new Result<>(false,"请求参数异常");
            }
            if(StationTypeEnum.INSIDE.getCode().equals(deviceInfoRequest.getDeviceType()) ||
                    StationTypeEnum.BOUNDARY.getCode().equals(deviceInfoRequest.getDeviceType())){
                baseStationInfoRequest.setLanIp(deviceInfoRequest.getLanIp());
                result = baseStationInfoService.selectDeviceId(baseStationInfoRequest);
            }else {
                if("04".equals(deviceInfoRequest.getDeviceType())){
                    deviceInfoRequest.setDeviceType("01");
                }
                if("05".equals(deviceInfoRequest.getDeviceType())){
                    deviceInfoRequest.setDeviceType("02");
                }
                if("06".equals(deviceInfoRequest.getDeviceType())){
                    deviceInfoRequest.setDeviceType("03");
                }
                result = deviceService.selectDeviceId(deviceInfoRequest);
            }
            if(result.getResultObj() == null){
                return new Result<>(false,"dataBase中没有该数据");
            }
        }catch (Exception e){
            log.info("根据条件查询基站异常:selectDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("根据条件查询基站异常");
        }
        return result;
    }

    @SystemLog(opermodul = "路由管理模块",operDesc = "路由查询边界基站设备",operType = "主备设备查询基站")
    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectLinkBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("主备设备查询基站:selectBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("主备设备查询基站");
        }
        return result;
    }


    /**
     * 查询激活状态基站设备
     * @param baseStationInfoRequest
     * @return
     */
    @SystemLog(opermodul = "基站管理模块",operDesc = "查询激活状态基站设备",operType = "查询")
    @RequestMapping(value = "/selectActiveBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectActiveBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectActiveBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("查询激活状态基站设备异常:selectBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后再试");
        }
        return result;
    }





    /**
     * 查询归属信息
     * @return
     */
    @SystemLog(opermodul = "基站管理模块",operDesc = "查询归属信息",operType = "查询")
    @RequestMapping(value = "/selectBelongInformation",method = RequestMethod.POST)
    public Result<BelongInformationResponse> selectBelongInformation(){
        try {
            return baseStationInfoService.selectBelongInformation();
        }catch (Exception e){
            log.info("selectBelongInformation:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    /**
     * 查询归属信息
     * @return
     */
    @SystemLog(opermodul = "基站管理模块",operDesc = "查询归属信息",operType = "查询")
    @RequestMapping(value = "/selectAllBelongInformation",method = RequestMethod.POST)
    public Result<BelongInformationResponse> selectAllBelongInformation(){
        try {
            return baseStationInfoService.selectAllBelongInformation();
        }catch (Exception e){
            log.info("selectAllBelongInformation:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    /**
     * 查询基站总数
     * @return
     */
    @SystemLog(opermodul = "设备管理模块",operDesc = "查询设备总数",operType = "查询")
    @RequestMapping(value = "/countBaseStation",method = RequestMethod.POST)
    public Result<CountBaseStationResponse> countBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        try {
            if(StringUtils.isEmpty(baseStationInfoRequest.getRelationOperatorId())){
                CountBaseStationResponse countBaseStationResponse = new CountBaseStationResponse();
                Result<CountBaseStationResponse> result = new Result<>();
                result.setResultObj(countBaseStationResponse);
                result.setSuccess(true);
                return result;
            }
            return baseStationInfoService.countBaseStation(baseStationInfoRequest);
        }catch (Exception e){
            log.info("countBaseStation:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

//    /**
//     * 更新基站下的用户数
//     * @return
//     */
//    @SystemLog(opermodul = "设备管理模块",operDesc = "更新设备下的用户数",operType = "更新")
//    @RequestMapping(value = "/updateConnectCount",method = RequestMethod.POST)
//    public Result<Integer> updateConnectCount(@RequestBody BaseStationCountRequest baseStationCountRequest){
//        try {
//            if(StringUtils.isEmpty(baseStationCountRequest.getDeviceId()) &&
//                    StringUtils.isEmpty(baseStationCountRequest.getCurrentConnectCount())){
//                throw new RuntimeException("缺少必传参数");
//            }
//            return deviceService.updateConnectCount(baseStationCountRequest);
//        }catch (Exception e){
//            log.info("updateConnectCount:{}",e.getMessage());
//            return new Result<>(false,"");
//        }
//    }

    /**
     * 查询不同Ip物理设备
     * @param baseStationInfoRequest
     * @return
     */
    @SystemLog(opermodul = "设备管理模块",operDesc = "查询不同Ip物理设备",operType = "查询")
    @RequestMapping(value = "/selectPhysicalDevice",method = RequestMethod.POST)
    public Result<List<CommunityBaseStationVo>> selectPhysicalDevice(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        try {
            return baseStationInfoService.selectPhysicalDevice(baseStationInfoRequest);
        }catch (Exception e){
            log.info("selectPhysicalDevice:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    /**
     * 更新当前用户数
     * @param currentCountRequest
     * @return
     */
    @RequestMapping(value = "/updateCurrentConnectCount",method = RequestMethod.POST)
    public Result<Integer> updateCurrentConnectCount(@RequestBody CurrentCountRequest currentCountRequest){
        try {
            return baseStationInfoService.updateCurrentConnectCount(currentCountRequest);
        }catch (Exception e){
            log.info("updateCurrentConnectCount:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    /**
     * 插入边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:boundarystation:save")
    @RequestMapping(value = "/insertBorderBaseStation",method = RequestMethod.POST)
    public Result<Integer> insertBorderBaseStation(@RequestBody BorderBaseStationInfoRequest borderBaseStationInfoRequest){
        try {
            return baseStationInfoService.insertBorderBaseStation(borderBaseStationInfoRequest);
        }catch (Exception e){
            log.info("insertBorderBaseStation:{}",e.getMessage());
            return new Result<>(false,"插入失败");
        }
    }

    /**
     * 删除边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:boundarystation:delete")
    @RequestMapping(value = "/deleteBorderBaseStation",method = RequestMethod.POST)
    public Result<Integer> deleteBorderBaseStation(@RequestBody BorderBaseStationInfoRequest borderBaseStationInfoRequest){
        try {
            return baseStationInfoService.deleteBorderBaseStation(borderBaseStationInfoRequest);
        }catch (Exception e){
            log.info("deleteBorderBaseStation:{}",e.getMessage());
            return new Result<>(false,"删除失败");
        }
    }

    /**
     * 更新边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:boundarystation:update")
    @RequestMapping(value = "/updateBorderBaseStation",method = RequestMethod.POST)
    public Result<Integer> updateBorderBaseStation(@RequestBody BorderBaseStationInfoRequest borderBaseStationInfoRequest){
        try {
            return baseStationInfoService.updateBorderBaseStation(borderBaseStationInfoRequest);
        }catch (Exception e){
            log.info("updateBorderBaseStation:{}",e.getMessage());
            return new Result<>(false,"更新失败");
        }
    }

    /**
     * 边界基站查询
     * @param borderBaseStationInfoRequest
     * @return
     */
    @RequiresPermissions("sys:boundarystation:query")
    @RequestMapping(value = "/selectBorderBaseStationInfo",method = RequestMethod.POST)
    public Result<PageInfo> selectBorderBaseStationInfo(@RequestBody BorderBaseStationInfoRequest borderBaseStationInfoRequest){
        try {
            return baseStationInfoService.selectBorderBaseStationInfo(borderBaseStationInfoRequest);
        }catch (Exception e){
            log.info("selectBorderBaseStationInfo:{}",e.getMessage());
            return new Result<>(false,"查询边界基站失败");
        }
    }



    /**
     * 获取设备id sequence
     */
    @RequestMapping(value = "/getDeviceId",method = RequestMethod.POST)
    public Result getDeviceId() {
        return baseStationInfoService.getDeviceId();
    }
}