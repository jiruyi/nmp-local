package com.matrictime.network.request;

import com.matrictime.network.model.AlarmInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AcceptAlarmDataReq implements Serializable {

    private static final long serialVersionUID = 4213294915485842284L;

    List<AlarmInfo> alarmInfoList;

    String cpuId;
}
