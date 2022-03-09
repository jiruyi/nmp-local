package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplSignalIo;
import com.matrictime.network.dao.model.NmplSignalIoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplSignalIoMapper {
    long countByExample(NmplSignalIoExample example);

    int deleteByExample(NmplSignalIoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplSignalIo record);

    int insertSelective(NmplSignalIo record);

    List<NmplSignalIo> selectByExample(NmplSignalIoExample example);

    NmplSignalIo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplSignalIo record, @Param("example") NmplSignalIoExample example);

    int updateByExample(@Param("record") NmplSignalIo record, @Param("example") NmplSignalIoExample example);

    int updateByPrimaryKeySelective(NmplSignalIo record);

    int updateByPrimaryKey(NmplSignalIo record);
}