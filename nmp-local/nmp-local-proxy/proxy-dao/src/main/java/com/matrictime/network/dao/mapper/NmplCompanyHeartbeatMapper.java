package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.dao.model.NmplCompanyHeartbeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplCompanyHeartbeatMapper {
    long countByExample(NmplCompanyHeartbeatExample example);

    int deleteByExample(NmplCompanyHeartbeatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplCompanyHeartbeat record);

    int insertSelective(NmplCompanyHeartbeat record);

    List<NmplCompanyHeartbeat> selectByExample(NmplCompanyHeartbeatExample example);

    NmplCompanyHeartbeat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplCompanyHeartbeat record, @Param("example") NmplCompanyHeartbeatExample example);

    int updateByExample(@Param("record") NmplCompanyHeartbeat record, @Param("example") NmplCompanyHeartbeatExample example);

    int updateByPrimaryKeySelective(NmplCompanyHeartbeat record);

    int updateByPrimaryKey(NmplCompanyHeartbeat record);
}