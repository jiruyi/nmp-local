package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplHeartInfo;
import com.matrictime.network.dao.model.NmplHeartInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplHeartInfoMapper {
    long countByExample(NmplHeartInfoExample example);

    int deleteByExample(NmplHeartInfoExample example);

    int insert(NmplHeartInfo record);

    int insertSelective(NmplHeartInfo record);

    List<NmplHeartInfo> selectByExample(NmplHeartInfoExample example);

    int updateByExampleSelective(@Param("record") NmplHeartInfo record, @Param("example") NmplHeartInfoExample example);

    int updateByExample(@Param("record") NmplHeartInfo record, @Param("example") NmplHeartInfoExample example);
}