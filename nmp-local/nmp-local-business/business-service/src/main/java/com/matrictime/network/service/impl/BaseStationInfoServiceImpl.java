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
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplBaseStationMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.BorderBaseStationInfoVo;
import com.matrictime.network.modelVo.CommunityBaseStationVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BorderBaseStationInfoRequest;
import com.matrictime.network.request.CurrentCountRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.BelongInformationResponse;
import com.matrictime.network.response.CountBaseStationResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.INSERT_OR_UPDATE_SUCCESS;
import static com.matrictime.network.base.exception.ErrorMessageContants.*;


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

    @Resource
    private NmplBaseStationMapper nmplBaseStationMapper;

    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Override
    public Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer insertFlag = null;
        try {
            baseStationInfoRequest.setStationNetworkId(String.valueOf(nmplBaseStationInfoMapper.getSequenceId()));
            baseStationInfoRequest.setCreateTime(getFormatDate(date));
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            baseStationInfoRequest.setStationId(SnowFlake.nextId_String());
            baseStationInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            baseStationInfoRequest.setIsExist("1");
            baseStationInfoRequest.setStationStatus(DeviceStatusEnum.NORMAL.getCode());

            //校验参数
            checkParam(baseStationInfoRequest);

            baseStationInfoRequest.setByteNetworkId(DecimalConversionUtil.idToByteArray(baseStationInfoRequest.getStationNetworkId()));
            baseStationInfoRequest.setPrefixNetworkId(DecimalConversionUtil.getPreBid(baseStationInfoRequest.getByteNetworkId()));
            baseStationInfoRequest.setSuffixNetworkId(DecimalConversionUtil.getSuffBid(baseStationInfoRequest.getByteNetworkId()));

            insertFlag = baseStationInfoDomainService.insertBaseStationInfo(baseStationInfoRequest);

            if(insertFlag.equals(INSERT_OR_UPDATE_SUCCESS)){
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
            baseStationInfoRequest.setUpdateTime(getFormatDate(date));
            //参数校验
            checkParam(baseStationInfoRequest);

            updateFlag = baseStationInfoDomainService.updateBaseStationInfo(baseStationInfoRequest);
            if(updateFlag.equals(INSERT_OR_UPDATE_SUCCESS)){
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
            if(RequestContext.getUser().getRoleId().equals(DataConstants.SUPER_ADMIN)){
                throw new SystemException(NO_ADMIN_ERROR_MSG);
            }
            deleteFlag = baseStationInfoDomainService.deleteBaseStationInfo(baseStationInfoRequest);
            if(deleteFlag.equals(INSERT_OR_UPDATE_SUCCESS)){
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
    @Override
    public void pushToProxy(String stationId, String suffix)throws Exception{
        //推送到代理
        NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
        nmplBaseStationExample.createCriteria().andStationIdEqualTo(stationId);
        List<NmplBaseStation> nmplBaseStationInfoList = nmplBaseStationMapper.selectByExampleWithBLOBs(nmplBaseStationExample);
        if(!CollectionUtils.isEmpty(nmplBaseStationInfoList)){
            Map<String,String> deviceMap = getAllUrl(nmplBaseStationInfoList.get(0).getRelationOperatorId());
            deviceMap.put(nmplBaseStationInfoList.get(0).getLanIp(),nmplBaseStationInfoList.get(0).getStationId());
            Set<String> set = deviceMap.keySet();
            for (String lanIp : set) {
                //默认为非本机
                NmplBaseStation request = new NmplBaseStation();
                BeanUtils.copyProperties(nmplBaseStationInfoList.get(0),request);
                request.setIsLocal(false);
                if(lanIp.equals(request.getLanIp())){
                    request.setIsLocal(true);
                }
                String data = "";
                if(suffix.equals(DataConstants.URL_STATION_INSERT)){
                    data = firstPushData(request);
                }else {
                    data = JSONObject.toJSONString(request);
                }
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,deviceMap.get(lanIp));
                map.put(DataConstants.KEY_DATA,data);
                String url = "http://"+lanIp+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
        }else {
            log.info("stationId not found:"+stationId);
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

    /**
     * 根据前端页面条件自动关联获取基站信息
     */
    @Override
    public Result<BaseStationInfoResponse> selectForRoute(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        try {
            baseStationInfoResponse.setBaseStationInfoList(baseStationInfoDomainService.
                    selectForRoute(baseStationInfoRequest));
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
        nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
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
    private String firstPushData(NmplBaseStation nmplBaseStation){
        JSONObject jsonObject = new JSONObject();
        if(nmplBaseStation.getIsLocal()){
            List<NmplBaseStation> nmplBaseStationInfos = nmplBaseStationMapper.selectByExampleWithBLOBs(null);
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            nmplDeviceExample.createCriteria().andRelationOperatorIdEqualTo(nmplBaseStation.getRelationOperatorId());
            List<NmplDevice> nmplDeviceInfos = nmplDeviceMapper.selectByExampleWithBLOBs(nmplDeviceExample);
            jsonObject.put("stationInfoVos",nmplBaseStationInfos);
            jsonObject.put("deviceInfoVos",nmplDeviceInfos);
        }
        jsonObject.put("localBaseInfo",nmplBaseStation);
        return jsonObject.toJSONString();
    }


    private void checkParam(BaseStationInfoRequest baseStationInfoRequest){
        if(!CommonCheckUtil.checkStringLength(baseStationInfoRequest.getStationName(),null,16)){
           throw new SystemException(PARAM_LENTH_ERROR_MSG);
        }
        //参数格式校验
        boolean publicIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getPublicNetworkIp());
        boolean lanIpReg = CommonCheckUtil.isIpv4Legal(baseStationInfoRequest.getLanIp());
        if(publicIpReg == false || lanIpReg == false){
           throw  new SystemException(IP_FORMAT_ERROR_MSG);
        }
        boolean publicPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getPublicNetworkPort());
        boolean lanPortReg = CommonCheckUtil.isPortLegal(baseStationInfoRequest.getLanPort());
        if(publicPortReg == false || lanPortReg == false){
            throw  new SystemException(PORT_FORMAT_ERROR_MSG);
        }
        //获取BID前缀信息
        String preBID = companyInfoDomainService.getPreBID(baseStationInfoRequest.getRelationOperatorId());
        if(StringUtil.isEmpty(preBID)){
            throw  new SystemException(NOT_EXIST_VILLAGE);
        }
        String networkId = preBID + "-" + baseStationInfoRequest.getStationNetworkId();
        baseStationInfoRequest.setStationNetworkId(networkId);

    }

    private void checkBorderBaseStationParam(BorderBaseStationInfoRequest borderBaseStationInfoRequest){
        if(!CommonCheckUtil.checkStringLength(borderBaseStationInfoRequest.getStationName(),null,16)){
            throw new SystemException(PARAM_LENTH_ERROR_MSG);
        }

        //获取BID前缀信息
        String preBID = companyInfoDomainService.getPreBID(borderBaseStationInfoRequest.getRelationOperatorId());
        if(StringUtil.isEmpty(preBID)){
            throw  new SystemException(NOT_EXIST_VILLAGE);
        }
        String networkId = preBID + "-" + borderBaseStationInfoRequest.getStationNetworkId();
        borderBaseStationInfoRequest.setStationNetworkId(networkId);

    }


    @Override
    public void initBaseStation() {
        NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
        nmplBaseStationExample.createCriteria().andStationStatusEqualTo(DeviceStatusEnum.ACTIVE.getCode()).andIsExistEqualTo(true);
        nmplBaseStationExample.or().andStationStatusEqualTo(DeviceStatusEnum.NOAUDIT.getCode()).andIsExistEqualTo(true);
        List<NmplBaseStation> nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
        for (NmplBaseStation nmplBaseStation : nmplBaseStations) {
            nmplBaseStation.setStationStatus(DeviceStatusEnum.OFFLINE.getCode());
            log.info("baseStation init deviceId:"+nmplBaseStation.getStationId());
            nmplBaseStationMapper.updateByPrimaryKeySelective(nmplBaseStation);
        }
    }

    /**
     * 查询归属信息
     * @return
     */
    @Override
    public Result<BelongInformationResponse> selectBelongInformation() {

        Result<BelongInformationResponse> result = new Result<>();
        try {
            BelongInformationResponse belongInformationResponse = baseStationInfoDomainService.selectBelongInformation();
            result.setResultObj(belongInformationResponse);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectBelongInformation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    /**
     * 查询基站总数
     * @param baseStationInfoRequest
     * @return
     */
    @Override
    public Result<CountBaseStationResponse> countBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        Result<CountBaseStationResponse> result = new Result<>();
        try {
            result.setResultObj(baseStationInfoDomainService.countBaseStation(baseStationInfoRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("countBaseStation:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateConnectCount(BaseStationCountRequest baseStationCountRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(baseStationInfoDomainService.updateConnectCount(baseStationCountRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("updateConnectCount:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 查询不同Ip物理设备
     * @param baseStationInfoRequest
     * @return
     */
    @Override
    public Result<List<CommunityBaseStationVo>> selectPhysicalDevice(BaseStationInfoRequest baseStationInfoRequest) {
        Result<List<CommunityBaseStationVo>> result = new Result<>();
        try {
            List<CommunityBaseStationVo> list = baseStationInfoDomainService.selectPhysicalDevice(baseStationInfoRequest);
            result.setSuccess(true);
            result.setResultObj(list);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("selectPhysicalDevice:{}",e.getMessage());
        }

        return result;
    }

    /**
     * 更新当前用户数
     * @param currentCountRequest
     * @return
     */
    @Override
    public Result<Integer> updateCurrentConnectCount(CurrentCountRequest currentCountRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(baseStationInfoDomainService.updateCurrentConnectCount(currentCountRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("updateCurrentConnectCount:{}",e.getMessage());
        }

        return result;
    }

    /**
     * 归属信息查询
     * @return
     */
    @Override
    public Result<BelongInformationResponse> selectAllBelongInformation() {
        Result<BelongInformationResponse> result = new Result<>();
        try {
            BelongInformationResponse belongInformationResponse = baseStationInfoDomainService.selectAllBelongInformation();
            result.setResultObj(belongInformationResponse);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectAllBelongInformation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    @Override
    public Result<Integer> insertBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        Integer insertFlag = null;
        try {
            borderBaseStationInfoRequest.setStationNetworkId(String.valueOf(nmplBaseStationInfoMapper.getSequenceId()));
            borderBaseStationInfoRequest.setCreateTime(getFormatDate(date));
            borderBaseStationInfoRequest.setUpdateTime(getFormatDate(date));
            borderBaseStationInfoRequest.setStationId(SnowFlake.nextId_String());
            borderBaseStationInfoRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            borderBaseStationInfoRequest.setIsExist("1");
            borderBaseStationInfoRequest.setStationStatus(DeviceStatusEnum.NORMAL.getCode());

            //校验参数
            checkBorderBaseStationParam(borderBaseStationInfoRequest);

            borderBaseStationInfoRequest.setByteNetworkId(DecimalConversionUtil.idToByteArray(borderBaseStationInfoRequest.getStationNetworkId()));
            borderBaseStationInfoRequest.setPrefixNetworkId(DecimalConversionUtil.getPreBid(borderBaseStationInfoRequest.getByteNetworkId()));
            borderBaseStationInfoRequest.setSuffixNetworkId(DecimalConversionUtil.getSuffBid(borderBaseStationInfoRequest.getByteNetworkId()));

            insertFlag = baseStationInfoDomainService.insertBorderBaseStation(borderBaseStationInfoRequest);

            if(insertFlag.equals(INSERT_OR_UPDATE_SUCCESS)){
                result.setResultObj(insertFlag);
                result.setSuccess(true);
                //推送到代理
                pushToProxy(borderBaseStationInfoRequest.getStationId(),DataConstants.URL_STATION_INSERT);
            }else {
                result.setResultObj(insertFlag);
                result.setSuccess(false);
            }
        }catch (SystemException e){
            log.info("边界基站创建异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("边界基站新增{}新增异常：{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 删除边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public Result<Integer> deleteBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        try {
            Integer deleteFlag = baseStationInfoDomainService.deleteBorderBaseStation(borderBaseStationInfoRequest);
            if(deleteFlag.equals(INSERT_OR_UPDATE_SUCCESS)){
                result.setSuccess(true);
                result.setResultObj(deleteFlag);
                //推送到代理
                pushToProxy(borderBaseStationInfoRequest.getStationId(),DataConstants.URL_STATION_UPDATE);
            }
        }catch (Exception e){
            log.info("deleteBorderBaseStation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 更新边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public Result<Integer> updateBorderBaseStation(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        Result<Integer> result = new Result<>();
        try {
            Integer i = baseStationInfoDomainService.updateBorderBaseStation(borderBaseStationInfoRequest);
            if(i.equals(INSERT_OR_UPDATE_SUCCESS)){
                result.setSuccess(true);
                result.setResultObj(i);
                //推送到代理
                pushToProxy(borderBaseStationInfoRequest.getStationId(),DataConstants.URL_STATION_UPDATE);
            }
        }catch (Exception e){
            log.info("updateBorderBaseStation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询边界基站
     * @param borderBaseStationInfoRequest
     * @return
     */
    @Override
    public Result<PageInfo> selectBorderBaseStationInfo(BorderBaseStationInfoRequest borderBaseStationInfoRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            PageInfo pageInfo = baseStationInfoDomainService.selectBorderBaseStationInfo(borderBaseStationInfoRequest);
            result.setResultObj(pageInfo);
            result.setSuccess(true);
        } catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }


}