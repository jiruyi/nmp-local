package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import com.matrictime.network.modelVo.PcDataVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.PcDataReq;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.service.PcDataService;
import com.matrictime.network.util.ListSplitUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    PcDataService pcDataService;

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


    @ApiOperation(value = "基站下挂一体机数据多条件查询接口",notes = "基站下挂一体机数据多条件查询接口")
    @RequestMapping(value = "/queryPcDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:station:detail")
    @SystemLog(opermodul = "基站管理模块",operDesc = "查询基站下挂一体机数据",operType = "查询")
    public Result<PageInfo> queryPcDataByConditon(@RequestBody PcDataReq pcDataReq){
        return pcDataService.queryByConditon(pcDataReq);
    }


    /**
     * 基站下挂一体机数据上报
     * @param pcDataReq
     * @return
     */
    @RequestMapping(value = "/savePcData",method = RequestMethod.POST)
    public Result savePcData(@RequestBody PcDataReq pcDataReq){
        Result result = null;
        try {
            if (pcDataReq.getNmplPcDataVoList()!=null&&pcDataReq.getNmplPcDataVoList().size()>maxSize){
                List<Result> resultList = new ArrayList<>();
                List<List<PcDataVo>> data = ListSplitUtil.split(pcDataReq.getNmplPcDataVoList(),maxSize);
                for (List<PcDataVo> datum : data) {
                    PcDataReq req = new PcDataReq();
                    req.setNmplPcDataVoList(datum);
                    resultList.add(pcDataService.save(req).get());
                }
                result = new Result<>();
                result.setSuccess(true);
                result.setResultObj(resultList);
            }else {
                result = pcDataService.save(pcDataReq).get();
            }
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        } catch (ExecutionException e) {
            log.info(e.getMessage());
        }finally {
            return result;
        }
    }


}