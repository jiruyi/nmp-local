package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpOperateServerInfo;
import com.matrictime.network.dao.model.NmpOperateServerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpOperateServerInfoMapper {
    long countByExample(NmpOperateServerInfoExample example);

    int deleteByExample(NmpOperateServerInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpOperateServerInfo record);

    int insertSelective(NmpOperateServerInfo record);

    List<NmpOperateServerInfo> selectByExample(NmpOperateServerInfoExample example);

    NmpOperateServerInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpOperateServerInfo record, @Param("example") NmpOperateServerInfoExample example);

    int updateByExample(@Param("record") NmpOperateServerInfo record, @Param("example") NmpOperateServerInfoExample example);

    int updateByPrimaryKeySelective(NmpOperateServerInfo record);

    int updateByPrimaryKey(NmpOperateServerInfo record);
}