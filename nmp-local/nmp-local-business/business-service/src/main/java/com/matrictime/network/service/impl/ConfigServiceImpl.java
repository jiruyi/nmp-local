package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import com.matrictime.network.request.*;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.KEY_FAIL_IDS;
import static com.matrictime.network.constant.DataConstants.KEY_SUCCESS_IDS;
import static com.matrictime.network.exception.ErrorMessageContants.DO_NOT_GET_CONFIG;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    @Autowired
    private AsyncService asyncService;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    // TODO: 2022/4/1 上线前需要确定等待时间
    @Value("${config.async.time.out}")
    private int asyncTimeOut;

    @Value("${proxy.context-path}")
    private String proxyPath;

    @Value("${proxy.port}")
    private String proxyPort;

    @Value("${switch.configCode}")
    private String dataSwitch;

    /**
     * 系统设置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryConfigByPages(QueryConfigByPagesReq req) {
        Result result;

        try {
            // 校验必传参数
            checkQueryConfigByPagesParam(req);
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据系统类型（必传）和配置名称（非必传）查询配置列表
            NmplConfigExample configExample = new NmplConfigExample();
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            configExample.setOrderByClause("update_time desc");
            criteria.andDeviceTypeEqualTo(req.getDeviceType());
            if (StringUtils.isNotBlank(req.getConfigName())){
                StringBuffer sb = new StringBuffer(KEY_PERCENT).append(req.getConfigName()).append(KEY_PERCENT);
                criteria.andConfigNameLike(sb.toString());
            }
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(configExample);

            // 列表分页
            PageInfo<NmplConfig> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), nmplConfigs);

            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("ConfigServiceImpl.queryConfigByPages Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    /**
     * 编辑系统设置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<EditConfigResp> editConfig(EditConfigReq req) {
        Result result;

        try {
            EditConfigResp resp = null;
            // check param is legal
            checkEditConfigParam(req);
            switch (req.getEditType()){
                // 批量插入（暂未使用）
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.insertSelective(config);
                    }
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("nmplConfigVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.updateByPrimaryKeySelective(config);
                    }
                    break;
                // 批量删除
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplConfigMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.editConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 恢复默认接口（支持全量恢复）
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<ResetDefaultConfigResp> resetDefaultConfig(ResetDefaultConfigReq req) {
        Result result;
        try {
            ResetDefaultConfigResp resp = new ResetDefaultConfigResp();
            // 入参校验
            checkResetDefaultConfigParam(req);
            List<Long> successIds = new ArrayList<>();
            List<Long> failIds = new ArrayList<>();
            switch (req.getEditRange()){
                // 非全量恢复默认
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
                // 全量恢复默认
                case DataConstants.EDIT_RANGE_ALL:
                    NmplConfigExample example = new NmplConfigExample();
                    example.createCriteria().andDeviceTypeEqualTo(req.getDeviceType()).andConfigCodeNotEqualTo(dataSwitch).andIsExistEqualTo(IS_EXIST);
                    List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(nmplConfigs)){
                        for (NmplConfig dto : nmplConfigs){
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
                            failIds.add(dto.getId());
                        }
                    }
                    break;
                default:
                    throw new Exception("editRange"+ PARAM_IS_UNEXPECTED_MSG);
            }

            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.resetDefaultConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 同步配置
     * @param req
     * @return
     */
    @Override
    public Result<SyncConfigResp> syncConfig(SyncConfigReq req) {
        Result result;

        try {
            SyncConfigResp resp = new SyncConfigResp();
            checkSyncConfigParam(req);

            List<String> successIds = new ArrayList<>();// 返回成功id列表
            List<String> failIds = new ArrayList<>();// 返回失败id列表
            List<Map<String,Object>> httpList = getHttpList(req);// 同步多线程处理业务列表

            if (!CollectionUtils.isEmpty(httpList)){
                List<Future> futures = new ArrayList<>();
                if (httpList.size()>maxPoolSize){
                    List<List<Map<String, Object>>> splitList = splitList(httpList);
                    for (List<Map<String, Object>> tmpList : splitList){
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
            result = failResult(e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.syncConfig Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 数据采集参数配置查询
     * @return
     */
    @Override
    public Result<QueryDataCollectResp> queryDataCollect() {
        Result result;

        try {
            QueryDataCollectResp resp = new QueryDataCollectResp();
            // 查询数据采集自动上报基础配置
            NmplConfigExample configExample = new NmplConfigExample();
            configExample.setOrderByClause("update_time desc");
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            criteria.andDeviceTypeEqualTo(DeviceTypeEnum.DATA_BASE.getCode()).andIsExistEqualTo(IS_EXIST);
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(configExample);
            List<NmplConfigVo> configVos = new ArrayList<>();

            for(NmplConfig config : nmplConfigs){
                NmplConfigVo configVo = new NmplConfigVo();
                BeanUtils.copyProperties(config,configVo);
                if(dataSwitch.equals(config.getConfigCode())){// 如果是数据采集开关则单独处理
                    resp.setCollectSwitch(configVo);
                }else {
                    configVos.add(configVo);
                }
            }
            resp.setConfigVos(configVos);

            // 查询数据采集上报业务配置
            NmplReportBusinessExample reportExample = new NmplReportBusinessExample();
            reportExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(reportExample);
            List<NmplReportBusinessVo> reportBusinessVos = new ArrayList<>();
            for (NmplReportBusiness reportBusiness : nmplReportBusinesses){
                NmplReportBusinessVo vo = new NmplReportBusinessVo();
                BeanUtils.copyProperties(reportBusiness,vo);
                reportBusinessVos.add(vo);
            }
            resp.setReportBusinessVos(reportBusinessVos);

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.queryDataCollect Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    /**
     * 恢复数据采集上报业务配置接口（支持全量恢复）
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<ResetDataBusinessConfigResp> resetDataBusinessConfig(ResetDataBusinessConfigReq req) {
        Result result;
        try {
            ResetDataBusinessConfigResp resp = new ResetDataBusinessConfigResp();
            // 入参校验
            checkResetDataBusinessParam(req);
            List<Long> successIds = new ArrayList<>();
            List<Long> failIds = new ArrayList<>();
            switch (req.getEditRange()){
                // 非全量恢复默认(预留代码，暂未使用)
                case DataConstants.EDIT_RANGE_PART:
                    for (Long id : req.getIds()){
                        NmplReportBusiness reportBusiness = nmplReportBusinessMapper.selectByPrimaryKey(id);
                        if (reportBusiness != null){
                            if (!reportBusiness.getBusinessValue().equals(reportBusiness.getDefaultValue())){
                                reportBusiness.setBusinessValue(reportBusiness.getDefaultValue());
                                int flag = nmplReportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
                                if(flag > 0){
                                    successIds.add(id);
                                    continue;
                                }
                            }else {
                                successIds.add(id);
                                continue;
                            }
                        }
                        failIds.add(id);
                    }
                    break;
                // 全量恢复默认
                case DataConstants.EDIT_RANGE_ALL:
                    NmplReportBusinessExample example = new NmplReportBusinessExample();
                    example.createCriteria().andIsExistEqualTo(IS_EXIST);
                    List<NmplReportBusiness> reportBusinesses = nmplReportBusinessMapper.selectByExample(example);
                    for (NmplReportBusiness reportBusiness : reportBusinesses){
                        if (!reportBusiness.getDefaultValue().equals(reportBusiness.getBusinessValue())){
                            NmplReportBusiness dto = new NmplReportBusiness();
                            dto.setId(reportBusiness.getId());
                            dto.setBusinessValue(reportBusiness.getDefaultValue());
                            int flag = nmplReportBusinessMapper.updateByPrimaryKeySelective(dto);
                            if(flag > 0){
                                successIds.add(reportBusiness.getId());
                                continue;
                            }
                        }else {
                            successIds.add(reportBusiness.getId());
                            continue;
                        }
                        failIds.add(reportBusiness.getId());
                    }

                    break;
                default:
                    throw new Exception("editRange"+ PARAM_IS_UNEXPECTED_MSG);
            }

            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.resetDefaultConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 编辑上报业务配置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<EditConfigResp> editDataBusinessConfig(EditDataBusinessConfigReq req) {
        Result result;

        try {
            EditConfigResp resp = null;
            // check param is legal
            checkEditDataBusinessConfigParam(req);
            switch (req.getEditType()){
                // 批量插入（暂未使用）
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplReportBusinessVo vo : req.getNmplReportBusinessVos()){
                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
                        BeanUtils.copyProperties(vo,reportBusiness);
                        nmplReportBusinessMapper.insertSelective(reportBusiness);
                    }
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplReportBusinessVo vo : req.getNmplReportBusinessVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("nmplReportBusinessVo.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
                        BeanUtils.copyProperties(vo,reportBusiness);
                        nmplReportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
                    }
                    break;
                // 批量删除（暂时未使用）
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplReportBusinessMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.editDataBusinessConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    private List<Map<String,Object>> getHttpList(SyncConfigReq req) throws Exception{
        List<Map<String,Object>> httpList = new ArrayList<>();

        List<NmplConfigVo> configVos = new ArrayList<>();
        // 根据配置同步范围查询配置信息
        switch (req.getConfigRange()){
            case CONFIG_RANGE_SINGLE:// 单条根据配置id查找配置信息
                NmplConfig nmplConfig = nmplConfigMapper.selectByPrimaryKey(req.getConfigId());
                if (nmplConfig != null){
                    NmplConfigVo vo = new NmplConfigVo();
                    BeanUtils.copyProperties(nmplConfig,vo);
                    configVos.add(vo);
                }
                break;
            case CONFIG_RANGE_ALL:// 全量根据设备类型和有效标志位查找配置信息
                NmplConfigExample example = new NmplConfigExample();
                example.createCriteria().andDeviceTypeEqualTo(req.getDeviceType()).andIsExistEqualTo(IS_EXIST);
                List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(nmplConfigs)){
                    for (NmplConfig config : nmplConfigs){
                        NmplConfigVo vo = new NmplConfigVo();
                        BeanUtils.copyProperties(config,vo);
                        configVos.add(vo);
                    }
                }
                break;
            default:
                throw new Exception("configRange"+PARAM_IS_UNEXPECTED_MSG);
        }
        if (CollectionUtils.isEmpty(configVos)){// 没有找到配置信息直接跳出方法
            throw new SystemException(ErrorCode.SYSTEM_ERROR,DO_NOT_GET_CONFIG);
        }

        // 查询同步到对应代理的设备信息
        if (req.getDeviceType().equals(DeviceTypeEnum.STATION_INSIDE.getCode()) ||
        req.getDeviceType().equals(DeviceTypeEnum.STATION_BOUNDARY.getCode())){// 基站
            List<NmplBaseStationInfo> stationInfos;
            switch (req.getEditRange()){
                case DataConstants.EDIT_RANGE_PART:
                    NmplBaseStationInfoExample pExample = new NmplBaseStationInfoExample();
                    pExample.createCriteria().andStationIdIn(req.getDeviceIds()).andIsExistEqualTo(IS_EXIST);
                    stationInfos = nmplBaseStationInfoMapper.selectByExample(pExample);
                    break;
                case DataConstants.EDIT_RANGE_ALL:
                    NmplBaseStationInfoExample aExample = new NmplBaseStationInfoExample();
                    aExample.createCriteria().andIsExistEqualTo(IS_EXIST);
                    stationInfos = nmplBaseStationInfoMapper.selectByExample(aExample);
                    break;
                default:
                    throw new Exception("editRange"+ PARAM_IS_UNEXPECTED_MSG);
            }

            if (!CollectionUtils.isEmpty(stationInfos)){
                for (NmplBaseStationInfo info:stationInfos){
                    Map<String,Object> httpParam = new HashMap<>(8);
                    httpParam.put(KEY_CONFIGVOS, configVos);
                    httpParam.put(KEY_DEVICE_TYPE, req.getDeviceType());
                    httpParam.put(KEY_DEVICE_ID,info.getStationId());
                    httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),proxyPort,proxyPath+SYNC_CONFIG_URL));
                    httpList.add(httpParam);
                }
            }
        }else if (req.getDeviceType().equals(DeviceTypeEnum.DEVICE_DISPENSER.getCode())){// 其他设备
            List<NmplDeviceInfo> deviceInfos;
            switch (req.getEditRange()){
                case DataConstants.EDIT_RANGE_PART:
                    NmplDeviceInfoExample pExample = new NmplDeviceInfoExample();
                    pExample.createCriteria().andDeviceIdIn(req.getDeviceIds()).andIsExistEqualTo(IS_EXIST);
                    deviceInfos = nmplDeviceInfoMapper.selectByExample(pExample);
                    break;
                case DataConstants.EDIT_RANGE_ALL:
                    NmplDeviceInfoExample aExample = new NmplDeviceInfoExample();
                    aExample.createCriteria().andIsExistEqualTo(IS_EXIST);
                    deviceInfos = nmplDeviceInfoMapper.selectByExample(aExample);
                    break;
                default:
                    throw new Exception("editRange"+ PARAM_IS_UNEXPECTED_MSG);
            }
            if (!CollectionUtils.isEmpty(deviceInfos)){
                for (NmplDeviceInfo info:deviceInfos){
                    Map<String,Object> httpParam = new HashMap<>(8);
                    httpParam.put(KEY_CONFIGVOS, configVos);
                    httpParam.put(KEY_DEVICE_TYPE, req.getDeviceType());
                    httpParam.put(KEY_DEVICE_ID,info.getDeviceId());
                    httpParam.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),proxyPort,proxyPath+SYNC_CONFIG_URL));
                    httpList.add(httpParam);
                }
            }
        }
        return httpList;
    }


    private List<List<Map<String,Object>>> splitList(List<Map<String,Object>> list){
        int length = list.size();
        /**
         * num 可以分成的组数
         **/
        int num = ( length + maxPoolSize - 1 )/maxPoolSize ;
        //用于存放最后结果
        List<List<Map<String,Object>>> resultList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * maxPoolSize;
            // 结束位置
            int toIndex = (i+1) * maxPoolSize < length ? ( i+1 ) * maxPoolSize : length ;
            resultList.add(list.subList(fromIndex,toIndex)) ;
        }
        return resultList;
    }

    private void checkQueryConfigByPagesParam(QueryConfigByPagesReq req) throws Exception{
        // 校验设备类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
            throw new Exception("DeviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkEditConfigParam(EditConfigReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplConfigVos())){
                throw new Exception("nmplConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


    private void checkEditDataBusinessConfigParam(EditDataBusinessConfigReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplReportBusinessVos())){
                throw new Exception("nmplReportBusinessVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


    private void checkResetDefaultConfigParam(ResetDefaultConfigReq req) throws Exception{
        String editRange = req.getEditRange();
        // 校验数据操作范围入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(editRange)){
            throw new Exception("EditRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 非全量修改时校验id列表入参是否合法
        if (EDIT_RANGE_PART.equals(editRange) && CollectionUtils.isEmpty(req.getIds())){
            throw new Exception("Ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 全量修改时校验设备类型入参是否合法
        if (EDIT_RANGE_ALL.equals(editRange)){
            String deviceType = req.getDeviceType();
            if (ParamCheckUtil.checkVoStrBlank(deviceType)){
                throw new Exception("DeviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }else {
                if (!DeviceTypeEnum.STATION_INSIDE.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.STATION_BOUNDARY.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.DEVICE_DISPENSER.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.DATA_BASE.getCode().equals(deviceType)){
                    throw new Exception("DeviceType"+ PARAM_IS_UNEXPECTED_MSG);
                }
            }
        }
    }

    private void checkResetDataBusinessParam(ResetDataBusinessConfigReq req) throws Exception{
        String editRange = req.getEditRange();
        // 校验数据操作范围入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(editRange)){
            throw new Exception("EditRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 非全量修改时校验id列表入参是否合法
        if (EDIT_RANGE_PART.equals(editRange) && CollectionUtils.isEmpty(req.getIds())){
            throw new Exception("Ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkSyncConfigParam(SyncConfigReq req) throws Exception{
        if (ParamCheckUtil.checkVoStrBlank(req.getEditRange())){
            throw new Exception("editRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_RANGE_PART.equals(req.getEditRange())){
            if (req.getConfigId() == null){
                throw new Exception("configId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
                throw new Exception("deviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (CollectionUtils.isEmpty(req.getDeviceIds())){
                throw new Exception("deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }


}
