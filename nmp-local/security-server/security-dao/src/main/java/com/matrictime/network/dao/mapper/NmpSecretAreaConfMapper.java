package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpSecretAreaConf;
import com.matrictime.network.dao.model.NmpSecretAreaConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpSecretAreaConfMapper {
    long countByExample(NmpSecretAreaConfExample example);

    int deleteByExample(NmpSecretAreaConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpSecretAreaConf record);

    int insertSelective(NmpSecretAreaConf record);

    List<NmpSecretAreaConf> selectByExample(NmpSecretAreaConfExample example);

    NmpSecretAreaConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpSecretAreaConf record, @Param("example") NmpSecretAreaConfExample example);

    int updateByExample(@Param("record") NmpSecretAreaConf record, @Param("example") NmpSecretAreaConfExample example);

    int updateByPrimaryKeySelective(NmpSecretAreaConf record);

    int updateByPrimaryKey(NmpSecretAreaConf record);
}