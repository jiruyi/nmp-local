package com.matrictime.network.service.impl;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
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
import com.matrictime.network.service.LinkRelationService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LinkRelationServiceImpl implements LinkRelationService {

    @Resource
    private LinkRelationDomainService linkRelationDomainService;

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
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
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
            result.setResultObj(linkRelationDomainService.updateLinkRelation(linkRelationRequest));
            result.setSuccess(true);
        }catch (Exception e){
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





    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }
}