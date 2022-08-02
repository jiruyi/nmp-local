package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.UserFriend;
import com.matrictime.network.dao.model.UserFriendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFriendMapper {
    long countByExample(UserFriendExample example);

    int deleteByExample(UserFriendExample example);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    List<UserFriend> selectByExample(UserFriendExample example);

    int updateByExampleSelective(@Param("record") UserFriend record, @Param("example") UserFriendExample example);

    int updateByExample(@Param("record") UserFriend record, @Param("example") UserFriendExample example);
}