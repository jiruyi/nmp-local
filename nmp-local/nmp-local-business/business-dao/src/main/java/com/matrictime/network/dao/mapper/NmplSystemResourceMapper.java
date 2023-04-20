package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplSystemResource;
import com.matrictime.network.dao.model.NmplSystemResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplSystemResourceMapper {
    long countByExample(NmplSystemResourceExample example);

    int deleteByExample(NmplSystemResourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NmplSystemResource record);

    int insertSelective(NmplSystemResource record);

    List<NmplSystemResource> selectByExample(NmplSystemResourceExample example);

    NmplSystemResource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NmplSystemResource record, @Param("example") NmplSystemResourceExample example);

    int updateByExample(@Param("record") NmplSystemResource record, @Param("example") NmplSystemResourceExample example);

    int updateByPrimaryKeySelective(NmplSystemResource record);

    int updateByPrimaryKey(NmplSystemResource record);
}