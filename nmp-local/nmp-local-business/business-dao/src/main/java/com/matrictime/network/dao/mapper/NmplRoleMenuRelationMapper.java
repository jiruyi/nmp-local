package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplRoleMenuRelation;
import com.matrictime.network.dao.model.NmplRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplRoleMenuRelationMapper {
    long countByExample(NmplRoleMenuRelationExample example);

    int deleteByExample(NmplRoleMenuRelationExample example);

    int insert(NmplRoleMenuRelation record);

    int insertSelective(NmplRoleMenuRelation record);

    List<NmplRoleMenuRelation> selectByExample(NmplRoleMenuRelationExample example);

    int updateByExampleSelective(@Param("record") NmplRoleMenuRelation record, @Param("example") NmplRoleMenuRelationExample example);

    int updateByExample(@Param("record") NmplRoleMenuRelation record, @Param("example") NmplRoleMenuRelationExample example);
}