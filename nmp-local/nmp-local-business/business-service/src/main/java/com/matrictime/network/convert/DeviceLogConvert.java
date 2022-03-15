package com.matrictime.network.convert;

import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.model.NmplDeviceLog;
import com.matrictime.network.model.DeviceLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/7 0007 14:20
 * @desc
 */
@Mapper(componentModel = "spring")
public interface DeviceLogConvert extends BasicObjectMapper<NmplDeviceLog, DeviceLog>{

    @Mappings({
            @Mapping(source = "operTime" ,target = "operTime",qualifiedByName = "toTimeStr"),
            @Mapping(source = "devcieName" ,target = "deviceName")
    })
    @Override
    DeviceLog to(NmplDeviceLog var1);

    default String toTimeStr(Date operTime){
        return operTime == null ? "" : DateUtils.formatDateToString(operTime, DateUtils.YYMMDDHHMM.get());
    }


}
