package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpCommAreaConf;
import com.matrictime.network.dao.model.NmpCommAreaConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpCommAreaConfMapper {
    long countByExample(NmpCommAreaConfExample example);

    int deleteByExample(NmpCommAreaConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpCommAreaConf record);

    int insertSelective(NmpCommAreaConf record);

    List<NmpCommAreaConf> selectByExample(NmpCommAreaConfExample example);

    NmpCommAreaConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpCommAreaConf record, @Param("example") NmpCommAreaConfExample example);

    int updateByExample(@Param("record") NmpCommAreaConf record, @Param("example") NmpCommAreaConfExample example);

    int updateByPrimaryKeySelective(NmpCommAreaConf record);

    int updateByPrimaryKey(NmpCommAreaConf record);
}