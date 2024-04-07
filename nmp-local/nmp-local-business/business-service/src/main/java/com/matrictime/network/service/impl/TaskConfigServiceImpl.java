package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.domain.TaskConfigDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceTaskConfigMapper;
import com.matrictime.network.dao.model.NmplDeviceTaskConfig;
import com.matrictime.network.dao.model.NmplDeviceTaskConfigExample;
import com.matrictime.network.dao.model.NmplTaskConfig;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.TaskConfigVo;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.SyncTaskReq;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.SyncTaskResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceConfigService;
import com.matrictime.network.service.TaskConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Slf4j
@Service
public class TaskConfigServiceImpl extends SystemBaseService implements TaskConfigService {

    @Autowired
    private TaskConfigDomainService taskConfigDomainService;

    @Autowired
    private DeviceConfigService deviceConfigService;

    @Autowired
    private DeviceExtraInfoDomainService deviceExtraInfoDomainService;

    @Resource
    private NmplDeviceTaskConfigMapper deviceTaskConfigMapper;


    @Override
    public Result<PageInfo> queryTaskByPages(QueryTaskReq req) {
        Result<PageInfo> result;
        try{
            PageInfo<NmplTaskConfig> taskConfigPageInfo = taskConfigDomainService.queryByPages(req);
            result = buildResult(taskConfigPageInfo);
        }catch (SystemException e){
            log.error("TaskConfigServiceImpl.queryTaskByPages SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("TaskConfigServiceImpl.queryTaskByPages Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result<SyncTaskResp> sync(SyncTaskReq req) {
        Result result;

        try {
            SyncTaskResp resp = null;
            // check param is legal
            checkSyncTaskParam(req);

            List<TaskConfigVo> vos = new ArrayList<>();
            Map<String,List<TaskConfigVo>> taskMap = new HashMap<>();
            List<TaskConfigVo> taskConfigVos = req.getTaskConfigVos();
            List<TaskConfigVo> tempTask = new ArrayList<>();
            for (int i = 0; i< taskConfigVos.size(); i++){
                TaskConfigVo vo = taskConfigVos.get(i);
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
            Integer upd = taskConfigDomainService.update(vos);
            NmplDeviceTaskConfigExample deviceTaskConfigExample = new NmplDeviceTaskConfigExample();
            deviceTaskConfigExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceTaskConfig> deviceTaskConfigs = deviceTaskConfigMapper.selectByExample(deviceTaskConfigExample);
            List<TaskVo> taskVos = getTaskVo(taskMap,deviceTaskConfigs);
            List<Map<String, Boolean>> futures = deviceConfigService.pushConfigToProxy(taskVos);

            result = buildResult(resp);
        }catch (Exception e){
            log.error("TaskConfigServiceImpl.edit Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }


    private void checkSyncTaskParam(SyncTaskReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (CollectionUtils.isEmpty(req.getTaskConfigVos())){
            throw new Exception("TaskConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 定时任务配置同步数据重组
     * @param taskMap
     * @return
     */
    private List<TaskVo> getTaskVo(Map<String,List<TaskConfigVo>> taskMap,List<NmplDeviceTaskConfig> deviceTaskConfigs){
        List<TaskVo> taskVos = new ArrayList<>();
        Iterator<Map.Entry<String, List<TaskConfigVo>>> iterator = taskMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<TaskConfigVo>> entry = iterator.next();
            String key = entry.getKey();
            List<TaskConfigVo> value = entry.getValue();
            List<DeviceInfoVo> devices = deviceExtraInfoDomainService.getDeviceByDeviceType(key);
            for (DeviceInfoVo deviceInfoVo:devices){
                TaskVo vo = new TaskVo();
                if (!CollectionUtils.isEmpty(deviceTaskConfigs)){

                }
                vo.setIp(deviceInfoVo.getLanIp());
                vo.setDeviceType(key);
                JSONObject data = new JSONObject();
                value = resetValue(deviceTaskConfigs,value,deviceInfoVo.getDeviceId());
                data.put("taskConfigVos",value);
                vo.setData(data.toJSONString());
                taskVos.add(vo);
            }
        }
        return taskVos;
    }

    /**
     * 配置推送到网元上的值
     * @param deviceTaskConfigs
     * @param taskConfigs
     * @param deviceId
     * @return
     */
    private List<TaskConfigVo> resetValue(List<NmplDeviceTaskConfig> deviceTaskConfigs, List<TaskConfigVo> taskConfigs, String deviceId){
        List<TaskConfigVo> returnList = new ArrayList<>();
        for (int i=0;i<taskConfigs.size();i++){
            TaskConfigVo taskConfigVo = taskConfigs.get(i);
            Long defaultValue = taskConfigVo.getDefaultValue();
            Long currentValue = taskConfigVo.getCurrentValue();
            // 网元配置了值则设置成网元的值
            if (!CollectionUtils.isEmpty(deviceTaskConfigs)){
                List<NmplDeviceTaskConfig> collect = deviceTaskConfigs.stream().filter(taskConfig -> taskConfig.getDeviceId().equals(deviceId) && taskConfig.getCurrentValue() != null).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)){
                    currentValue = collect.get(0).getCurrentValue();
                }
            }
            // 网元和修改都没值则设置成默认值
            if (currentValue == null){
                taskConfigVo.setCurrentValue(defaultValue);
            }
            taskConfigVo.setCurrentValue(currentValue);
            returnList.add(taskConfigVo);
        }
        return returnList;
    }
}
