package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.AddUserRequest;
import com.matrictime.network.dao.model.AddUserRequestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddUserRequestMapper {
    long countByExample(AddUserRequestExample example);

    int deleteByExample(AddUserRequestExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AddUserRequest record);

    int insertSelective(AddUserRequest record);

    List<AddUserRequest> selectByExample(AddUserRequestExample example);

    AddUserRequest selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AddUserRequest record, @Param("example") AddUserRequestExample example);

    int updateByExample(@Param("record") AddUserRequest record, @Param("example") AddUserRequestExample example);

    int updateByPrimaryKeySelective(AddUserRequest record);

    int updateByPrimaryKey(AddUserRequest record);
}