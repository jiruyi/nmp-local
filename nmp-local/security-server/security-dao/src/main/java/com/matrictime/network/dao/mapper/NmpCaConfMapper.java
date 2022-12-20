package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpCaConf;
import com.matrictime.network.dao.model.NmpCaConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpCaConfMapper {
    long countByExample(NmpCaConfExample example);

    int deleteByExample(NmpCaConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpCaConf record);

    int insertSelective(NmpCaConf record);

    List<NmpCaConf> selectByExample(NmpCaConfExample example);

    NmpCaConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpCaConf record, @Param("example") NmpCaConfExample example);

    int updateByExample(@Param("record") NmpCaConf record, @Param("example") NmpCaConfExample example);

    int updateByPrimaryKeySelective(NmpCaConf record);

    int updateByPrimaryKey(NmpCaConf record);
}