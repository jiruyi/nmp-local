package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplVersion;
import com.matrictime.network.dao.model.NmplVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplVersionMapper {
    long countByExample(NmplVersionExample example);

    int deleteByExample(NmplVersionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplVersion record);

    int insertSelective(NmplVersion record);

    List<NmplVersion> selectByExample(NmplVersionExample example);

    NmplVersion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplVersion record, @Param("example") NmplVersionExample example);

    int updateByExample(@Param("record") NmplVersion record, @Param("example") NmplVersionExample example);

    int updateByPrimaryKeySelective(NmplVersion record);

    int updateByPrimaryKey(NmplVersion record);
}