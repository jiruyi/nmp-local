package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplKeycenterHeartInfo;
import com.matrictime.network.dao.model.NmplKeycenterHeartInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplKeycenterHeartInfoMapper {
    long countByExample(NmplKeycenterHeartInfoExample example);

    int deleteByExample(NmplKeycenterHeartInfoExample example);

    int insert(NmplKeycenterHeartInfo record);

    int insertSelective(NmplKeycenterHeartInfo record);

    List<NmplKeycenterHeartInfo> selectByExample(NmplKeycenterHeartInfoExample example);

    int updateByExampleSelective(@Param("record") NmplKeycenterHeartInfo record, @Param("example") NmplKeycenterHeartInfoExample example);

    int updateByExample(@Param("record") NmplKeycenterHeartInfo record, @Param("example") NmplKeycenterHeartInfoExample example);
}