package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplRole;
import com.matrictime.network.dao.model.NmplRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplRoleMapper {
    long countByExample(NmplRoleExample example);

    int deleteByExample(NmplRoleExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(NmplRole record);

    int insertSelective(NmplRole record);

    List<NmplRole> selectByExample(NmplRoleExample example);

    NmplRole selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") NmplRole record, @Param("example") NmplRoleExample example);

    int updateByExample(@Param("record") NmplRole record, @Param("example") NmplRoleExample example);

    int updateByPrimaryKeySelective(NmplRole record);

    int updateByPrimaryKey(NmplRole record);
}