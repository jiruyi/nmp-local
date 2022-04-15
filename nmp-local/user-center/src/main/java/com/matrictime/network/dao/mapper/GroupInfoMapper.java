package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.GroupInfo;
import com.matrictime.network.dao.model.GroupInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupInfoMapper {
    long countByExample(GroupInfoExample example);

    int deleteByExample(GroupInfoExample example);

    int deleteByPrimaryKey(Long groupId);

    int insert(GroupInfo record);

    int insertSelective(GroupInfo record);

    List<GroupInfo> selectByExample(GroupInfoExample example);

    GroupInfo selectByPrimaryKey(Long groupId);

    int updateByExampleSelective(@Param("record") GroupInfo record, @Param("example") GroupInfoExample example);

    int updateByExample(@Param("record") GroupInfo record, @Param("example") GroupInfoExample example);

    int updateByPrimaryKeySelective(GroupInfo record);

    int updateByPrimaryKey(GroupInfo record);
}