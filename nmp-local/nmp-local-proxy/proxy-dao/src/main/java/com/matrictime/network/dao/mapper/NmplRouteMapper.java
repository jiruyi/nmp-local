package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplRoute;
import com.matrictime.network.dao.model.NmplRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplRouteMapper {
    long countByExample(NmplRouteExample example);

    int deleteByExample(NmplRouteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplRoute record);

    int insertSelective(NmplRoute record);

    List<NmplRoute> selectByExample(NmplRouteExample example);

    NmplRoute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplRoute record, @Param("example") NmplRouteExample example);

    int updateByExample(@Param("record") NmplRoute record, @Param("example") NmplRouteExample example);

    int updateByPrimaryKeySelective(NmplRoute record);

    int updateByPrimaryKey(NmplRoute record);
}