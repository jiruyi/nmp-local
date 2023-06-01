package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.PageInfo;
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
import com.matrictime.network.util.DateUtils;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    /**
     * 终端流量列表查询
     * @param terminalDataRequest
     * @return
     */
    @Override
    public Result<PageInfo> selectTerminalData(TerminalDataRequest terminalDataRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            result.setResultObj(terminalDataDomainService.selectTerminalData(terminalDataRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("selectTerminalData:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 终端流量流量收集
     * @param terminalDataListRequest
     * @return
     */
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
        //数据放入缓存中
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
            if(cache.isEmpty()) {
                queryAllDataFromMysql(terminalDataReq,cache);
                //将数据放入redis缓存
                redisTemplate.opsForHash().putAll(key, cache);
            }
            supplementaryDateToRedis(cache,terminalDataReq,key);
            return buildResult(sortData(cache));
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

    /**
     * 校验入参
     * @param terminalDataReq
     */
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
     * 补充数据
     * @param map
     * @param terminalDataReq
     * @param key
     */
    private void supplementaryDateToRedis(Map<String, TimeDataVo> map,TerminalDataReq terminalDataReq,String key){
        Map<String,TimeDataVo> missingTime = new HashMap<>();
        Date timeBeforeHours =TimeUtil.getTimeBeforeHours(TWELVE,ZERO);
        List<String> timeList = CommonServiceImpl.getXTimePerHalfHour(DateUtils.getRecentHalfTime(new Date()), -24, 30 * 60, DateUtils.MINUTE_TIME_FORMAT);
        for (String time : timeList) {
            TimeDataVo timeDataVo = new TimeDataVo();
            timeDataVo.setUpValue(0.0);
            timeDataVo.setDownValue(0.0);
            timeDataVo.setTime(time);
            timeDataVo.setDate(new Date());
            if(!map.containsKey(time)){
                missingTime.put(time,timeDataVo);
            }else if(map.get(time).getDate().before(timeBeforeHours)){
                missingTime.put(time,timeDataVo);
            }
        }
        if(!missingTime.isEmpty()){
            queryMissDataFromMysql(terminalDataReq,missingTime);
            for (String time : missingTime.keySet()) {
                redisTemplate.opsForHash().put(key,time,missingTime.get(time));
            }
            map.putAll(missingTime);
        }
    }

    /**
     * mysql获取12小时的历史数据来补缺失的数据
     * @param terminalDataReq
     * @param cache
     */
    private void queryMissDataFromMysql(TerminalDataReq terminalDataReq,Map<String,TimeDataVo> cache) {
        NmplTerminalDataExample nmplTerminalDataExample = new NmplTerminalDataExample();
        nmplTerminalDataExample.createCriteria().andDataTypeEqualTo(terminalDataReq.getDataType())
                .andTerminalNetworkIdEqualTo(terminalDataReq.getTerminalNetworkId())
                .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(TWELVE, ZERO));
        List<NmplTerminalData> nmplTerminalData = nmplTerminalDataMapper.selectByExample(nmplTerminalDataExample);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        for (NmplTerminalData nmplTerminalDatum : nmplTerminalData) {
            BigDecimal upValue = new BigDecimal(nmplTerminalDatum.getUpValue());
            BigDecimal downValue = new BigDecimal(nmplTerminalDatum.getDownValue());
            if (cache.containsKey(formatter.format(nmplTerminalDatum.getUploadTime()))) {
                TimeDataVo timeDataVo = cache.get(formatter.format(nmplTerminalDatum.getUploadTime()));
                timeDataVo.setUpValue(upValue.divide(new BigDecimal(BASE_NUMBER * BASE_NUMBER * HALF_HOUR_SECONDS / BYTE_TO_BPS), RESERVE_DIGITS, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getUpValue())).doubleValue());
                timeDataVo.setDownValue(downValue.divide(new BigDecimal(BASE_NUMBER * BASE_NUMBER * HALF_HOUR_SECONDS / BYTE_TO_BPS), RESERVE_DIGITS, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getDownValue())).doubleValue());
                timeDataVo.setDate(nmplTerminalDatum.getUploadTime());
            }
        }
    }

    /**
     * mysql获取12小时所有的历史数据
     * @param terminalDataReq
     * @param cache
     */
    private void queryAllDataFromMysql(TerminalDataReq terminalDataReq,Map<String,TimeDataVo> cache){
        // 如果redis查询为空 或者redis查询异常 则通过mysql获取12小时历史数据
        NmplTerminalDataExample nmplTerminalDataExample = new NmplTerminalDataExample();
        nmplTerminalDataExample.createCriteria().andDataTypeEqualTo(terminalDataReq.getDataType())
                .andTerminalNetworkIdEqualTo(terminalDataReq.getTerminalNetworkId())
                .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(TWELVE,ZERO));
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
    }

    /**
     * 排序返回结果集
     * @param map
     * @return
     */
    private List<TimeDataVo> sortData(Map<String, TimeDataVo> map){
        List<String> timeList = CommonServiceImpl.getXTimePerHalfHour(DateUtils.getRecentHalfTime(new Date()), -24, 30 * 60, DateUtils.MINUTE_TIME_FORMAT);
        List<TimeDataVo> result = new ArrayList<>();
        for (String s : timeList) {
            map.get(s).setTime(s);
            result.add(map.get(s));
        }
        return result;
    }
}
