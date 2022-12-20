package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpUser;
import com.matrictime.network.dao.model.NmpUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpUserMapper {
    long countByExample(NmpUserExample example);

    int deleteByExample(NmpUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(NmpUser record);

    int insertSelective(NmpUser record);

    List<NmpUser> selectByExample(NmpUserExample example);

    NmpUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") NmpUser record, @Param("example") NmpUserExample example);

    int updateByExample(@Param("record") NmpUser record, @Param("example") NmpUserExample example);

    int updateByPrimaryKeySelective(NmpUser record);

    int updateByPrimaryKey(NmpUser record);
}