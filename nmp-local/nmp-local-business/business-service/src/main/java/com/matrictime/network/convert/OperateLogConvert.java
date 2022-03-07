package com.matrictime.network.convert;

import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.model.OperateLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/8 0008 17:16
 * @desc
 */
@Mapper(componentModel = "spring")
public interface OperateLogConvert extends  BasicObjectMapper<NmplOperateLog, OperateLog>{

    @Mappings({
           @Mapping(source = "operTime" ,target = "operTime",qualifiedByName = "toOperTimeStr"),
            @Mapping(source = "id" ,target = "id",qualifiedByName = "toStringId")
    })
    @Override
    OperateLog to(NmplOperateLog var1);

    default String toOperTimeStr(Date operTime){
        return operTime == null ? "" : DateUtils.formatDateToString(operTime, DateUtils.YYMMDDHHMM.get());
    }

    default String toStringId(Long id){
        return ObjectUtils.isEmpty(id) ? "": String.valueOf(id);
    }
}
