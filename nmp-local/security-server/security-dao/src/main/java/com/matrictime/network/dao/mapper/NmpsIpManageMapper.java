package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsIpManage;
import com.matrictime.network.dao.model.NmpsIpManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsIpManageMapper {
    long countByExample(NmpsIpManageExample example);

    int deleteByExample(NmpsIpManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsIpManage record);

    int insertSelective(NmpsIpManage record);

    List<NmpsIpManage> selectByExample(NmpsIpManageExample example);

    NmpsIpManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsIpManage record, @Param("example") NmpsIpManageExample example);

    int updateByExample(@Param("record") NmpsIpManage record, @Param("example") NmpsIpManageExample example);

    int updateByPrimaryKeySelective(NmpsIpManage record);

    int updateByPrimaryKey(NmpsIpManage record);
}