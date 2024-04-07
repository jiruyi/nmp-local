package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.ConfigDomainService;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.service.DeviceConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.matrictime.network.constant.BusinessConsts.SWITCH_CONFIGCODE;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired(required = false)
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    @Autowired
    private ConfigDomainService configDomainService;

    @Autowired
    private DeviceConfigService deviceConfigService;

    @Resource
    private NmplDeviceConfigMapper deviceConfigMapper;

    @Autowired
    private DeviceExtraInfoDomainService deviceExtraInfoDomainService;

    /**
     * 系统设置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryConfigByPages(QueryConfigByPagesReq req) {
        Result result;

        try {
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据系统类型（必传）和配置名称（非必传）查询配置列表
            NmplConfigExample configExample = new NmplConfigExample();
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            configExample.setOrderByClause("update_time desc");
            if (!ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
                criteria.andDeviceTypeEqualTo(req.getDeviceType());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getConfigName())){
                StringBuffer sb = new StringBuffer(KEY_PERCENT).append(req.getConfigName()).append(KEY_PERCENT);
                criteria.andConfigNameLike(sb.toString());
            }
            criteria.andConfigCodeNotEqualTo(SWITCH_CONFIGCODE);
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
    public Result<SyncConfigResp> sync(SyncConfigReq req) {
        Result result;

        try {
            SyncConfigResp resp = new SyncConfigResp();
            List<ConfigVo> vos = new ArrayList<>();
            Map<String,List<ConfigVo>> taskMap = new HashMap<>();
            List<ConfigVo> configVos = req.getConfigVos();
            List<ConfigVo> tempTask = new ArrayList<>();
            for (int i = 0; i< configVos.size(); i++){
                ConfigVo vo = configVos.get(i);
                String mapKey = vo.getDeviceType();
                // 校验id是否为空
                if (vo.getId() == null){
                    throw new Exception("TaskConfigVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                vos.add(vo);
                if (taskMap.containsKey(mapKey)){
                    tempTask = taskMap.get(mapKey);
                }
                tempTask.add(vo);
                taskMap.put(mapKey,tempTask);
            }
            Integer upd = configDomainService.update(vos);
            NmplDeviceConfigExample configExample = new NmplDeviceConfigExample();
            configExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceConfig> deviceConfigs = deviceConfigMapper.selectByExample(configExample);
            List<TaskVo> taskVos = getTaskVo(taskMap,deviceConfigs);
            List<Map<String, Boolean>> futures = deviceConfigService.pushConfigToProxy(taskVos);

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.sync Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 系统配置同步数据重组
     * @param taskMap
     * @return
     */
    private List<TaskVo> getTaskVo(Map<String,List<ConfigVo>> taskMap,List<NmplDeviceConfig> deviceConfigs){
        List<TaskVo> taskVos = new ArrayList<>();
        Iterator<Map.Entry<String, List<ConfigVo>>> iterator = taskMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<ConfigVo>> entry = iterator.next();
            String key = entry.getKey();
            List<ConfigVo> value = entry.getValue();
            List<DeviceInfoVo> devices = deviceExtraInfoDomainService.getDeviceByDeviceType(key);
            for (DeviceInfoVo deviceInfoVo:devices){
                TaskVo vo = new TaskVo();
                vo.setIp(deviceInfoVo.getLanIp());
                vo.setDeviceType(key);
                JSONObject data = new JSONObject();
                value = resetValue(deviceConfigs,value,deviceInfoVo.getDeviceId());
                data.put("configVos",value);
                vo.setData(data.toJSONString());
                taskVos.add(vo);
            }
        }
        return taskVos;
    }

    /**
     * 配置推送到网元上的值
     * @param deviceConfigs
     * @param configs
     * @param deviceId
     * @return
     */
    private List<ConfigVo> resetValue(List<NmplDeviceConfig> deviceConfigs, List<ConfigVo> configs, String deviceId){
        List<ConfigVo> returnList = new ArrayList<>();
        for (int i=0;i<configs.size();i++){
            ConfigVo configVo = configs.get(i);
            String defaultValue = configVo.getDefaultValue();
            String configValue = configVo.getConfigValue();
            // 网元配置了值则设置成网元的值
            if (!CollectionUtils.isEmpty(deviceConfigs)){
                List<NmplDeviceConfig> collect = deviceConfigs.stream().filter(config -> config.getDeviceId().equals(deviceId) && ParamCheckUtil.checkVoStrBlank(config.getConfigValue())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)){
                    configValue = collect.get(0).getConfigValue();
                }
            }
            // 网元和修改都没值则设置成默认值
            if (configValue == null){
                configVo.setConfigValue(defaultValue);
            }
            configVo.setConfigValue(configValue);
            returnList.add(configVo);
        }
        return returnList;
    }

    /**
     * 同步采集业务配置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result editDataCollectConfig(EditDataCollectConfigReq req) {
        Result result;

        try {
            // check param is legal
            checkEditDataCollectConfigParam(req);
            for (NmplReportBusinessVo vo : req.getNmplReportBusinessVos()){
                // 校验id是否为空
                if (vo.getId() == null){
                    throw new Exception("nmplReportBusinessVo.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                NmplReportBusiness reportBusiness = new NmplReportBusiness();
                BeanUtils.copyProperties(vo,reportBusiness);
                nmplReportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
            }
            for (NmplConfigVo vo : req.getNmplConfigVos()){
                // 校验id是否为空
                if (vo.getId() == null){
                    throw new Exception("nmplConfigVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                NmplConfig config = new NmplConfig();
                BeanUtils.copyProperties(vo,config);
                config.setStatus(SYNC_KEY_YES);
                nmplConfigMapper.updateByPrimaryKeySelective(config);
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("ConfigServiceImpl.editDataCollectConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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

            // 查询数据采集开关配置
            NmplConfigExample configExample = new NmplConfigExample();
            configExample.setOrderByClause("update_time desc");
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            criteria.andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(configExample);
            if (!CollectionUtils.isEmpty(nmplConfigs)){
                NmplConfigVo configVo = new NmplConfigVo();
                BeanUtils.copyProperties(nmplConfigs.get(0),configVo);
                resp.setCollectSwitch(configVo);
            }else {
                throw new Exception("数据采集开关配置未配置");
            }

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


    private void checkSyncConfigParam(SyncConfigReq req) throws Exception{
        if (CollectionUtils.isEmpty(req.getConfigVos())){
            throw new Exception("ConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkEditDataCollectConfigParam(EditDataCollectConfigReq req) throws Exception{
        // 校验入参是否合法
        if (CollectionUtils.isEmpty(req.getNmplReportBusinessVos())){
            throw new Exception("NmplReportBusinessVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getNmplConfigVos())){
            throw new Exception("NmplConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
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

}
