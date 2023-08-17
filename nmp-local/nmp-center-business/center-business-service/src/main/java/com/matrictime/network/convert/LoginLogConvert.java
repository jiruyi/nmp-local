package com.matrictime.network.convert;

import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.modelVo.LoginDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/7 0007 9:51
 * @desc
 */
@Mapper(componentModel = "spring")
public interface LoginLogConvert extends BasicObjectMapper<NmplLoginDetail, LoginDetail>{

    @Mappings({
            @Mapping(source = "createTime" ,target = "loginTime",qualifiedByName = "toLoginTimeStr"),
            @Mapping(source = "id" ,target = "id",qualifiedByName = "toStringId")
    })
    @Override
    LoginDetail to(NmplLoginDetail var1);

    default String toLoginTimeStr(Date createTime){
        return createTime == null ? "" : DateUtils.formatDateToString(createTime, DateUtils.YYMMDDHHMM.get());
    }
    @Named("toStringId")
    default String toStringId(Long id){
        return ObjectUtils.isEmpty(id) ? "": String.valueOf(id);
    }


}
