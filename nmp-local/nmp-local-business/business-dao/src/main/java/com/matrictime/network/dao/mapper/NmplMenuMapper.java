package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplMenu;
import com.matrictime.network.dao.model.NmplMenuExample;
import java.util.List;

import com.matrictime.network.modelVo.NmplMenuVo;
import org.apache.ibatis.annotations.Param;

public interface NmplMenuMapper {
    long countByExample(NmplMenuExample example);

    int deleteByExample(NmplMenuExample example);

    int deleteByPrimaryKey(Long menuId);

    int insert(NmplMenu record);

    int insertSelective(NmplMenu record);

    List<NmplMenu> selectByExample(NmplMenuExample example);

    NmplMenu selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") NmplMenu record, @Param("example") NmplMenuExample example);

    int updateByExample(@Param("record") NmplMenu record, @Param("example") NmplMenuExample example);

    int updateByPrimaryKeySelective(NmplMenu record);

    int updateByPrimaryKey(NmplMenu record);

    List<String> queryAllPerms(Long roleId);

    List<NmplMenuVo> queryAllMenu();

    List<Long> queryAllPermId(Long roleId);
}