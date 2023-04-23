package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataListRequest;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.TerminalDataResponse;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.TimeUtil;
import com.matrictime.network.dao.mapper.NmplTerminalDataMapper;
import com.matrictime.network.dao.mapper.extend.NmplTerminalDataExtMapper;
import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplDataCollectExample;
import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.dao.model.NmplTerminalDataExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.TerminalDataVo;
import com.matrictime.network.modelVo.TimeDataVo;
import com.matrictime.network.request.TerminalDataReq;
import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.TerminalDataResponse;
import com.matrictime.network.service.TerminalDataService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Service
@Slf4j
public class TerminalDataServiceImpl extends SystemBaseService implements TerminalDataService {

    @Resource
    NmplTerminalDataMapper nmplTerminalDataMapper;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    NmplTerminalDataExtMapper nmplTerminalDataExtMapper;

    @Resource
    private TerminalDataDomainService terminalDataDomainService;

    @Override
    public Result<TerminalDataResponse> selectTerminalData(TerminalDataRequest terminalDataRequest) {
        Result<TerminalDataResponse> result = new Result<>();
        try {
            result.setResultObj(terminalDataDomainService.selectTerminalData(terminalDataRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("selectTerminalData:{}",e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<Integer> collectTerminalData(TerminalDataListRequest terminalDataListRequest) {
        Result<Integer> result = new Result<>();
        int i = 0;
        for (TerminalDataVo terminalDataVo: terminalDataListRequest.getList()){
            i = terminalDataDomainService.collectTerminalData(terminalDataVo);
        }
        result.setResultObj(i);
        result.setSuccess(true);
        return result;
    }


    @Override
    public Result flowTransformation(TerminalDataReq terminalDataReq) {
        checkParam(terminalDataReq);
        String key = DataConstants.FLOW_TRANSFOR
                +terminalDataReq.getTerminalNetworkId()+"_" +terminalDataReq.getDataType();
        Map<String, TimeDataVo> cache = getRedisHash(key);
        Map<String, JSONObject> map = new HashMap<>();
        if(cache.isEmpty()) {
            // 如果redis查询为空 或者redis查询异常 则通过mysql获取24小时历史数据
            NmplTerminalDataExample nmplTerminalDataExample = new NmplTerminalDataExample();
            nmplTerminalDataExample.createCriteria().andDataTypeEqualTo(terminalDataReq.getDataType())
                    .andTerminalNetworkIdEqualTo(terminalDataReq.getTerminalNetworkId())
                    .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(24,0));
            List<NmplTerminalData> nmplTerminalData = nmplTerminalDataMapper.selectByExample(nmplTerminalDataExample);

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            for (NmplTerminalData nmplTerminalDatum : nmplTerminalData) {
                BigDecimal upValue = new BigDecimal(nmplTerminalDatum.getUpValue());
                BigDecimal downValue = new BigDecimal(nmplTerminalDatum.getDownValue());
                if (cache.containsKey(formatter.format(nmplTerminalDatum.getUploadTime()))) {
                    TimeDataVo timeDataVo = cache.get(formatter.format(nmplTerminalDatum.getUploadTime()));
                    timeDataVo.setUpValue(timeDataVo.getUpValue() +upValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    timeDataVo.setDownValue(timeDataVo.getDownValue() + downValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                } else {
                    TimeDataVo timeDataVo = new TimeDataVo();
                    timeDataVo.setDate(nmplTerminalDatum.getUploadTime());
                    timeDataVo.setUpValue(upValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    timeDataVo.setDownValue(downValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    cache.put(formatter.format(nmplTerminalDatum.getUploadTime()), timeDataVo);
                }
            }
            //将数据放入redis缓存
            redisTemplate.opsForHash().putAll(key, cache);
        }
        supplementaryData(cache);
        map = filterData(cache);
       return buildResult(map);
    }


    @Override
    public void handleAddData(TerminalDataReq terminalDataReq) {
        checkParam(terminalDataReq);
        List<TerminalDataVo> terminalDataVoList = nmplTerminalDataExtMapper.selectCurrentIpFlow(terminalDataReq);
        String time = TimeUtil.getOnTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String key = DataConstants.FLOW_TRANSFOR
                +terminalDataReq.getTerminalNetworkId()+"_" +terminalDataReq.getDataType();
        TimeDataVo timeDataVo = new TimeDataVo();

        for (TerminalDataVo terminalDataVo : terminalDataVoList) {
            if(time.equals(formatter.format(terminalDataVo.getUploadTime()))){
                BigDecimal upValue = new BigDecimal(terminalDataVo.getUpValue());
                BigDecimal downValue = new BigDecimal(terminalDataVo.getDownValue());
                timeDataVo.setUpValue(upValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                timeDataVo.setDownValue(downValue.divide(new BigDecimal(1024.0*1024.0*225),2,BigDecimal.ROUND_HALF_UP).doubleValue());
                // 8Mbps = 1MB/s    byte->Mb 10^20  半小时 1800s
            }
        }
        timeDataVo.setDate(new Date());
        redisTemplate.opsForHash().put(key,time,timeDataVo);

    }

    private void checkParam(TerminalDataReq terminalDataReq){
        if(StringUtil.isEmpty(terminalDataReq.getDataType())|| StringUtil.isEmpty(terminalDataReq.getTerminalNetworkId())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
    /**
     * redis获取hash数据
     * @param key
     * @return
     */
    private Map<String, TimeDataVo> getRedisHash(String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String, Object> entries = hashOps.entries(key);
        Map<String,TimeDataVo> res = new HashMap<>();
        for (String s : entries.keySet()) {
            res.put(s,JSONObject.parseObject(JSONObject.toJSONString(entries.get(s)),TimeDataVo.class));
        }
        return res;
    }

    /**
     * 过滤12小时以外的数据
     * @param map
     * @return
     */
    private  Map<String, JSONObject> filterData(Map<String, TimeDataVo> map){
        Map<String,JSONObject> res = new HashMap<>();
        Set<String> set = map.keySet();

        Date timeBeforeHours =TimeUtil.getTimeBeforeHours(12,30);
        for (String s : set) {
            if(TimeUtil.checkTime(s)){
                TimeDataVo timeDataVo = map.get(s);
                JSONObject json = new JSONObject();
                if(timeDataVo.getDate().after(timeBeforeHours)){
                    json.put("upValue",timeDataVo.getUpValue());
                    json.put("downValue",timeDataVo.getDownValue());
                }else {
                    json.put("upValue",0.0);
                    json.put("downValue",0.0);
                }
                res.put(s,json);
            }
        }
        //排序
        List<Map.Entry<String, JSONObject>> list = new ArrayList<>(res.entrySet());
        Collections.sort(list, (o1, o2) -> timeChange(o1.getKey()).compareTo(timeChange(o2.getKey())));
        Map<String, JSONObject> sortedMap = new LinkedHashMap<>();
        list.forEach(item -> sortedMap.put(item.getKey(), item.getValue()));
        return sortedMap;
    }

    private Map<String,TimeDataVo> supplementaryData(Map<String, TimeDataVo> map){
        TimeDataVo timeDataVo = new TimeDataVo();
        timeDataVo.setDate(new Date());
        timeDataVo.setUpValue(0.0);
        timeDataVo.setDownValue(0.0);
        for (int i = 0; i < 24; i++) {
            String time1 = i+":00";
            String time2 = i+":30";
            if(!map.containsKey(time1)){
                map.put(time1,timeDataVo);
            }
            if(!map.containsKey(time2)){
                map.put(time2,timeDataVo);
            }
        }
        return map;
    }

    private Integer timeChange(String string){
        return Integer.valueOf(string.replaceAll(":",""));
    }
}
