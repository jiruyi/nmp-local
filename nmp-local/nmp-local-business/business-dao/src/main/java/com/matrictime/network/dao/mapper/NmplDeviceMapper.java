package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDevice;
import com.matrictime.network.dao.model.NmplDeviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceMapper {
    long countByExample(NmplDeviceExample example);

    int deleteByExample(NmplDeviceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDevice record);

    int insertSelective(NmplDevice record);

    List<NmplDevice> selectByExampleWithBLOBs(NmplDeviceExample example);

    List<NmplDevice> selectByExample(NmplDeviceExample example);

    NmplDevice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDevice record, @Param("example") NmplDeviceExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplDevice record, @Param("example") NmplDeviceExample example);

    int updateByExample(@Param("record") NmplDevice record, @Param("example") NmplDeviceExample example);

    int updateByPrimaryKeySelective(NmplDevice record);

    int updateByPrimaryKeyWithBLOBs(NmplDevice record);

    int updateByPrimaryKey(NmplDevice record);
}