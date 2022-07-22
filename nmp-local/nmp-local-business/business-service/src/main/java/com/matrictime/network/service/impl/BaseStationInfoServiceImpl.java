package com.matrictime.network.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.util.CommonCheckUtil;
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
        BaseStationInfoRequest infoRequest = new BaseStationInfoRequest();
        try {
            if(!CommonCheckUtil.checkStringLength(baseStationInfoRequest.getStationName(),null,16)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            baseStationInfoRequest.setCreateTime(getFormatDate(date));
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            baseStationInfoRequest.setStationId(SnowFlake.nextId_String());
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            baseStationInfoRequest.setIsExist("1");
            baseStationInfoRequest.setStationStatus(DeviceStatusEnum.NORMAL.getCode());
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            boolean publicPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getPublicNetworkPort());
            boolean lanPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getLanPort());
            if(publicPortReg == false || lanPortReg == false){
                return new Result<>(false,"端口格式不正确");
            }
            //判断小区是否正确
            String preBID = companyInfoDomainService.getPreBID(baseStationInfoRequest.getRelationOperatorId());
            if(StringUtil.isEmpty(preBID)){
                return new Result<>(false,"运营商不存在");
            }
            String networkId = preBID + "-" + baseStationInfoRequest.getStationNetworkId();
            baseStationInfoRequest.setStationNetworkId(networkId);
            infoRequest.setStationNetworkId(baseStationInfoRequest.getStationNetworkId());
            infoRequest.setPublicNetworkIp(baseStationInfoRequest.getPublicNetworkIp());
            infoRequest.setLanIp(baseStationInfoRequest.getLanIp());
            List<BaseStationInfoVo> baseStationInfoVos = baseStationInfoDomainService.selectBaseStation(infoRequest);
            if(baseStationInfoVos.size() > 0){
                return new Result<>(false,"入网id或ip重复");
            }
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
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public Result<Integer> updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer updateFlag;
        try {
            if(!CommonCheckUtil.checkStringLength(baseStationInfoRequest.getStationName(),null,16)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            boolean publicIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getPublicNetworkIp());
            boolean lanIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getLanIp());
            if(publicIpReg == false || lanIpReg == false){
                return new Result<>(false,"ip格式不正确");
            }
            boolean publicPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getPublicNetworkPort());
            boolean lanPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getLanPort());
            if(publicPortReg == false || lanPortReg == false){
                return new Result<>(false,"端口格式不正确");
            }
            updateFlag = baseStationInfoDomainService.updateBaseStationInfo(baseStationInfoRequest);
            if(updateFlag == 1){
                result.setSuccess(true);
                result.setResultObj(updateFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public Result<Integer> deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer deleteFlag;
        try {
            deleteFlag = baseStationInfoDomainService.deleteBaseStationInfo(baseStationInfoRequest);
            if(deleteFlag == 1){
                result.setSuccess(true);
                result.setResultObj(deleteFlag);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
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
            result.setErrorMsg("参数异常");
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
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<BaseStationInfoResponse> selectActiveBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        try {
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoDomainService.
                    selectActiveBaseStationInfo(baseStationInfoRequest));
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
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
            result.setErrorMsg("参数异常");
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
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public BaseStationInfoVo selectByNetworkId(BaseStationInfoRequest baseStationInfoRequest) {
        BaseStationInfoVo result = new BaseStationInfoVo();
        try {
            result = baseStationInfoDomainService.selectByNetworkId(baseStationInfoRequest);
        }catch (Exception e){
            log.info("selectByNetworkId{} 查询异常");
        }
        return result;
    }

    @Override
    public Result<BaseStationInfoResponse> selectByOperatorId(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        try {
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoDomainService.selectByOperatorId(baseStationInfoRequest));
            result.setResultObj(baseStationInfoResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<PageInfo> selectBaseStationList(BaseStationInfoRequest baseStationInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = baseStationInfoDomainService.selectBaseStationList(baseStationInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (Exception e){
            log.error(e.getMessage());
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