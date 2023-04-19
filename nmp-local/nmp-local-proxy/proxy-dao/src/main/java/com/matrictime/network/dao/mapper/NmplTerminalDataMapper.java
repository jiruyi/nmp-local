package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.dao.model.NmplTerminalDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplTerminalDataMapper {
    long countByExample(NmplTerminalDataExample example);

    int deleteByExample(NmplTerminalDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplTerminalData record);

    int insertSelective(NmplTerminalData record);

    List<NmplTerminalData> selectByExample(NmplTerminalDataExample example);

    NmplTerminalData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplTerminalData record, @Param("example") NmplTerminalDataExample example);

    int updateByExample(@Param("record") NmplTerminalData record, @Param("example") NmplTerminalDataExample example);

    int updateByPrimaryKeySelective(NmplTerminalData record);

    int updateByPrimaryKey(NmplTerminalData record);
}