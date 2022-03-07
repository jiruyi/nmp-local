package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.dao.model.NmplOperateLogExample;
import java.util.List;

import com.matrictime.network.request.LogRequest;
import org.apache.ibatis.annotations.Param;

public interface NmplOperateLogMapper {
    long countByExample(NmplOperateLogExample example);

    int deleteByExample(NmplOperateLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplOperateLog record);

    int insertSelective(NmplOperateLog record);

    List<NmplOperateLog> selectByExample(NmplOperateLogExample example);

    NmplOperateLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplOperateLog record, @Param("example") NmplOperateLogExample example);

    int updateByExample(@Param("record") NmplOperateLog record, @Param("example") NmplOperateLogExample example);

    int updateByPrimaryKeySelective(NmplOperateLog record);

    int updateByPrimaryKey(NmplOperateLog record);

    List<NmplOperateLog>  queryLogList(LogRequest logRequest);
}