package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplErrorPushLog;
import com.matrictime.network.dao.model.NmplErrorPushLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplErrorPushLogMapper {
    long countByExample(NmplErrorPushLogExample example);

    int deleteByExample(NmplErrorPushLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplErrorPushLog record);

    int insertSelective(NmplErrorPushLog record);

    List<NmplErrorPushLog> selectByExampleWithBLOBs(NmplErrorPushLogExample example);

    List<NmplErrorPushLog> selectByExample(NmplErrorPushLogExample example);

    NmplErrorPushLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplErrorPushLog record, @Param("example") NmplErrorPushLogExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplErrorPushLog record, @Param("example") NmplErrorPushLogExample example);

    int updateByExample(@Param("record") NmplErrorPushLog record, @Param("example") NmplErrorPushLogExample example);

    int updateByPrimaryKeySelective(NmplErrorPushLog record);

    int updateByPrimaryKeyWithBLOBs(NmplErrorPushLog record);

    int updateByPrimaryKey(NmplErrorPushLog record);
}