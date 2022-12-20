package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpKeyInfo;
import com.matrictime.network.dao.model.NmpKeyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpKeyInfoMapper {
    long countByExample(NmpKeyInfoExample example);

    int deleteByExample(NmpKeyInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpKeyInfo record);

    int insertSelective(NmpKeyInfo record);

    List<NmpKeyInfo> selectByExample(NmpKeyInfoExample example);

    NmpKeyInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpKeyInfo record, @Param("example") NmpKeyInfoExample example);

    int updateByExample(@Param("record") NmpKeyInfo record, @Param("example") NmpKeyInfoExample example);

    int updateByPrimaryKeySelective(NmpKeyInfo record);

    int updateByPrimaryKey(NmpKeyInfo record);
}