package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpDnsConf;
import com.matrictime.network.dao.model.NmpDnsConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpDnsConfMapper {
    long countByExample(NmpDnsConfExample example);

    int deleteByExample(NmpDnsConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpDnsConf record);

    int insertSelective(NmpDnsConf record);

    List<NmpDnsConf> selectByExample(NmpDnsConfExample example);

    NmpDnsConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpDnsConf record, @Param("example") NmpDnsConfExample example);

    int updateByExample(@Param("record") NmpDnsConf record, @Param("example") NmpDnsConfExample example);

    int updateByPrimaryKeySelective(NmpDnsConf record);

    int updateByPrimaryKey(NmpDnsConf record);
}