package com.matrictime.network.service.impl;

import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class BaseStationInfoServiceImpl implements BaseStationInfoService {

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private CompanyInfoDomainService companyInfoDomainService;

    @Override
    public Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer insertFlag = null;
        try {
            baseStationInfoRequest.setCreateTime(getFormatDate(date));
            baseStationInfoRequest.setStationId(SnowFlake.nextId_String());
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getNickName());
            baseStationInfoRequest.setIsExist("1");
            baseStationInfoRequest.setStationStatus(DeviceStatusEnum.NORMAL.getCode());
            //判断小区是否正确

            String preBID = companyInfoDomainService.getPreBID(baseStationInfoRequest.getRelationOperatorId());
            String stationRandomSeed = preBID + "-" + baseStationInfoRequest.getStationRandomSeed();
            baseStationInfoRequest.setStationRandomSeed(stationRandomSeed);
            insertFlag = baseStationInfoDomainService.insertBaseStationInfo(baseStationInfoRequest);
            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
            }else {
                result.setResultObj(insertFlag);
                result.setSuccess(false);
            }
        }catch (Exception e){
            log.error("基站新增{}新增异常：{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer updateFlag = null;
        try {
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getNickName());
            updateFlag = baseStationInfoDomainService.updateBaseStationInfo(baseStationInfoRequest);
            if(updateFlag == 1){
                result.setSuccess(true);
                result.setResultObj(updateFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer deleteFlag = null;
        try {
            deleteFlag = baseStationInfoDomainService.deleteBaseStationInfo(baseStationInfoRequest);
            if(deleteFlag == 1){
                result.setSuccess(true);
                result.setResultObj(deleteFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = baseStationInfoDomainService.selectBaseStationInfo(baseStationInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 根据前端页面条件自动关联获取基站信息
     */
    @Override
    public Result<BaseStationInfoResponse> selectLinkBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        try {
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoDomainService.
                    selectLinkBaseStationInfo(baseStationInfoRequest));
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<BaseStationInfoResponse> selectBaseStationBatch(List<String> list) {
        Result<BaseStationInfoResponse> result = new Result<>();
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        try {
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoDomainService.
                    selectBaseStationBatch(list));
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<StationVo> selectDeviceId(BaseStationInfoRequest baseStationInfoRequest) {
        Result<StationVo> result = new Result<>();
        try {
            result.setResultObj(baseStationInfoDomainService.selectDeviceId(baseStationInfoRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }


    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }
}