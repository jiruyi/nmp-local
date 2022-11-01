package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.PortalSystem;
import com.matrictime.network.dao.model.PortalSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PortalSystemMapper {
    long countByExample(PortalSystemExample example);

    int deleteByExample(PortalSystemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PortalSystem record);

    int insertSelective(PortalSystem record);

    List<PortalSystem> selectByExample(PortalSystemExample example);

    PortalSystem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PortalSystem record, @Param("example") PortalSystemExample example);

    int updateByExample(@Param("record") PortalSystem record, @Param("example") PortalSystemExample example);

    int updateByPrimaryKeySelective(PortalSystem record);

    int updateByPrimaryKey(PortalSystem record);
}