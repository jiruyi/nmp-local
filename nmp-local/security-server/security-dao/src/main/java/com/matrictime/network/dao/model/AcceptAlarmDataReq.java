package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.List;

@Data
public class AcceptAlarmDataReq{

    List<NmpsAlarmInfo> alarmInfoList;

    String redisKey;
}
