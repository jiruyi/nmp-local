package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
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

import static com.matrictime.network.base.constant.DataConstants.*;

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
        Map<String,Set<String>> map = new HashMap<>();
        for (TerminalDataVo terminalDataVo: terminalDataListRequest.getList()){
            i = terminalDataDomainService.collectTerminalData(terminalDataVo);
            if(!map.containsKey(terminalDataVo.getTerminalNetworkId())){
                map.put(terminalDataVo.getTerminalNetworkId(),new HashSet<>());
            }
            map.get(terminalDataVo.getTerminalNetworkId()).add(terminalDataVo.getDataType());
        }
        for (Map.Entry<String, Set<String>> stringSetEntry : map.entrySet()) {
            for (String s : stringSetEntry.getValue()) {
                TerminalDataReq terminalDataReq = new TerminalDataReq();
                terminalDataReq.setDataType(s);
                terminalDataReq.setTerminalNetworkId(stringSetEntry.getKey());
                handleAddData(terminalDataReq);
            }
        }
        result.setResultObj(i);
        result.setSuccess(true);
        return result;
    }


    @Override
    public Result flowTransformation(TerminalDataReq terminalDataReq) {
        Result result;
        try {
            checkParam(terminalDataReq);
            String key = DataConstants.FLOW_TRANSFOR
                    +terminalDataReq.getTerminalNetworkId()+UNDERLINE +terminalDataReq.getDataType();
            Map<String, TimeDataVo> cache = getRedisHash(key);
            Map<String, JSONObject> map = new HashMap<>();
            if(cache.isEmpty()) {
                // 如果redis查询为空 或者redis查询异常 则通过mysql获取24小时历史数据
                NmplTerminalDataExample nmplTerminalDataExample = new NmplTerminalDataExample();
                nmplTerminalDataExample.createCriteria().andDataTypeEqualTo(terminalDataReq.getDataType())
                        .andTerminalNetworkIdEqualTo(terminalDataReq.getTerminalNetworkId())
                        .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(TWENTY_FOUR,ZERO));
                List<NmplTerminalData> nmplTerminalData = nmplTerminalDataMapper.selectByExample(nmplTerminalDataExample);

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                for (NmplTerminalData nmplTerminalDatum : nmplTerminalData) {
                    BigDecimal upValue = new BigDecimal(nmplTerminalDatum.getUpValue());
                    BigDecimal downValue = new BigDecimal(nmplTerminalDatum.getDownValue());
                    if (cache.containsKey(formatter.format(nmplTerminalDatum.getUploadTime()))) {
                        TimeDataVo timeDataVo = cache.get(formatter.format(nmplTerminalDatum.getUploadTime()));
                        timeDataVo.setUpValue( upValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getUpValue())).doubleValue());
                        timeDataVo.setDownValue( downValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getDownValue())).doubleValue());
                    } else {
                        TimeDataVo timeDataVo = new TimeDataVo();
                        timeDataVo.setDate(nmplTerminalDatum.getUploadTime());
                        timeDataVo.setUpValue(upValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
                        timeDataVo.setDownValue(downValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
                        cache.put(formatter.format(nmplTerminalDatum.getUploadTime()), timeDataVo);
                    }
                }
                //将数据放入redis缓存
                redisTemplate.opsForHash().putAll(key, cache);
            }
            supplementaryData(cache);
            map = filterData(cache);
            return buildResult(map);
        }catch (SystemException e) {
            log.info("查询终端流量变化数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.info("查询终端流量变化数据异常",e.getMessage());
            result = failResult("");
        }
        return result;

    }


    @Override
    public void handleAddData(TerminalDataReq terminalDataReq) {
        checkParam(terminalDataReq);
        //获取根据terminal_network_id,data_type分组最新上报的数据
        List<TerminalDataVo> terminalDataVoList = nmplTerminalDataExtMapper.selectCurrentIpFlow(terminalDataReq);
        String time = TimeUtil.getOnTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String key = DataConstants.FLOW_TRANSFOR
                +terminalDataReq.getTerminalNetworkId()+UNDERLINE +terminalDataReq.getDataType();
        TimeDataVo timeDataVo = new TimeDataVo();
        timeDataVo.setUpValue(0.0);
        timeDataVo.setDownValue(0.0);
        for (TerminalDataVo terminalDataVo : terminalDataVoList) {
            //判断数据是否是当天以及时刻是否是当前时刻的
            if(time.equals(formatter.format(terminalDataVo.getUploadTime()))&& TimeUtil.IsTodayDate(terminalDataVo.getUploadTime())){
                BigDecimal upValue = new BigDecimal(terminalDataVo.getUpValue());
                BigDecimal downValue = new BigDecimal(terminalDataVo.getDownValue());
                timeDataVo.setUpValue(upValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).
                        add(BigDecimal.valueOf(timeDataVo.getUpValue())).doubleValue());
                timeDataVo.setDownValue(downValue.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).
                        add(BigDecimal.valueOf(timeDataVo.getDownValue())).doubleValue());
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

        Date timeBeforeHours =TimeUtil.getTimeBeforeHours(TWELVE,ZERO);
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

    /**
     * 补充map缺失的节点数据
     */
    private Map<String,TimeDataVo> supplementaryData(Map<String, TimeDataVo> map){
        TimeDataVo timeDataVo = new TimeDataVo();
        timeDataVo.setDate(new Date());
        timeDataVo.setUpValue(0.0);
        timeDataVo.setDownValue(0.0);
        for (int i = 0; i < TWENTY_FOUR; i++) {
            String time1 = null;
            String time2 = null;
            if(i<10){
                time1 = "0"+ i +":00";
                time2 = "0"+ i +":30";
            }else {
                time1 =  i +":00";
                time2 =  i +":30";
            }
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
