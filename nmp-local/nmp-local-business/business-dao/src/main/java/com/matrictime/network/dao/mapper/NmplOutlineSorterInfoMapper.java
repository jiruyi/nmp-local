package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplOutlineSorterInfoMapper {
    long countByExample(NmplOutlineSorterInfoExample example);

    int deleteByExample(NmplOutlineSorterInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplOutlineSorterInfo record);

    int insertSelective(NmplOutlineSorterInfo record);

    List<NmplOutlineSorterInfo> selectByExample(NmplOutlineSorterInfoExample example);

    NmplOutlineSorterInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplOutlineSorterInfo record, @Param("example") NmplOutlineSorterInfoExample example);

    int updateByExample(@Param("record") NmplOutlineSorterInfo record, @Param("example") NmplOutlineSorterInfoExample example);

    int updateByPrimaryKeySelective(NmplOutlineSorterInfo record);

    int updateByPrimaryKey(NmplOutlineSorterInfo record);
}