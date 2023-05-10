package com.matrictime.network.service.impl;


import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.matrictime.network.util.DateUtils.DATE_DF_INT;
import static com.matrictime.network.util.DateUtils.MINUTE_TIME_FORMAT;

@Slf4j
public class CommonServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取x轴时间坐标
     * @param date
     * @param step 距离date多少个点
     * @param period 间隔多少秒
     * @param format 坐标轴样式
     * @return
     */
    public static List<String> getXTimePerHalfHour(Date date, int step, int period, String format){
        List<String> resList = new ArrayList<>();
        Date tempDate = date;
        if (step < 0){
            step = -step;
            for (int i=0; i<step; i++){
                tempDate = DateUtils.addSecondsForDate(date,-i*period);
                String res = DateUtils.formatDateToString2(tempDate, format);
                resList.add(res);
            }
            Collections.reverse(resList);
        }else {
            for (int i=0; i<step; i++){
                tempDate = DateUtils.addSecondsForDate(date,i*period);
                String res = DateUtils.formatDateToString2(tempDate, format);
                resList.add(res);
            }
        }
        return resList;
    }

    /**
     * 根据时间字符串获取时间秒和毫秒为0（yyMMddHH:mm）
     * @param str
     * @return
     */
    public static Date getDateByStr(String str){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_DF_INT+MINUTE_TIME_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            log.warn("CommonServiceImpl.getDateByStr ParseException:{}",e);
        }
        return date;
    }


    public static void main(String[] args) {
//        List<String> hour = getXTimePerHalfHour(DateUtils.getRecentHalfTime(new Date()), -24, 30 * 60, DateUtils.MINUTE_TIME_FORMAT);
        Date dateByStr = getDateByStr(DateUtils.formatDateToInteger(new Date()) + "11:30");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");
        System.out.println(sdf.format(dateByStr));
    }
}

