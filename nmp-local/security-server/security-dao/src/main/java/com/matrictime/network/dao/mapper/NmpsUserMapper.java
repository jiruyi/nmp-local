package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsUser;
import com.matrictime.network.dao.model.NmpsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsUserMapper {
    long countByExample(NmpsUserExample example);

    int deleteByExample(NmpsUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(NmpsUser record);

    int insertSelective(NmpsUser record);

    List<NmpsUser> selectByExample(NmpsUserExample example);

    NmpsUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") NmpsUser record, @Param("example") NmpsUserExample example);

    int updateByExample(@Param("record") NmpsUser record, @Param("example") NmpsUserExample example);

    int updateByPrimaryKeySelective(NmpsUser record);

    int updateByPrimaryKey(NmpsUser record);
}