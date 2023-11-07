package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsDnsManageMapper {
    long countByExample(NmpsDnsManageExample example);

    int deleteByExample(NmpsDnsManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsDnsManage record);

    int insertSelective(NmpsDnsManage record);

    List<NmpsDnsManage> selectByExample(NmpsDnsManageExample example);

    NmpsDnsManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsDnsManage record, @Param("example") NmpsDnsManageExample example);

    int updateByExample(@Param("record") NmpsDnsManage record, @Param("example") NmpsDnsManageExample example);

    int updateByPrimaryKeySelective(NmpsDnsManage record);

    int updateByPrimaryKey(NmpsDnsManage record);
}