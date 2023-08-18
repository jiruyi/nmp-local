package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDataPushRecord;
import com.matrictime.network.dao.model.NmplDataPushRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDataPushRecordMapper {
    long countByExample(NmplDataPushRecordExample example);

    int deleteByExample(NmplDataPushRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDataPushRecord record);

    int insertSelective(NmplDataPushRecord record);

    List<NmplDataPushRecord> selectByExample(NmplDataPushRecordExample example);

    NmplDataPushRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDataPushRecord record, @Param("example") NmplDataPushRecordExample example);

    int updateByExample(@Param("record") NmplDataPushRecord record, @Param("example") NmplDataPushRecordExample example);

    int updateByPrimaryKeySelective(NmplDataPushRecord record);

    int updateByPrimaryKey(NmplDataPushRecord record);
}