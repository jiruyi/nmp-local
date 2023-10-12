package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.modelVo.PercentageFlowVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.service.DataCollectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */

@RequestMapping(value = "/dataCollect")
@RestController
@Slf4j
public class DataCollectController {

    @Resource
    private DataCollectService dataCollectService;

    /**
     * 分别获取各个流量的总和
     * @param dataCollectRequest
     * @return
     */
    //@RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/sumDataCollect",method = RequestMethod.POST)
    public Result<Double> sumDataCollect(@RequestBody DataCollectRequest dataCollectRequest){
        Result<Double> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectRequest.getDeviceType())){
                return new Result<>(false,"缺少必传参数");
            }
            result = dataCollectService.sumDataValue(dataCollectRequest);
        }catch (Exception e){
            log.info("sumDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 分别获取单个小区各个流量
     * @param dataCollectRequest
     * @return
     */
    //@RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/sumCompanyDataCollect",method = RequestMethod.POST)
    public Result<Double> sumCompanyDataCollect(@RequestBody DataCollectRequest dataCollectRequest){
        Result<Double> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectRequest.getDeviceType())){
                return new Result<>(false,"缺少必传参数");
            }
            if(StringUtils.isEmpty(dataCollectRequest.getCompanyNetworkId())){
                return new Result<>(false,"缺少必传参数");
            }
            result = dataCollectService.sumDataValue(dataCollectRequest);
        }catch (Exception e){
            log.info("sumCompanyDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询各个流量时间点的流量图
     * @param dataCollectRequest
     * @return
     */
    //@RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/selectLoadData",method = RequestMethod.POST)
    public Result<List<DataTimeVo>> selectLoadData(@RequestBody DataCollectRequest dataCollectRequest){
        Result<List<DataTimeVo> > result = new Result<>();
        try {
            result = dataCollectService.selectLoadData(dataCollectRequest);
        }catch (Exception e){
            log.info("selectLoadData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询单个小区流量值占比
     * @param
     * @return
     */
    //@RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/selectCompanyData",method = RequestMethod.POST)
    public Result<List<PercentageFlowVo>> selectCompanyData(){
        Result<List<PercentageFlowVo>> result = new Result<>();
        try {
            result = dataCollectService.selectCompanyData();
        }catch (Exception e){
            log.info("selectCompanyData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询单个小区流量折线图
     * @param dataCollectRequest
     * @return
     */
    //@RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/selectCompanyLoadData",method = RequestMethod.POST)
    public Result<List<DataTimeVo>> selectCompanyLoadData(@RequestBody DataCollectRequest dataCollectRequest){
        Result<List<DataTimeVo>> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectRequest.getDeviceType())){
                return new Result<>(false,"缺少必传参数");
            }
            if(StringUtils.isEmpty(dataCollectRequest.getCompanyNetworkId())){
                return new Result<>(false,"缺少必传参数");
            }
            result = dataCollectService.selectLoadData(dataCollectRequest);
        }catch (Exception e){
            log.info("selectCompanyLoadData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/insertDataCollect",method = RequestMethod.POST)
    public Result<Integer> insertDataCollect(@RequestBody DataCollectResponse dataCollectResponse){
        Result<Integer> result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(dataCollectResponse)){
                return new Result<>(false,"缺少必传参数");
            }
            result = dataCollectService.insertDataCollect(dataCollectResponse);
        }catch (Exception e){
            log.info("insertDataCollect:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }
}
