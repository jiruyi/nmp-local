package com.matrictime.network.convert;

import com.matrictime.network.dao.model.extend.NmplAlarmInfoExt;
import com.matrictime.network.modelVo.AlarmInfo;
import org.mapstruct.Mapper;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 17:17
 * @desc
 */
@Mapper(componentModel = "spring")
public interface AlarmInfoConvert extends  BasicObjectMapper<NmplAlarmInfoExt, AlarmInfo>{
    @Override
    AlarmInfo to(NmplAlarmInfoExt var1);
}
