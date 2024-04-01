package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmplTaskConfigMapper;
import com.matrictime.network.dao.mapper.extend.TaskConfigExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.modelVo.CenterTaskConfigVo;
import com.matrictime.network.response.UpdateTaskConfigResp;
import com.matrictime.network.service.TaskConfigService;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class TaskConfigServiceImpl extends SystemBaseService implements TaskConfigService {

    @Autowired
    private UpdateInfoService updateInfoService;

    @Resource
    private NmplTaskConfigMapper taskConfigMapper;

    @Resource
    private TaskConfigExtMapper taskConfigExtMapper;


    /**
     * 初始化
     * @param centerTaskConfigVos
     */
    @Override
    public void initInfo(List<CenterTaskConfigVo> centerTaskConfigVos) {
        update(centerTaskConfigVos);
    }

    /**
     * 数据更新入库
     * @param centerTaskConfigVos
     * @return
     */
    @Override
    @Transactional
    public Result<UpdateTaskConfigResp> update(List<CenterTaskConfigVo> centerTaskConfigVos) {
        Result result;
        try{
            Date createTime = new Date();
            // 获取代理库定时任务配置
            List<NmplTaskConfig> taskConfigs = taskConfigMapper.selectByExample(null);
            Map<Long, NmplTaskConfig> localMap = taskConfigs.stream().collect(Collectors.toMap(NmplTaskConfig::getId, Function.identity(), (key1, key2) -> key2));

            // 将录入定时任务按设备类型分组
            Map<String, List<CenterTaskConfigVo>> map = groupTask(centerTaskConfigVos);
            Iterator<Map.Entry<String, List<CenterTaskConfigVo>>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, List<CenterTaskConfigVo>> entry = iterator.next();
                List<CenterTaskConfigVo> list = entry.getValue();
                boolean addFlag = false;
                boolean updateFlag = false;
                List<CenterTaskConfigVo> updateList = new ArrayList<>();
                List<CenterTaskConfigVo> insertList = new ArrayList<>();

                // 遍历判断是插入还是更新操作
                for (int i=0;i<list.size();i++){
                    CenterTaskConfigVo vo = list.get(i);
                    vo.setUpdateTime(createTime);
                    if (localMap.containsKey(vo.getId())){
                        updateFlag = true;
                        updateList.add(vo);
                    }else {
                        addFlag = true;
                        insertList.add(vo);
                    }
                }
                if (!CollectionUtils.isEmpty(updateList)){
                    int batchUpdate = taskConfigExtMapper.batchUpdate(updateList);
                }
                if (!CollectionUtils.isEmpty(insertList)){
                    int batchInsert = taskConfigExtMapper.batchInsert(insertList);
                }
                // 通知设备更新
                if (updateFlag){
                    updateInfoService.updateInfo(entry.getKey(),NMPL_TASK_CONFIG,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
                }
                // 通知设备插入
                if (addFlag){
                    updateInfoService.updateInfo(entry.getKey(),NMPL_TASK_CONFIG,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
                }
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("TaskConfigServiceImpl.update SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }catch (Exception e){
            log.error("TaskConfigServiceImpl.update Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 将定时任务配置按设备类型分类
     * @param centerTaskConfigVos
     * @return
     */
    private Map<String,List<CenterTaskConfigVo>> groupTask(List<CenterTaskConfigVo> centerTaskConfigVos){
        Map<String,List<CenterTaskConfigVo>> resultMap = new HashMap<>();
        for (int i=0;i<centerTaskConfigVos.size();i++){
            CenterTaskConfigVo vo = centerTaskConfigVos.get(i);
            String key = vo.getDeviceType();
            List<CenterTaskConfigVo> value = new ArrayList<>();
            if (resultMap.containsKey(key)){
                value = resultMap.get(key);
            }
            value.add(vo);
            resultMap.put(key,value);
        }
        return resultMap;
    }

    public static void main(String[] args) {
        List<NmplTaskConfig> taskConfigs = new ArrayList<>();
        NmplTaskConfig config1 = new NmplTaskConfig();
        config1.setId(1L);
        config1.setTaskId("1");
        NmplTaskConfig config2 = new NmplTaskConfig();
        config2.setId(2L);
        config2.setTaskId("2");
        NmplTaskConfig config3 = new NmplTaskConfig();
        config3.setId(3L);
        config3.setTaskId("3");
        taskConfigs.add(config1);
        taskConfigs.add(config2);
        taskConfigs.add(config3);
        Map<Long, NmplTaskConfig> maps = taskConfigs.stream().collect(Collectors.toMap(NmplTaskConfig::getId, Function.identity(), (key1, key2) -> key2));
        System.out.println(JSONObject.toJSONString(maps));
    }
}
