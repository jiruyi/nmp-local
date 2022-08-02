package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.UserGroup;
import com.matrictime.network.dao.model.UserGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMapper {
    long countByExample(UserGroupExample example);

    int deleteByExample(UserGroupExample example);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    List<UserGroup> selectByExample(UserGroupExample example);

    int updateByExampleSelective(@Param("record") UserGroup record, @Param("example") UserGroupExample example);

    int updateByExample(@Param("record") UserGroup record, @Param("example") UserGroupExample example);
}