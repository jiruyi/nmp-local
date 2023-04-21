package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplPhysicalDeviceHeartbeat;
import com.matrictime.network.dao.model.NmplPhysicalDeviceHeartbeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplPhysicalDeviceHeartbeatMapper {
    long countByExample(NmplPhysicalDeviceHeartbeatExample example);

    int deleteByExample(NmplPhysicalDeviceHeartbeatExample example);

    int deleteByPrimaryKey(String ip1Ip2);

    int insert(NmplPhysicalDeviceHeartbeat record);

    int insertSelective(NmplPhysicalDeviceHeartbeat record);

    List<NmplPhysicalDeviceHeartbeat> selectByExample(NmplPhysicalDeviceHeartbeatExample example);

    NmplPhysicalDeviceHeartbeat selectByPrimaryKey(String ip1Ip2);

    int updateByExampleSelective(@Param("record") NmplPhysicalDeviceHeartbeat record, @Param("example") NmplPhysicalDeviceHeartbeatExample example);

    int updateByExample(@Param("record") NmplPhysicalDeviceHeartbeat record, @Param("example") NmplPhysicalDeviceHeartbeatExample example);

    int updateByPrimaryKeySelective(NmplPhysicalDeviceHeartbeat record);

    int updateByPrimaryKey(NmplPhysicalDeviceHeartbeat record);
}