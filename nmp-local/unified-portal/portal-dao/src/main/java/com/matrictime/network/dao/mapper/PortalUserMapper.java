package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.PortalUser;
import com.matrictime.network.dao.model.PortalUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PortalUserMapper {
    long countByExample(PortalUserExample example);

    int deleteByExample(PortalUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(PortalUser record);

    int insertSelective(PortalUser record);

    List<PortalUser> selectByExample(PortalUserExample example);

    PortalUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") PortalUser record, @Param("example") PortalUserExample example);

    int updateByExample(@Param("record") PortalUser record, @Param("example") PortalUserExample example);

    int updateByPrimaryKeySelective(PortalUser record);

    int updateByPrimaryKey(PortalUser record);
}