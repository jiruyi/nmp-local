package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.extend.ConfigExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterConfigVo;
import com.matrictime.network.response.UpdateConfigResp;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.matrictime.network.base.constant.DataConstants.NMPL_CONFIG;
import static com.matrictime.network.constant.DataConstants.*;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    private NmplConfigMapper nmplConfigMapper;

    @Resource
    private ConfigExtMapper configExtMapper;

    @Autowired
    private UpdateInfoService updateInfoService;

    @Override
    public void initInfo(List<CenterConfigVo> centerConfigVos) {
        update(centerConfigVos);
    }

    @Override
    @Transactional
    public Result<UpdateConfigResp> update(List<CenterConfigVo> centerConfigVos) {
        Result result;
        try{
            Date inserTime = new Date();
            Date updateTime = new Date(inserTime.getTime()+100);

            // 获取代理库系统配置
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(null);
            Map<String, NmplConfig> localMap = nmplConfigs.stream().collect(Collectors.toMap(NmplConfig::getConfigCode, Function.identity(), (key1, key2) -> key2));
            log.info("localMap:{}", JSONObject.toJSONString(localMap));

            // 将录入系统配置按设备类型分组
            Map<String, List<CenterConfigVo>> map = groupTask(centerConfigVos);
            Iterator<Map.Entry<String, List<CenterConfigVo>>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, List<CenterConfigVo>> entry = iterator.next();
                List<CenterConfigVo> list = entry.getValue();
                boolean addFlag = false;
                boolean updateFlag = false;
                List<CenterConfigVo> updateList = new ArrayList<>();
                List<CenterConfigVo> insertList = new ArrayList<>();

                // 遍历判断是插入还是更新操作
                for (int i=0;i<list.size();i++){
                    CenterConfigVo vo = list.get(i);

                    if (localMap.containsKey(vo.getConfigCode())){
                        vo.setUpdateTime(updateTime);
                        updateFlag = true;
                        updateList.add(vo);
                    }else {
                        vo.setUpdateTime(inserTime);
                        addFlag = true;
                        insertList.add(vo);
                    }
                }
                if (!CollectionUtils.isEmpty(updateList)){
                    int batchUpdate = configExtMapper.batchUpdate(updateList);
                }
                if (!CollectionUtils.isEmpty(insertList)){
                    int batchInsert = configExtMapper.batchInsert(insertList);
                }
                // 通知设备更新
                if (updateFlag){
                    updateInfoService.updateInfo(entry.getKey(),NMPL_CONFIG,EDIT_TYPE_UPD,SYSTEM_NM,updateTime);
                }
                // 通知设备插入
                if (addFlag){
                    updateInfoService.updateInfo(entry.getKey(),NMPL_CONFIG,EDIT_TYPE_ADD,SYSTEM_NM,inserTime);
                }
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.update SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }catch (Exception e){
            log.error("ConfigServiceImpl.update Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 将系统配置按设备类型分类
     * @param centerConfigVos
     * @return
     */
    private Map<String,List<CenterConfigVo>> groupTask(List<CenterConfigVo> centerConfigVos){
        Map<String,List<CenterConfigVo>> resultMap = new HashMap<>();
        for (int i=0;i<centerConfigVos.size();i++){
            CenterConfigVo vo = centerConfigVos.get(i);
            String key = vo.getDeviceType();
            List<CenterConfigVo> value = new ArrayList<>();
            if (resultMap.containsKey(key)){
                value = resultMap.get(key);
            }
            value.add(vo);
            resultMap.put(key,value);
        }
        return resultMap;
    }
}
