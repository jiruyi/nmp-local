package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplInternetRoute;
import com.matrictime.network.dao.model.NmplInternetRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplInternetRouteMapper {
    long countByExample(NmplInternetRouteExample example);

    int deleteByExample(NmplInternetRouteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplInternetRoute record);

    int insertSelective(NmplInternetRoute record);

    List<NmplInternetRoute> selectByExampleWithBLOBs(NmplInternetRouteExample example);

    List<NmplInternetRoute> selectByExample(NmplInternetRouteExample example);

    NmplInternetRoute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplInternetRoute record, @Param("example") NmplInternetRouteExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplInternetRoute record, @Param("example") NmplInternetRouteExample example);

    int updateByExample(@Param("record") NmplInternetRoute record, @Param("example") NmplInternetRouteExample example);

    int updateByPrimaryKeySelective(NmplInternetRoute record);

    int updateByPrimaryKeyWithBLOBs(NmplInternetRoute record);

    int updateByPrimaryKey(NmplInternetRoute record);
}