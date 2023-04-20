package com.matrictime.network.convert;

import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.model.AlarmInfo;
import org.mapstruct.Mapper;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 17:17
 * @desc
 */
@Mapper(componentModel = "spring")
public interface AlarmInfoConvert extends  BasicObjectMapper<com.matrictime.network.dao.model.NmplAlarmInfo , com.matrictime.network.model.AlarmInfo>{
    @Override
    AlarmInfo to(NmplAlarmInfo var1);
}
