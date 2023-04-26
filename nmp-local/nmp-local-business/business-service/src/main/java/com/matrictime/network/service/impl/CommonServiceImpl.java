package com.matrictime.network.service.impl;


import com.matrictime.network.util.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CommonServiceImpl {

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


    public static void main(String[] args) {
        List<String> hour = getXTimePerHalfHour(DateUtils.getRecentHalfTime(new Date()), -24, 30 * 60, DateUtils.MINUTE_TIME_FORMAT);
        System.out.println(hour.toString());
    }
}

