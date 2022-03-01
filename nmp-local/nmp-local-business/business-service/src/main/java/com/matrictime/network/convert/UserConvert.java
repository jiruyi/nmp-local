package com.matrictime.network.convert;

import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.request.UserRequest;
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
 * @date 2022/3/1 0001 9:25
 * @desc
 */
@Mapper(componentModel = "spring")
public interface UserConvert extends BasicObjectMapper<NmplUser, UserRequest>{

    @Mappings({
            @Mapping(source = "createTime" ,target = "createTime",qualifiedByName = "toTimeStr"),
            @Mapping(source = "userId" ,target = "userId",qualifiedByName = "toStringUserId")
    })
    @Override
    UserRequest to(NmplUser var1);

    @Named("toTimeStr")
    default String toTimeStr(Date createTime){
        return createTime == null ? "" : DateUtils.formatDateToString(createTime, DateUtils.YYMMDDHHMM.get());
    }
    @Named("toStringUserId")
    default String toStringUserId(Long userId){
        return ObjectUtils.isEmpty(userId) ? "": String.valueOf(userId);
    }
}
