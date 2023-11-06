package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsCaManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsCaManageMapper {
    long countByExample(NmpsCaManageExample example);

    int deleteByExample(NmpsCaManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsCaManage record);

    int insertSelective(NmpsCaManage record);

    List<NmpsCaManage> selectByExample(NmpsCaManageExample example);

    NmpsCaManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsCaManage record, @Param("example") NmpsCaManageExample example);

    int updateByExample(@Param("record") NmpsCaManage record, @Param("example") NmpsCaManageExample example);

    int updateByPrimaryKeySelective(NmpsCaManage record);

    int updateByPrimaryKey(NmpsCaManage record);
}