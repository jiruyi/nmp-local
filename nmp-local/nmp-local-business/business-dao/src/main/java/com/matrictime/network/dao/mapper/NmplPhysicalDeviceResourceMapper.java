package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplPhysicalDeviceResource;
import com.matrictime.network.dao.model.NmplPhysicalDeviceResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplPhysicalDeviceResourceMapper {
    long countByExample(NmplPhysicalDeviceResourceExample example);

    int deleteByExample(NmplPhysicalDeviceResourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NmplPhysicalDeviceResource record);

    int insertSelective(NmplPhysicalDeviceResource record);

    List<NmplPhysicalDeviceResource> selectByExample(NmplPhysicalDeviceResourceExample example);

    NmplPhysicalDeviceResource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NmplPhysicalDeviceResource record, @Param("example") NmplPhysicalDeviceResourceExample example);

    int updateByExample(@Param("record") NmplPhysicalDeviceResource record, @Param("example") NmplPhysicalDeviceResourceExample example);

    int updateByPrimaryKeySelective(NmplPhysicalDeviceResource record);

    int updateByPrimaryKey(NmplPhysicalDeviceResource record);
}