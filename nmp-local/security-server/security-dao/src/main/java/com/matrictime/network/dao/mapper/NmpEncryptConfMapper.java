package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpEncryptConf;
import com.matrictime.network.dao.model.NmpEncryptConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpEncryptConfMapper {
    long countByExample(NmpEncryptConfExample example);

    int deleteByExample(NmpEncryptConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpEncryptConf record);

    int insertSelective(NmpEncryptConf record);

    List<NmpEncryptConf> selectByExample(NmpEncryptConfExample example);

    NmpEncryptConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpEncryptConf record, @Param("example") NmpEncryptConfExample example);

    int updateByExample(@Param("record") NmpEncryptConf record, @Param("example") NmpEncryptConfExample example);

    int updateByPrimaryKeySelective(NmpEncryptConf record);

    int updateByPrimaryKey(NmpEncryptConf record);
}