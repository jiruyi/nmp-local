package com.matrictime.network.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.NmplConfigurationMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplConfigExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.request.ResetDefaultConfigReq;
import com.matrictime.network.request.SyncConfigReq;
import com.matrictime.network.response.*;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigExtMapper nmplConfigExtMapper;

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplConfigurationMapper nmplConfigurationMapper;

    @Autowired
    private AsyncService asyncService;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    // TODO: 2022/4/1 上线前需要确定等待时间
    @Value("${config.async.time.out}")
    private int asyncTimeOut;

    @Value("${device.path.config}")
    private String configPath;

    @Override
    public Result<PageInfo> queryConfigByPages(QueryConfigByPagesReq req) {
        Result result;

        try {
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            NmplConfig nmplConfig = new NmplConfig();
            if (StringUtils.isNotBlank(req.getConfigName())){
                StringBuffer sb = new StringBuffer("%").append(req.getConfigName()).append("%");
                nmplConfig.setConfigName(sb.toString());
            }
            List<NmplConfig> nmplConfigs = nmplConfigExtMapper.selectByExample(nmplConfig);
            PageInfo<NmplConfig> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), nmplConfigs);

            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("ConfigServiceImpl.queryConfigByPages Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    @Transactional
    public Result<EditConfigResp> editConfig(EditConfigReq req) {
        Result result;

        try {
            EditConfigResp resp = null;
            // check param is legal
            checkEditConfigParam(req);
            switch (req.getEditType()){
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.insertSelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplConfigVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.updateByPrimaryKeySelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplConfigMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(resp);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.editConfig SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.editConfig Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<ResetDefaultConfigResp> resetDefaultConfig(ResetDefaultConfigReq req) {
        Result result;

        try {
            ResetDefaultConfigResp resp = new ResetDefaultConfigResp();
            checkResetDefaultConfigParam(req);
            List<Long> successIds = new ArrayList<>();
            List<Long> failIds = new ArrayList<>();
            switch (req.getEditRange()){
                case DataConstants.EDIT_RANGE_PART:
                    for (Long id : req.getIds()){
                        NmplConfig tmp = nmplConfigMapper.selectByPrimaryKey(id);
                        if (tmp != null){
                            NmplConfig nmplConfig = new NmplConfig();
                            nmplConfig.setId(id);
                            nmplConfig.setConfigValue(tmp.getDefaultValue());
                            int flag = nmplConfigMapper.updateByPrimaryKeySelective(nmplConfig);
                            if(flag > 0){
                                successIds.add(id);
                                continue;
                            }
                        }
                        failIds.add(id);
                    }
                    break;
                case DataConstants.EDIT_RANGE_ALL:
                    NmplConfigExample example = new NmplConfigExample();
                    List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(nmplConfigs)){
                        for (NmplConfig dto : nmplConfigs){
                            if (StringUtils.isNotEmpty(dto.getDefaultValue())){
                                if (!dto.getDefaultValue().equals(dto.getConfigValue())){
                                    NmplConfig nmplConfig = new NmplConfig();
                                    nmplConfig.setId(dto.getId());
                                    nmplConfig.setConfigValue(dto.getDefaultValue());
                                    int flag = nmplConfigMapper.updateByPrimaryKeySelective(nmplConfig);
                                    if(flag > 0){
                                        successIds.add(dto.getId());
                                        continue;
                                    }
                                }else {
                                    successIds.add(dto.getId());
                                    continue;
                                }

                            }
                            failIds.add(dto.getId());
                        }
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editRange"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.resetDefaultConfig SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.resetDefaultConfig Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<SyncConfigResp> syncConfig(SyncConfigReq req) {
        Result result;

        try {
            SyncConfigResp resp = new SyncConfigResp();
            checkSyncConfigParam(req);
            List<String> successIds = new ArrayList<>();
            List<String> failIds = new ArrayList<>();
            List<Map<String,String>> httpList = new ArrayList<>();
            switch (req.getEditRange()){
                case DataConstants.EDIT_RANGE_PART:
                    for (String deviceId : req.getDeviceIds()){
                        Map<String,String> httpParam = new HashMap<>(8);
                        NmplConfig nmplConfig = nmplConfigMapper.selectByPrimaryKey(req.getConfigId());
                        if (nmplConfig != null){
                            httpParam.put(KEY_DEVICE_ID,deviceId);
                            httpParam.put(KEY_CONFIG_CODE,nmplConfig.getConfigCode());
                            httpParam.put(KEY_CONFIG_VALUE,nmplConfig.getConfigValue());
                            httpParam.put(KEY_UNIT,nmplConfig.getUnit());
                        }
                        NmplConfigurationExample configurationExample = new NmplConfigurationExample();
                        configurationExample.createCriteria().andTypeEqualTo(req.getDeviceType()).andDeviceTypeEqualTo(CONFIGURATION_TYPE_CONFIG);
                        List<NmplConfiguration> configurations = nmplConfigurationMapper.selectByExample(configurationExample);
                        switch (req.getDeviceType()){
                            case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_1:
                                NmplBaseStationInfoExample bExample = new NmplBaseStationInfoExample();
                                bExample.createCriteria().andStationIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bExample);
                                if (!CollectionUtils.isEmpty(stationInfos) && !CollectionUtils.isEmpty(configurations)){
                                    NmplBaseStationInfo info = stationInfos.get(0);
                                    NmplConfiguration nmplConfiguration = configurations.get(0);
                                    httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),nmplConfiguration.getRealPort(),nmplConfiguration.getUrl()));
                                }
                                break;
                            case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_2:
                            case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_3:
                            case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_4:
                                NmplDeviceInfoExample dExample = new NmplDeviceInfoExample();
                                dExample.createCriteria().andDeviceIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dExample);
                                if (!CollectionUtils.isEmpty(deviceInfos) && !CollectionUtils.isEmpty(configurations)){
                                    NmplDeviceInfo info = deviceInfos.get(0);
                                    NmplConfiguration nmplConfiguration = configurations.get(0);
                                    httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),nmplConfiguration.getRealPort(),nmplConfiguration.getUrl()));
                                }
                                break;
                            default:
                                log.warn("device_id:{} device_type is not illegal",deviceId);
                                break;
                        }
                        httpList.add(httpParam);
                    }
                    break;
                case DataConstants.EDIT_RANGE_ALL:
                    NmplConfigExample example = new NmplConfigExample();
                    example.createCriteria().andIsExistEqualTo(IS_EXIST);
                    List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(nmplConfigs)){
                        for (NmplConfig config : nmplConfigs){
                            Map<String,String> httpParam = new HashMap<>(8);
                            httpParam.put(KEY_CONFIG_CODE,config.getConfigCode());
                            httpParam.put(KEY_CONFIG_VALUE,config.getConfigValue());
                            httpParam.put(KEY_UNIT,config.getUnit());

                            NmplConfigurationExample configurationExample = new NmplConfigurationExample();
                            configurationExample.createCriteria().andTypeEqualTo(config.getDeviceType()).andDeviceTypeEqualTo(CONFIGURATION_TYPE_CONFIG);
                            List<NmplConfiguration> configurations = nmplConfigurationMapper.selectByExample(configurationExample);

                            switch (config.getDeviceType()){
                                case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_1:
                                    NmplBaseStationInfoExample bExample = new NmplBaseStationInfoExample();
                                    bExample.createCriteria().andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bExample);
                                    if (!CollectionUtils.isEmpty(stationInfos)){
                                        for (NmplBaseStationInfo info : stationInfos){
                                            httpParam.put(KEY_DEVICE_ID,info.getStationId());
                                            if (!CollectionUtils.isEmpty(configurations)){
                                                httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),configurations.get(0).getRealPort(),configurations.get(0).getUrl()));
                                            }
                                            httpList.add(httpParam);
                                        }
                                    }
                                    break;
                                case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_2:
                                case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_3:
                                case com.matrictime.network.base.constant.DataConstants.CONFIG_DEVICE_TYPE_4:
                                    NmplDeviceInfoExample dExample = new NmplDeviceInfoExample();
                                    dExample.createCriteria().andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dExample);
                                    if (!CollectionUtils.isEmpty(deviceInfos)){
                                        for (NmplDeviceInfo info : deviceInfos){
                                            httpParam.put(KEY_DEVICE_ID,info.getDeviceId());
                                            if (!CollectionUtils.isEmpty(configurations)){
                                                httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),configurations.get(0).getRealPort(),configurations.get(0).getUrl()));
                                            }
                                            httpList.add(httpParam);
                                        }
                                    }
                                    break;
                                default:
                                    log.warn("config:{} device_type is not illegal",config.getId());
                                    break;
                            }
                        }
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editRange"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            if (!CollectionUtils.isEmpty(httpList)){
                List<Future> futures = new ArrayList<>();
                if (httpList.size()>maxPoolSize){
                    List<List<Map<String, String>>> splitList = splitList(httpList);
                    for (List<Map<String, String>> tmpList : splitList){
                        Future<Map<String, List<String>>> mapFuture = asyncService.httpSyncConfig(tmpList);
                        futures.add(mapFuture);
                    }
                }else {
                    Future<Map<String, List<String>>> mapFuture = asyncService.httpSyncConfig(httpList);
                    futures.add(mapFuture);
                }

                Date timeout = DateUtils.addMinuteForDate(new Date(), asyncTimeOut);
                while (true) {
                    if (!CollectionUtils.isEmpty(futures)) {
                        boolean isAllDone = true;
                        for (Future future : futures) {
                            if (null == future || !future.isDone()) {
                                isAllDone = false;
                            }else {
                                try {
                                    Map<String, List<String>> msg =  (Map<String, List<String>>) future.get();
                                    if (!CollectionUtils.isEmpty(msg)) {
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_SUCCESS_IDS))){
                                            successIds.addAll(msg.get(KEY_SUCCESS_IDS));
                                        }
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_FAIL_IDS))){
                                            failIds.addAll(msg.get(KEY_FAIL_IDS));
                                        }
                                    }
                                } catch (Exception e) {
                                    log.info("同步配置线程池处理单个批次配置出错！error:{}",e.getMessage());
                                }
                            }
                        }
                        if (isAllDone || new Date().after(timeout)) {
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }

            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.syncConfig SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.syncConfig Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result httpSyncConfig(Map<String,String> map) {
        Result result;
        try {
//            Map<String ,String> httpParam = new HashMap<>(4);
//            httpParam.put("configCode",map.get("configCode"));
//            httpParam.put("configValue",map.get("configValue"));
//            httpParam.put("unit",map.get("unit"));
//            HttpClientUtil.post(map.get("ip"),map.get("port"),"path",httpParam);
            result = buildResult(null);
        }catch (Exception e){
            log.warn("ConfigServiceImpl.httpSyncConfig Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    private List<List<Map<String,String>>> splitList(List<Map<String,String>> list){
        int length = list.size();
        /**
         * num 可以分成的组数
         **/
        int num = ( length + maxPoolSize - 1 )/maxPoolSize ;
        //用于存放最后结果
        List<List<Map<String,String>>> resultList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * maxPoolSize;
            // 结束位置
            int toIndex = (i+1) * maxPoolSize < length ? ( i+1 ) * maxPoolSize : length ;
            resultList.add(list.subList(fromIndex,toIndex)) ;
        }
        return resultList;
    }

    private void checkEditConfigParam(EditConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplConfigVos())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkResetDefaultConfigParam(ResetDefaultConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditRange())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_RANGE_PART.equals(req.getEditRange()) && CollectionUtils.isEmpty(req.getIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkSyncConfigParam(SyncConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditRange())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_RANGE_PART.equals(req.getEditRange())){
            if (req.getConfigId() == null){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "configId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (CollectionUtils.isEmpty(req.getDeviceIds())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }


}
