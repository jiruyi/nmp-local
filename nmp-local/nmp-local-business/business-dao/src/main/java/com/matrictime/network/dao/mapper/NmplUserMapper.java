package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.NmplUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUserMapper {
    long countByExample(NmplUserExample example);

    int deleteByExample(NmplUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(NmplUser record);

    int insertSelective(NmplUser record);

    List<NmplUser> selectByExample(NmplUserExample example);

    NmplUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") NmplUser record, @Param("example") NmplUserExample example);

    int updateByExample(@Param("record") NmplUser record, @Param("example") NmplUserExample example);

    int updateByPrimaryKeySelective(NmplUser record);

    int updateByPrimaryKey(NmplUser record);
}