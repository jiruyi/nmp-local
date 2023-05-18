package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.TimeUtil;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.TimeDataVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.request.TerminalDataReq;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.MonitorResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.util.DateUtils;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;

@Service
@Slf4j
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class DataCollectServiceImpl extends SystemBaseService implements DataCollectService {
    @Autowired
    DataCollectDomainService dataCollectDomainService;

    @Value("${data.delayTime}")
    private Integer delayTime;

    @Resource
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    NmplDataCollectMapper nmplDataCollectMapper;

    @Resource
    NmplDataCollectExtMapper nmplDataCollectExtMapper;

    @Resource
    NmplAlarmInfoMapper nmplAlarmInfoMapper;

    @Override
    public Result<PageInfo> queryByConditon(DataCollectReq dataCollectReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<DataCollectVo> pageResult = new PageInfo<>();
            pageResult = dataCollectDomainService.queryByConditions(dataCollectReq);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("统计数据查询异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("统计数据查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Async("taskExecutor")
    @Override
    @Transactional
    public Future<Result> save(DataCollectReq dataCollectReq) {
        Result<Integer> result;
        try {
            if(dataCollectReq.getDataCollectVoList()!=null){
                List<DataCollectVo> dataCollectVoLoadList = new ArrayList<>();
                Map<String,String> deviceMap = new HashMap<>();
                NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
                nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
                List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

                NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
                nmplDeviceInfoExample.createCriteria().andIsExistEqualTo(true);
                List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

                for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
                    deviceMap.put(nmplBaseStationInfo.getStationId(),nmplBaseStationInfo.getStationName());
                }
                for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
                    deviceMap.put(nmplDeviceInfo.getDeviceId(),nmplDeviceInfo.getDeviceName());
                }

                for (DataCollectVo dataCollectVo : dataCollectReq.getDataCollectVoList()) {
                    String name = DataCollectEnum.getMap().get(dataCollectVo.getDataItemCode()).getConditionDesc();
                    String unit = DataCollectEnum.getMap().get(dataCollectVo.getDataItemCode()).getUnit();
                    if(deviceMap.get(dataCollectVo.getDeviceId())!=null){
                        dataCollectVo.setDeviceName(deviceMap.get(dataCollectVo.getDeviceId()));
                    }
                    dataCollectVo.setDataItemName(name);
                    dataCollectVo.setUnit(unit);
                    if (INTRANET_BROADBAND_LOAD_CODE.equals(dataCollectVo.getDataItemCode()) || INTERNET_BROADBAND_LOAD_CODE.equals(dataCollectVo.getDataItemCode())) {
                        dataCollectVoLoadList.add(dataCollectVo);
                    }
                }
                dataCollectReq.setDataCollectVoLoadList(dataCollectVoLoadList);
            }
            result = buildResult(dataCollectDomainService.save(dataCollectReq));
        }catch (SystemException e){
            log.info("存储统计数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.info("存储统计数据异常",e.getMessage());
            result = failResult("");
        }
        return new AsyncResult<>(result);
    }

    @Override
    public Result monitorData(MonitorReq monitorReq) {
        Result result= null;
        try {
            //1.根据小区id和当前时间找到最近的统计数据
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(15)));
            List<NmplDataCollect> dataCollectList = dataCollectDomainService.queryMonitorData(monitorReq);
            //2.根据设备类型分组
            Integer userNumber = 0;
            double totalBandwidth=0.0;
            double dispenserSecretKey=0.0;
            double generatorSecretKey=0.0;
            double cacheSecretKey=0.0;

            for (NmplDataCollect nmplDataCollect : dataCollectList) {
                BigDecimal bigDecimal = new BigDecimal(nmplDataCollect.getDataItemValue());
                double value = 0.0;
                if(nmplDataCollect.getDataItemCode().equals("10006")){
                    value = bigDecimal.divide(new BigDecimal(1000.0*1000.0),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if(nmplDataCollect.getDataItemCode().equals("10007")){
                    value = bigDecimal.divide(new BigDecimal(1024.0*1024.0),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                switch (nmplDataCollect.getDeviceType()){
                    case "00":
                        if(nmplDataCollect.getDataItemCode().equals("10000")){
                            userNumber+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "11":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            dispenserSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "12":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            generatorSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "13":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            cacheSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    default:
                }
            }
            //统计用户数 表pc_data 去重
            userNumber = dataCollectDomainService.countDeviceNumber(monitorReq);

            //统计带宽 表 data_collect 累加 totalBandwidth  code=10006
            totalBandwidth = dataCollectDomainService.sumDataItemValue(monitorReq);

            //cacheSecretKey=dispenserSecretKey+random(1000)
            Random random = new Random(1000);
            double v = random.nextDouble();
            generatorSecretKey = dispenserSecretKey + v;

            //统一单位
            String [] totalBandwidthStr = dataChangeWithoutUnit(totalBandwidth,1);

            String dispenserSecretKeyStr = dataChange(dispenserSecretKey,0);

            String generatorSecretKeyStr = dataChange(generatorSecretKey,0);

            String cacheSecretKeyStr = dataChange(cacheSecretKey,0);


            MonitorResp monitorResp = new MonitorResp
                    (userNumber,totalBandwidthStr[0],totalBandwidthStr[1],dispenserSecretKeyStr,generatorSecretKeyStr,
                            cacheSecretKeyStr,new ArrayList<>());
            result = buildResult(monitorResp);
        } catch (SystemException e) {
            log.info("查询监控数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.info("查询监控数据异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * @param data
     * 计算出来的数据
     * @param countFlag
     * 判断传过来的是否是基站带宽总量
     * 1 代表是基站带宽总量,0 代表其他
     * @return
     */
    private String dataChange(Double data, int countFlag){
        //转换成MB
        if(countFlag == 1){
            data = data/(1024.0*1024.0);
        }
        //转换成GB
        if(data >= 999.0){
            data = data/1024.0;
            //转换成TB
            if(data >= 999.0){
                data = data/1024.0;
                return String.format("%.2f", data) + "TB";
            }
            return String.format("%.2f", data) + "GB";
        }
        return String.format("%.2f", data) + "MB";
    }

    private String[] dataChangeWithoutUnit(Double data, int countFlag){
        String [] strings = new String[2];
        //转换成MB
        if(countFlag == 1){
            data = data/(1024.0*1024.0);
        }
        //转换成GB
        if(data >= 999.0){
            data = data/1024.0;
            //转换成TB
            if(data >= 999.0){
                data = data/1024.0;
                strings[0] = String.format("%.2f", data);
                strings[1] = "TB";
                return strings;
            }
            strings[0] = String.format("%.2f", data);
            strings[1] = "GB";
            return strings;
        }
        strings[0] = String.format("%.2f", data);
        strings[1] = "MB";
        return strings;
    }

    @Override
    public Result monitorDataTopTen(MonitorReq monitorReq) {
        Result result= null;
        try {
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(delayTime)));
            result = buildResult(dataCollectDomainService.queryTopTen(monitorReq));
        }catch (SystemException e){
            log.info("查询排行前10数据异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("查询排行前10数据异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    public Date LocalTimeToUdate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    @Override
    public Result selectAllDevice(DataCollectReq dataCollectReq) {
        Result result =null;
        DeviceResponse deviceResponse = new DeviceResponse();
        List<DeviceInfoVo>deviceInfoVos = new ArrayList<>();
        try {
            if(dataCollectReq.getDeviceType().equals(StationTypeEnum.BASE.getCode())){
                NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
                nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
                List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
                for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
                    String[]strings = nmplBaseStationInfo.getStationNetworkId().split("-");
                    nmplBaseStationInfo.setStationNetworkId(strings[strings.length-1]);
                    DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                    BeanUtils.copyProperties(nmplBaseStationInfo,deviceInfoVo);
                    deviceInfoVo.setDeviceName(nmplBaseStationInfo.getStationName());
                    deviceInfoVo.setDeviceId(nmplBaseStationInfo.getStationId());
                    deviceInfoVos.add(deviceInfoVo);
                }
            }else {
//                Map<String,String> map = new HashMap<>();
//                map.put("02","01");
//                map.put("03","02");
//                map.put("04","03");
                NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
                nmplDeviceInfoExample.createCriteria().andIsExistEqualTo(true).andDeviceTypeEqualTo(dataCollectReq.getDeviceType());
                List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
                for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
                    String[]strings = nmplDeviceInfo.getStationNetworkId().split("-");
                    nmplDeviceInfo.setStationNetworkId(strings[strings.length-1]);
                    DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                    BeanUtils.copyProperties(nmplDeviceInfo,deviceInfoVo);
                    deviceInfoVos.add(deviceInfoVo);
                }
            }
            deviceResponse.setDeviceInfoVos(deviceInfoVos);
            result = buildResult(deviceResponse);
        }catch (Exception e){
            log.info("查询设备异常",e.getMessage());
            result =  failResult("");
        }
       return result;
    }


    //3.0.18版本的监控管理

    /**
     * 流量变化
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result flowTransformation(DataCollectReq dataCollectReq) {
        Result result;
        try {
            //参数校验
            checkParam(dataCollectReq);
            // 查询redis获取最近12小时的数据
            String key = DataConstants.FLOW_TRANSFOR
                    +dataCollectReq.getDeviceIp()+UNDERLINE +dataCollectReq.getDataItemCode();
            Map<String, TimeDataVo> cache = getRedisHash(key);
            if(cache.isEmpty()) {
                queryAllDataFromMysql(dataCollectReq,cache);
                //将数据放入redis缓存
                redisTemplate.opsForHash().putAll(key, cache);
            }
            supplementaryDateToRedis(cache,dataCollectReq,key);
            return buildResult(sortData(cache));
        }catch (SystemException e) {
            log.info("查询服务器流量变化数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.info("查询服务器流量变化数据异常",e.getMessage());
            result = failResult("");
        }
        return result;

    }

    /**
     * 当前流量
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result currentIpFlow(DataCollectReq dataCollectReq) {
        Result result;
        try {
            //校验参数
            checkParam(dataCollectReq);
            //从redis中获取值
            String key = DataConstants.CURRENT_FLOW+dataCollectReq.getDeviceIp()+UNDERLINE +dataCollectReq.getDataItemCode();
            Object value = redisTemplate.opsForValue().get(key);
            String time = TimeUtil.getOnTime();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            if(value == null){
                double res =0.0;
                //获取根据device_id,data_item_code分组最新上报的数据
                List<DataCollectVo> dataCollectVos = nmplDataCollectExtMapper.selectCurrentIpFlow(dataCollectReq);
                for (DataCollectVo dataCollectVo : dataCollectVos) {
                    //判断数据是否是当天以及时刻是否是当前时刻的
                    if(time.equals(formatter.format(dataCollectVo.getUploadTime()))&& TimeUtil.IsTodayDate(dataCollectVo.getUploadTime())){
                        BigDecimal bigDecimal = new BigDecimal(dataCollectVo.getDataItemValue());
                        // 8Mbps = 1MB/s    byte->Mb 10^20  半小时 1800s
                        res = bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(res)).doubleValue();
                    }
                }
                value = String.valueOf(res);
                redisTemplate.opsForValue().set(key,value);
                redisTemplate.expire(key,THIRTY,TimeUnit.MINUTES);
            }
            return buildResult(value);
        }catch (SystemException e) {
            log.info("查询服务器最新流量数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.info("查询服务器最新流量数据异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 处理新增数据放入redis
     */
    @Override
    public void handleAddData(String code,String ip){
        DataCollectReq dataCollectReq = new DataCollectReq();
        dataCollectReq.setDataItemCode(code);
        dataCollectReq.setDeviceIp(ip);
        //获取根据device_id,data_item_code分组最新上报的数据
        List<DataCollectVo> dataCollectVos = nmplDataCollectExtMapper.selectCurrentIpFlow(dataCollectReq);
        String time = TimeUtil.getOnTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String transforKey = DataConstants.FLOW_TRANSFOR + ip +"_" + code;
        String currentKey = DataConstants.CURRENT_FLOW + ip +"_" + code;
        double result = 0.0;
        for (DataCollectVo dataCollectVo : dataCollectVos) {
            //判断数据是否是当天以及时刻是否是当前时刻的
            if(time.equals(formatter.format(dataCollectVo.getUploadTime()))&& TimeUtil.IsTodayDate(dataCollectVo.getUploadTime())){
                BigDecimal bigDecimal = new BigDecimal(dataCollectVo.getDataItemValue());
                // 8Mbps = 1MB/s    byte->Mb 10^20  半小时 1800s
                result = bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(result)).doubleValue();
            }
        }
        //当前流量大于xxx时增加告警信息

        TimeDataVo timeDataVo = new TimeDataVo();
        timeDataVo.setDate(new Date());
        timeDataVo.setValue(result);
        redisTemplate.opsForValue().set(currentKey,String.valueOf(result));
        redisTemplate.expire(currentKey,THIRTY,TimeUnit.MINUTES);
        redisTemplate.opsForHash().put(transforKey,time,timeDataVo);
    }



    /**
     * 校验参数
     * @param dataCollectReq
     */
    private void checkParam(DataCollectReq dataCollectReq){
        if(StringUtil.isEmpty(dataCollectReq.getDeviceIp())||StringUtil.isEmpty(dataCollectReq.getDataItemCode())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


    /**
     * 新增流量过载告警
     */
    private void addFlowAlarm(String ip){
        NmplAlarmInfo nmplAlarmInfo = new NmplAlarmInfo();
        nmplAlarmInfo.setAlarmSourceIp(ip);
        nmplAlarmInfo.setAlarmLevel("1");
        nmplAlarmInfo.setAlarmContentType(AlarmPhyConTypeEnum.FLOW.getCode());
        nmplAlarmInfo.setAlarmSourceType("00");
        nmplAlarmInfo.setAlarmUploadTime(new Date());
        nmplAlarmInfo.setAlarmContent(AlarmPhyConTypeEnum.FLOW.getDesc());
        nmplAlarmInfoMapper.insert(nmplAlarmInfo);
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
     * @param dataCollectReq
     * @param key
     */
    private void supplementaryDateToRedis(Map<String, TimeDataVo> map, DataCollectReq dataCollectReq, String key){
        Map<String,TimeDataVo> missingTime = new HashMap<>();
        Date timeBeforeHours =TimeUtil.getTimeBeforeHours(TWELVE,ZERO);
        List<String> timeList = CommonServiceImpl.getXTimePerHalfHour(DateUtils.getRecentHalfTime(new Date()), -24, 30 * 60, DateUtils.MINUTE_TIME_FORMAT);
        for (String time : timeList) {
            TimeDataVo timeDataVo = new TimeDataVo();
            timeDataVo.setValue(0.0);
            timeDataVo.setTime(time);
            timeDataVo.setDate(new Date());
            if(!map.containsKey(time)){
                missingTime.put(time,timeDataVo);
            }else if(map.get(time).getDate().before(timeBeforeHours)){
                missingTime.put(time,timeDataVo);
            }
        }
        if(!missingTime.isEmpty()){
            queryMissDataFromMysql(dataCollectReq,missingTime);
            for (String time : missingTime.keySet()) {
                redisTemplate.opsForHash().put(key,time,missingTime.get(time));
            }
            map.putAll(missingTime);
        }
    }

    /**
     * mysql获取12小时的历史数据来补缺失的数据
     * @param dataCollectReq
     * @param cache
     */
    private void queryMissDataFromMysql(DataCollectReq dataCollectReq,Map<String,TimeDataVo> cache) {
        // 如果redis查询为空 或者redis查询异常 则通过mysql获取12小时历史数据
        NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
        nmplDataCollectExample.createCriteria().andDataItemCodeEqualTo(dataCollectReq.getDataItemCode())
                .andDeviceIpEqualTo(dataCollectReq.getDeviceIp())
                .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(TWELVE,ZERO));
        List<NmplDataCollect> dataCollectList = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        for (NmplDataCollect nmplDataCollect : dataCollectList) {
            BigDecimal bigDecimal = new BigDecimal(nmplDataCollect.getDataItemValue());
            if (cache.containsKey(formatter.format(nmplDataCollect.getUploadTime()))) {
                TimeDataVo timeDataVo = cache.get(formatter.format(nmplDataCollect.getUploadTime()));
                // 8Mbps = 1MB/s    byte->Mb 10^20  半小时 1800s
                timeDataVo.setValue( + bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getValue())).doubleValue());
            }
        }
    }

    /**
     * mysql获取12小时所有的历史数据
     * @param dataCollectReq
     * @param cache
     */
    private void queryAllDataFromMysql(DataCollectReq dataCollectReq,Map<String,TimeDataVo> cache){
        // 如果redis查询为空 或者redis查询异常 则通过mysql获取12小时历史数据
        NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
        nmplDataCollectExample.createCriteria().andDataItemCodeEqualTo(dataCollectReq.getDataItemCode())
                .andDeviceIpEqualTo(dataCollectReq.getDeviceIp())
                .andUploadTimeGreaterThan(TimeUtil.getTimeBeforeHours(TWELVE,ZERO));
        List<NmplDataCollect> dataCollectList = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        for (NmplDataCollect nmplDataCollect : dataCollectList) {
            BigDecimal bigDecimal = new BigDecimal(nmplDataCollect.getDataItemValue());
            if (cache.containsKey(formatter.format(nmplDataCollect.getUploadTime()))) {
                TimeDataVo timeDataVo = cache.get(formatter.format(nmplDataCollect.getUploadTime()));
                // 8Mbps = 1MB/s    byte->Mb 10^20  半小时 1800s
                timeDataVo.setValue( + bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(timeDataVo.getValue())).doubleValue());
            } else {
                TimeDataVo timeDataVo = new TimeDataVo();
                timeDataVo.setDate(nmplDataCollect.getUploadTime());
                timeDataVo.setValue(bigDecimal.divide(new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
                cache.put(formatter.format(nmplDataCollect.getUploadTime()), timeDataVo);
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
