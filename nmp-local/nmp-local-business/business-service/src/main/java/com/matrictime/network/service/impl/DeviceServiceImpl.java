package com.matrictime.network.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.util.CommonCheckUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.matrictime.network.base.exception.ErrorMessageContants.*;

@Service
@Slf4j
public class DeviceServiceImpl  extends SystemBaseService implements DeviceService {

    @Resource
    private DeviceDomainService deviceDomainService;

    @Resource
    private CompanyInfoDomainService companyInfoDomainService;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    private AsyncService asyncService;

    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Override
    public Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer insertFlag = null;
        Date date = new Date();
        DeviceInfoRequest infoRequest = new DeviceInfoRequest();
        try {

            deviceInfoRequest.setStationNetworkId(String.valueOf(nmplBaseStationInfoMapper.getSequenceId()));
            deviceInfoRequest.setDeviceId(SnowFlake.nextId_String());
            deviceInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());

            checkParam(deviceInfoRequest);

            deviceInfoRequest.setByteNetworkId(DecimalConversionUtil.bidToByteArray(deviceInfoRequest.getStationNetworkId()));

            insertFlag = deviceDomainService.insertDevice(deviceInfoRequest);
            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
                pushToProxy(deviceInfoRequest.getDeviceId(),DataConstants.URL_DEVICE_INSERT);
            }
        }catch (SystemException e){
            log.info("设备新增异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备新增异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> deleteDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer deleteFlag;
        try {
            deleteFlag = deviceDomainService.deleteDevice(deviceInfoRequest);
            if(deleteFlag == 1){
                result.setResultObj(deleteFlag);
                result.setSuccess(true);
                pushToProxy(deviceInfoRequest.getDeviceId(),DataConstants.URL_DEVICE_UPDATE);
            }
        }catch (SystemException e){
            log.info("设备删除异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备删除异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> updateDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<Integer> result = new Result<>();
        Integer updateFlag;
        try {
            checkParam(deviceInfoRequest);

            updateFlag = deviceDomainService.updateDevice(deviceInfoRequest);
            if(updateFlag == 1){
                result.setResultObj(updateFlag);
                result.setSuccess(true);
                pushToProxy(deviceInfoRequest.getDeviceId(),DataConstants.URL_DEVICE_UPDATE);
            }
        }catch (SystemException e){
            log.info("设备修改异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备修改异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<PageInfo> selectDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = deviceDomainService.selectDevice(deviceInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectLinkDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectLinkDevice(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectDeviceForLinkRelation(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectDeviceForLinkRelation(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<DeviceResponse> selectActiveDevice(DeviceInfoRequest deviceInfoRequest) {
        Result<DeviceResponse> result = new Result<>();
        try {
            DeviceResponse deviceResponse = new DeviceResponse();
            if (ParamCheckUtil.checkVoStrBlank(deviceInfoRequest.getDeviceType())){
                log.info("DeviceServiceImpl.selectActiveDevice 接口异常,DeviceType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                result.setErrorMsg("系统异常，请稍后再试");
                result.setSuccess(false);
                return result;
            }
            deviceResponse.setDeviceInfoVos(deviceDomainService.selectActiveDevice(deviceInfoRequest));
            result.setResultObj(deviceResponse);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<StationVo> selectDeviceId(DeviceInfoRequest deviceInfoRequest) {
       Result<StationVo> result = new Result<>();
        try {
            StationVo stationVo = deviceDomainService.selectDeviceId(deviceInfoRequest);
            result.setResultObj(stationVo);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<PageInfo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = deviceDomainService.selectDeviceALl(deviceInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        }catch (SystemException e){
            log.info("设备查询异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("设备查询异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }

    private Map<String,String> getAllUrl(String relationOperatorId){
        Map<String,String> map = new HashMap<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample  = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(relationOperatorId).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(relationOperatorId).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            map.put(nmplBaseStationInfo.getLanIp(),nmplBaseStationInfo.getStationId());
        }
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            map.put(nmplDeviceInfo.getLanIp(),nmplDeviceInfo.getDeviceId());
        }
        return  map;
    }
    @Override
    public void pushToProxy(String deviceId,String suffix)throws Exception{
        //推送到代理
        NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
        nmplDeviceExample.createCriteria().andDeviceIdEqualTo(deviceId);
        List<NmplDevice> nmplDeviceInfoList = nmplDeviceMapper.selectByExampleWithBLOBs(nmplDeviceExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfoList)){
            Map<String,String> deviceMap = getAllUrl(nmplDeviceInfoList.get(0).getRelationOperatorId());
            deviceMap.put(nmplDeviceInfoList.get(0).getLanIp(),nmplDeviceInfoList.get(0).getDeviceId());
            Set<String> set = deviceMap.keySet();
            for (String lanIp : set) {
                NmplDevice request = new NmplDevice();
                BeanUtils.copyProperties(nmplDeviceInfoList.get(0),request);
                request.setLocal(false);
                if(lanIp.equals(request.getLanIp())){
                    request.setLocal(true);
                }
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,deviceMap.get(lanIp));
                map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(request));
                String url = "http://"+lanIp+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
        }else {
            log.info("deviceId not found:"+deviceId);
        }

    }



    private void checkParam(DeviceInfoRequest deviceInfoRequest){
        if(!CommonCheckUtil.checkStringLength(deviceInfoRequest.getDeviceName(),null,16)){
            throw new SystemException(DEVICENAME_LENTH_ERROR_MSG);
        }
        boolean publicIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getPublicNetworkIp());
        boolean lanIpReg = CommonCheckUtil.isIpv4Legal(deviceInfoRequest.getLanIp());
        if(publicIpReg == false || lanIpReg == false){
            throw  new SystemException(IP_FORMAT_ERROR_MSG);
        }
        boolean publicPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getPublicNetworkPort());
        boolean lanPortReg = CommonCheckUtil.isPortLegal(deviceInfoRequest.getLanPort());
        if(publicPortReg == false || lanPortReg == false){
            throw  new SystemException(PORT_FORMAT_ERROR_MSG);
        }
        //判断小区是否正确
        String preBID = companyInfoDomainService.getPreBID(deviceInfoRequest.getRelationOperatorId());
        if(StringUtil.isEmpty(preBID)){
            throw  new SystemException(NOT_EXIST_VILLAGE);
        }
        String NetworkId = preBID + "-" + deviceInfoRequest.getStationNetworkId();
        deviceInfoRequest.setStationNetworkId(NetworkId);
    }


}