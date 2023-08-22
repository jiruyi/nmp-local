package com.matrictime.network.base.util;

import com.matrictime.network.modelVo.TimeDataVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class TimeUtil {

    /**
     * 获取过去时间
     * @param hours
     * @param minutes
     * @return
     */
    public static Date getTimeBeforeHours(Integer hours, Integer minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        calendar.add(Calendar.MINUTE,-minutes);
        Date timeBeforeOneDay = calendar.getTime();
        return timeBeforeOneDay;
    }

    public static boolean checkTime(String time){
        // 11.19 拿数据的范围为 23.00-11.00 13.19 1.00->13.00
        Integer max = Integer.valueOf(getOnTime().replaceAll(":", ""));
        Integer value = Integer.valueOf(time.replaceAll(":",""));
        if(max>1200){
            Integer min = max -1200;
            if(value<=max&&value>=min){
                return true;
            }
        }else {
            Integer min = 1200+max;
            if(value>=min || value<=max){
                return true;
            }
        }
        return false;
    }

    public static String getOnTime() {
        //获取系统时间
        LocalDateTime now = LocalDateTime.now();
        //获取分
        int minute = now.getMinute();
        int count = minute/30;
        switch (count){
            case 0:
                now = now.withMinute(0);
                break;
            case 1:
                now = now.withMinute(30);
                break;
        }
        //格式化
        return DateTimeFormatter.ofPattern("HH:mm").format(now);
    }

    public static boolean IsTodayDate(Date date){
        // 将Date对象转换为LocalDate对象
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 获取今天的日期
        LocalDate today = LocalDate.now();

        // 判断是否是今天
        if (localDate.equals(today)) {
            return true;
        } else {
            return false;
        }
    }

}
