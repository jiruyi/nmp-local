package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsServerHeartInfo;
import com.matrictime.network.dao.model.NmpsServerHeartInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsServerHeartInfoMapper {
    long countByExample(NmpsServerHeartInfoExample example);

    int deleteByExample(NmpsServerHeartInfoExample example);

    int insert(NmpsServerHeartInfo record);

    int insertSelective(NmpsServerHeartInfo record);

    List<NmpsServerHeartInfo> selectByExample(NmpsServerHeartInfoExample example);

    int updateByExampleSelective(@Param("record") NmpsServerHeartInfo record, @Param("example") NmpsServerHeartInfoExample example);

    int updateByExample(@Param("record") NmpsServerHeartInfo record, @Param("example") NmpsServerHeartInfoExample example);
}