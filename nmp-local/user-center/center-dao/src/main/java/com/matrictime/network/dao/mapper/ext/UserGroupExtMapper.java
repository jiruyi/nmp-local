package com.matrictime.network.dao.mapper.ext;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.UserGroupReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupExtMapper {
    Integer updateByUserIdAndGroupId(UserGroupReq userGroupReq);

    List<UserGroupVo> selectByCondition(UserGroupReq userGroupReq);

    List<UserGroupVo> selectByGroupIds(@Param("list") List<String>list);
}
