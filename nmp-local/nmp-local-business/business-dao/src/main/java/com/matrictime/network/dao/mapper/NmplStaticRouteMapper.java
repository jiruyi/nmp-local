package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplStaticRoute;
import com.matrictime.network.dao.model.NmplStaticRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplStaticRouteMapper {
    long countByExample(NmplStaticRouteExample example);

    int deleteByExample(NmplStaticRouteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplStaticRoute record);

    int insertSelective(NmplStaticRoute record);

    List<NmplStaticRoute> selectByExample(NmplStaticRouteExample example);

    NmplStaticRoute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplStaticRoute record, @Param("example") NmplStaticRouteExample example);

    int updateByExample(@Param("record") NmplStaticRoute record, @Param("example") NmplStaticRouteExample example);

    int updateByPrimaryKeySelective(NmplStaticRoute record);

    int updateByPrimaryKey(NmplStaticRoute record);
}