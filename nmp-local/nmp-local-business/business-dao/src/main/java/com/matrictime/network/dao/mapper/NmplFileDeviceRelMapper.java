package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplFileDeviceRel;
import com.matrictime.network.dao.model.NmplFileDeviceRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplFileDeviceRelMapper {
    long countByExample(NmplFileDeviceRelExample example);

    int deleteByExample(NmplFileDeviceRelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplFileDeviceRel record);

    int insertSelective(NmplFileDeviceRel record);

    List<NmplFileDeviceRel> selectByExample(NmplFileDeviceRelExample example);

    NmplFileDeviceRel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplFileDeviceRel record, @Param("example") NmplFileDeviceRelExample example);

    int updateByExample(@Param("record") NmplFileDeviceRel record, @Param("example") NmplFileDeviceRelExample example);

    int updateByPrimaryKeySelective(NmplFileDeviceRel record);

    int updateByPrimaryKey(NmplFileDeviceRel record);
}