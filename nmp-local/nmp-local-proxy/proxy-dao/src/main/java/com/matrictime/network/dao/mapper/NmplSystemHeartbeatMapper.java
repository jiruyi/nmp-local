package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplSystemHeartbeat;
import com.matrictime.network.dao.model.NmplSystemHeartbeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplSystemHeartbeatMapper {
    long countByExample(NmplSystemHeartbeatExample example);

    int deleteByExample(NmplSystemHeartbeatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplSystemHeartbeat record);

    int insertSelective(NmplSystemHeartbeat record);

    List<NmplSystemHeartbeat> selectByExample(NmplSystemHeartbeatExample example);

    NmplSystemHeartbeat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplSystemHeartbeat record, @Param("example") NmplSystemHeartbeatExample example);

    int updateByExample(@Param("record") NmplSystemHeartbeat record, @Param("example") NmplSystemHeartbeatExample example);

    int updateByPrimaryKeySelective(NmplSystemHeartbeat record);

    int updateByPrimaryKey(NmplSystemHeartbeat record);
}