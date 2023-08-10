package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.dao.model.NmplTerminalUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplTerminalUserMapper {
    long countByExample(NmplTerminalUserExample example);

    int deleteByExample(NmplTerminalUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplTerminalUser record);

    int insertSelective(NmplTerminalUser record);

    List<NmplTerminalUser> selectByExample(NmplTerminalUserExample example);

    NmplTerminalUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplTerminalUser record, @Param("example") NmplTerminalUserExample example);

    int updateByExample(@Param("record") NmplTerminalUser record, @Param("example") NmplTerminalUserExample example);

    int updateByPrimaryKeySelective(NmplTerminalUser record);

    int updateByPrimaryKey(NmplTerminalUser record);
}