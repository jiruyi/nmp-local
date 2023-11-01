package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpsDataInfoMapper;
import com.matrictime.network.dao.mapper.ext.DataInfoMapper;
import com.matrictime.network.dao.model.NmpsDataInfo;
import com.matrictime.network.dao.model.NmpsDataInfoExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.DataInfoReq;
import com.matrictime.network.req.DataReq;
import com.matrictime.network.resp.DailyDataResp;
import com.matrictime.network.resp.DataResp;
import com.matrictime.network.service.DataService;
import com.matrictime.network.util.DateUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static com.matrictime.network.base.constant.DataConstants.ONE_THOUSAND_AND_TWENTY_FOUR;
import static com.matrictime.network.base.constant.DataConstants.ZERO_DOUBLE;

@Service
public class DataServiceImpl extends SystemBaseService implements DataService {
    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);
    @Resource
    private DataInfoMapper dataInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private NmpsDataInfoMapper nmpsDataInfoMapper;


    /**
     * 获取波形图数据
     * @param dataReq
     * @return
     */
    @Override
    public Result<List<DailyDataResp>> queryWaveformData(DataReq dataReq) {
        ArrayList dataRespList = new ArrayList();
        Result result;
        try {
            checkParam(dataReq);
            if (dataReq.isFlag()) {
                dataRespList.add(this.getDailyData("", dataReq.getDataType(), dataReq.getNetworkId(), dataReq.isFlag()));
            } else {
                for (String time : dataReq.getTime()) {
                    dataRespList.add(this.getDailyData(time, dataReq.getDataType(), dataReq.getNetworkId(), dataReq.isFlag()));
                }
            }
            result = this.buildResult(dataRespList);
        } catch (SystemException e) {
            log.error(e.getMessage());
            result = this.failResult(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = this.failResult("");
        }

        return result;
    }

    /**
     * 获取最新数据
     * @param dataReq
     * @return
     */
    @Override
    public Result queryCurrentData(DataReq dataReq) {
        Result result = new Result();
        try {
            this.checkParam(dataReq);

            NmpsDataInfoExample nmpsDataInfoExample = new NmpsDataInfoExample();
            NmpsDataInfoExample.Criteria criteria = nmpsDataInfoExample.createCriteria();
            criteria.andDataTypeEqualTo(dataReq.getDataType()).andNetworkIdEqualTo(dataReq.getNetworkId());
            List<NmpsDataInfo> nmpsDataInfoList = new ArrayList<>();
            BigDecimal value = new BigDecimal(ZERO_DOUBLE);
            if((dataReq.getDataType().equals(DataConstants.LAST_UP_DATA_VALUE) ||
                    dataReq.getDataType().equals(DataConstants.LAST_DOWN_DATA_VALUE))){
                criteria.andUploadTimeEqualTo(DateUtils.getCurrentHourTime(new Date()));
                nmpsDataInfoList = nmpsDataInfoMapper.selectByExample(nmpsDataInfoExample);
                if(!CollectionUtils.isEmpty(nmpsDataInfoList)){
                    value.add(new BigDecimal(nmpsDataInfoList.get(0).getDataValue()).divide(new BigDecimal(ONE_THOUSAND_AND_TWENTY_FOUR*ONE_THOUSAND_AND_TWENTY_FOUR),2,4));
                }
            }else {
                criteria.andUploadTimeGreaterThanOrEqualTo(DateUtils.getCurrentHourTime(new Date()));
                nmpsDataInfoList = nmpsDataInfoMapper.selectByExample(nmpsDataInfoExample);
                for (NmpsDataInfo nmpsDataInfo : nmpsDataInfoList) {
                    value.add(new BigDecimal(nmpsDataInfo.getDataValue()).divide(new BigDecimal(ONE_THOUSAND_AND_TWENTY_FOUR*ONE_THOUSAND_AND_TWENTY_FOUR),2,4));
                }
            }
            result.setResultObj(value.doubleValue());
        } catch (SystemException e) {
            log.error(e.getMessage());
            result = this.failResult(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = this.failResult("");
        }
        return result;
    }

    /**
     * 插入统计数据
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result insert(DataInfoReq req) {
        Result result = new Result();
        try {
            this.checkParam(req);
            this.dataInfoMapper.batchInsert(req.getDataInfoVoList());
            this.redisTemplate.opsForValue().set(req.getKey(), req.getIndex());
        } catch (SystemException e) {
            log.error(e.getMessage());
            result = this.failResult(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = this.failResult("");
        }
        return result;
    }

    /**
     * 入参校验
     * @param req
     */
    private void checkParam(DataInfoReq req) {
        if (CollectionUtils.isEmpty(req.getDataInfoVoList())) {
            throw new SystemException(ErrorMessageContants.REPORT_EMPTY_DATA);
        } else if (req.getIndex() == null || req.getKey() == null) {
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 入参校验
     * @param dataReq
     */
    private void checkParam(DataReq dataReq) {
        if (dataReq.getDataType() == null || dataReq.getNetworkId() == null) {
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


    /**
     * 获取某天某种类型的数据
     * @param date 时间
     * @param code 数据类型
     * @param networkId 入网ID
     * @param flag 是否默认
     * @return
     * @throws Exception
     */
    public DailyDataResp getDailyData(String date, String code, String networkId, boolean flag) throws Exception {
        DailyDataResp dailyDataResp = new DailyDataResp();

        List<String> timeList = new ArrayList<>();
        Map<String,Double> map = new HashMap<>();
        if (flag) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(new Date());
            //获取往前推24小时的时间点
            timeList = this.getXTimePerHalfHour(DateUtils.getCurrentHourTime(new Date()), -24, 3600, "HH:mm");
            //从mysql中获取数据
            map = this.getDataFromMysql(code, networkId, date, flag, new HashSet(timeList));
        } else {
            //获取一天24个时间点
            timeList = this.get24Hours();
            //从redis中获取缓存数据
            map = this.getCacheFromRedis(networkId + "-" + date + "-" + code, date);
            if (map.isEmpty()) {
                //从mysql中获取数据
                map = this.getDataFromMysql(code, networkId, date, flag, new HashSet(timeList));
            }
        }

        dailyDataResp.setDate(date);
        for (String time : timeList) {
            DataResp dataResp = new DataResp();
            dataResp.setTime(time);
            if (map.get(time) != null) {
                dataResp.setValue(String.valueOf(map.get(time)));
            } else {
                map.put(time, ZERO_DOUBLE);
                dataResp.setValue(String.valueOf(ZERO_DOUBLE));
            }
            dailyDataResp.getDataRespList().add(dataResp);
        }


        if (!flag && this.isToday(date, "yyyy-MM-dd")) {
            this.redisTemplate.opsForHash().putAll(networkId + "-" + date + "-" + code, map);
        }

        return dailyDataResp;
    }

    /**
     * 判断是否是当天
     * @param str
     * @param formatStr
     * @return
     * @throws Exception
     */
    public boolean isToday(String str, String formatStr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        date = format.parse(str);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DATE);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DATE);
        return year1 == year2 && month1 == month2 && day1 == day2;
    }


    /**
     * 从redis中获取缓存数据 没有则返回一个空Map
     * @param key
     * @param date
     * @return
     * @throws Exception
     */
    public Map<String, Double> getCacheFromRedis(String key, String date) throws Exception {
        if (this.isToday(date, "yyyy-MM-dd")) {
            return new HashMap();
        } else if (!this.redisTemplate.hasKey(key)) {
            return new HashMap();
        } else {
            Map<String, Double> data = this.redisTemplate.opsForHash().entries(key);
            return data;
        }
    }


    /**
     * 获取往该时间段推移的时间点
     * @param date
     * @param step
     * @param period
     * @param format
     * @return
     */
    public List<String> getXTimePerHalfHour(Date date, int step, int period, String format) {
        List<String> resList = new ArrayList();
        Date tempDate;
        int i;
        String res;
        if (step < 0) {
            step = -step;

            for(i = 0; i < step; ++i) {
                tempDate = DateUtils.addSecondsForDate(date, -i * period);
                res = DateUtils.formatDateToString2(tempDate, format);
                resList.add(res);
            }

            Collections.reverse(resList);
        } else {
            for(i = 0; i < step; ++i) {
                tempDate = DateUtils.addSecondsForDate(date, i * period);
                res = DateUtils.formatDateToString2(tempDate, format);
                resList.add(res);
            }
        }

        return resList;
    }

    /**
     * 获取一天24小时的时间点
     * @return
     */
    public List<String> get24Hours() {
        List<String> time = new ArrayList();
        for(int i = 0; i <= 23; ++i) {
            if (i < 10) {
                time.add("0" + i + ":00");
            } else {
                time.add(i + ":00");
            }
        }

        return time;
    }


    /**
     * 从mysql中获取数据
     * @param code 数据类型
     * @param networkId 入网id
     * @param date 时间
     * @param flag 是否是默认
     * @param set 时间点集合
     * @return
     */
    public Map<String, Double> getDataFromMysql(String code, String networkId, String date, boolean flag, HashSet<String> set) {
        Map<String, Double> data = new HashMap();
        List<NmpsDataInfo> nmpsDataInfoList = new ArrayList();;
        if (flag) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -23);
            calendar.set(Calendar.MINUTE, 0);
            NmpsDataInfoExample nmpsDataInfoExample = new NmpsDataInfoExample();
            nmpsDataInfoExample.createCriteria().andUploadTimeGreaterThanOrEqualTo(calendar.getTime()).andDataTypeEqualTo(code).andNetworkIdEqualTo(networkId);
            nmpsDataInfoList = this.nmpsDataInfoMapper.selectByExample(nmpsDataInfoExample);
        } else {
            nmpsDataInfoList = this.dataInfoMapper.selectDataList(code, networkId, date);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        for (NmpsDataInfo nmpsDataInfo : nmpsDataInfoList) {
            if((code.equals(DataConstants.LAST_UP_DATA_VALUE) || code.equals(DataConstants.LAST_DOWN_DATA_VALUE)) && !set.contains(formatter.format(nmpsDataInfo.getUploadTime()))){
                continue;
            }
            Double value = data.getOrDefault(DateUtils.getCurrentHourTime(nmpsDataInfo.getUploadTime()), ZERO_DOUBLE);
            BigDecimal bigDecimal = new BigDecimal(nmpsDataInfo.getDataValue());
            bigDecimal.divide(BigDecimal.valueOf(ONE_THOUSAND_AND_TWENTY_FOUR*ONE_THOUSAND_AND_TWENTY_FOUR), 2, 4).add(BigDecimal.valueOf(value));
            data.put(formatter.format(DateUtils.getCurrentHourTime(nmpsDataInfo.getUploadTime())), bigDecimal.doubleValue());
        }
        return data;
    }
}
