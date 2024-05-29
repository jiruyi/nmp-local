package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplProxyConfig;
import com.matrictime.network.dao.model.NmplProxyConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplProxyConfigMapper {
    long countByExample(NmplProxyConfigExample example);

    int deleteByExample(NmplProxyConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplProxyConfig record);

    int insertSelective(NmplProxyConfig record);

    List<NmplProxyConfig> selectByExample(NmplProxyConfigExample example);

    NmplProxyConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplProxyConfig record, @Param("example") NmplProxyConfigExample example);

    int updateByExample(@Param("record") NmplProxyConfig record, @Param("example") NmplProxyConfigExample example);

    int updateByPrimaryKeySelective(NmplProxyConfig record);

    int updateByPrimaryKey(NmplProxyConfig record);
}