package com.matrictime.network.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class BaseStationInfoServiceImpl extends SystemBaseService implements BaseStationInfoService {

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private CompanyInfoDomainService companyInfoDomainService;

    @Resource
    private AsyncService asyncService;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;


    @Override
    public Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer insertFlag = null;
        BaseStationInfoRequest infoRequest = new BaseStationInfoRequest();
        try {
            Integer stationNetworkId = nmplBaseStationInfoMapper.getSequenceId();
            baseStationInfoRequest.setStationNetworkId(stationNetworkId.toString());
            if(!CommonCheckUtil.checkStringLength(baseStationInfoRequest.getStationName(),null,16)){
                return new Result<>(false, ErrorMessageContants.SYSTEM_ERROR);
            }
            baseStationInfoRequest.setCreateTime(getFormatDate(date));
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            baseStationInfoRequest.setStationId(SnowFlake.nextId_String());
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            baseStationInfoRequest.setIsExist("1");
            baseStationInfoRequest.setStationStatus(DeviceStatusEnum.NORMAL.getCode());
            //参数格式校验
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
                return new Result<>(false,"小区不存在");
            }
            String networkId = preBID + "-" + baseStationInfoRequest.getStationNetworkId();
            baseStationInfoRequest.setStationNetworkId(networkId);

            insertFlag = baseStationInfoDomainService.insertBaseStationInfo(baseStationInfoRequest);

            if(insertFlag == 1){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
                //推送到代理
                pushToProxy(baseStationInfoRequest.getStationId(),DataConstants.URL_STATION_INSERT);
            }else {
                result.setResultObj(insertFlag);
                result.setSuccess(false);
            }
        }catch (SystemException e){
            log.info("基站创建异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("基站新增{}新增异常：{}",e.getMessage());
            result = failResult("");
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
            //判断小区是否正确
            String preBID = companyInfoDomainService.getPreBID(baseStationInfoRequest.getRelationOperatorId());
            if(StringUtil.isEmpty(preBID)){
                return new Result<>(false,"小区不存在");
            }
            String networkId = preBID + "-" + baseStationInfoRequest.getStationNetworkId();
            baseStationInfoRequest.setStationNetworkId(networkId);

            updateFlag = baseStationInfoDomainService.updateBaseStationInfo(baseStationInfoRequest);
            if(updateFlag == 1){
                result.setSuccess(true);
                result.setResultObj(updateFlag);
                //推送到代理
                pushToProxy(baseStationInfoRequest.getStationId(),DataConstants.URL_STATION_UPDATE);
            }
        }catch (SystemException e){
            log.info("基站修改异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("基站修改异常：{}",e.getMessage());
            result = failResult("");
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
                //推送到代理
                pushToProxy(baseStationInfoRequest.getStationId(),DataConstants.URL_STATION_UPDATE);
            }
        }catch (SystemException e){
            log.info("基站删除异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("基站删除异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 基站推送到代理
     * @param stationId 基站id
     * @param suffix 推送url后缀信息
     * @throws Exception
     */
    private void pushToProxy(String stationId,String suffix)throws Exception{
        //推送到代理
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andStationIdEqualTo(stationId);
        List<NmplBaseStationInfo> nmplBaseStationInfoList = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        Map<String,String> deviceMap = getAllUrl(nmplBaseStationInfoList.get(0).getRelationOperatorId());
        deviceMap.put(nmplBaseStationInfoList.get(0).getLanIp(),nmplBaseStationInfoList.get(0).getStationId());
        Set<String> set = deviceMap.keySet();
        for (String lanIp : set) {
            //默认为非本机
            NmplBaseStationInfo request = new NmplBaseStationInfo();
            BeanUtils.copyProperties(nmplBaseStationInfoList.get(0),request);
            request.setLocal(false);
            if(lanIp.equals(request.getLanIp())){
                request.setLocal(true);
            }
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DEVICE_ID,deviceMap.get(lanIp));
            map.put(DataConstants.KEY_DATA,JSONObject.toJSONString(request));
            String url = "http://"+lanIp+":"+port+contextPath+suffix;
            map.put(DataConstants.KEY_URL,url);
            asyncService.httpPush(map);
        }
    }

    @Override
    public Result<PageInfo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = baseStationInfoDomainService.selectBaseStationInfo(baseStationInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        } catch (Exception e){
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

}