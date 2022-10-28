package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.dao.model.NmplBusinessRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplBusinessRouteMapper {
    long countByExample(NmplBusinessRouteExample example);

    int deleteByExample(NmplBusinessRouteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplBusinessRoute record);

    int insertSelective(NmplBusinessRoute record);

    List<NmplBusinessRoute> selectByExampleWithBLOBs(NmplBusinessRouteExample example);

    List<NmplBusinessRoute> selectByExample(NmplBusinessRouteExample example);

    NmplBusinessRoute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplBusinessRoute record, @Param("example") NmplBusinessRouteExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplBusinessRoute record, @Param("example") NmplBusinessRouteExample example);

    int updateByExample(@Param("record") NmplBusinessRoute record, @Param("example") NmplBusinessRouteExample example);

    int updateByPrimaryKeySelective(NmplBusinessRoute record);

    int updateByPrimaryKeyWithBLOBs(NmplBusinessRoute record);

    int updateByPrimaryKey(NmplBusinessRoute record);
}