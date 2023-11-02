package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsDataInfo;
import com.matrictime.network.dao.model.NmpsDataInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsDataInfoMapper {
    long countByExample(NmpsDataInfoExample example);

    int deleteByExample(NmpsDataInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsDataInfo record);

    int insertSelective(NmpsDataInfo record);

    List<NmpsDataInfo> selectByExample(NmpsDataInfoExample example);

    NmpsDataInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsDataInfo record, @Param("example") NmpsDataInfoExample example);

    int updateByExample(@Param("record") NmpsDataInfo record, @Param("example") NmpsDataInfoExample example);

    int updateByPrimaryKeySelective(NmpsDataInfo record);

    int updateByPrimaryKey(NmpsDataInfo record);
}