package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsSecurityServerInfoMapper {
    long countByExample(NmpsSecurityServerInfoExample example);

    int deleteByExample(NmpsSecurityServerInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsSecurityServerInfo record);

    int insertSelective(NmpsSecurityServerInfo record);

    List<NmpsSecurityServerInfo> selectByExample(NmpsSecurityServerInfoExample example);

    NmpsSecurityServerInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsSecurityServerInfo record, @Param("example") NmpsSecurityServerInfoExample example);

    int updateByExample(@Param("record") NmpsSecurityServerInfo record, @Param("example") NmpsSecurityServerInfoExample example);

    int updateByPrimaryKeySelective(NmpsSecurityServerInfo record);

    int updateByPrimaryKey(NmpsSecurityServerInfo record);
}