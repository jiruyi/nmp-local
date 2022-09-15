package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlinePcInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplOutlinePcInfoMapper {
    long countByExample(NmplOutlinePcInfoExample example);

    int deleteByExample(NmplOutlinePcInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplOutlinePcInfo record);

    int insertSelective(NmplOutlinePcInfo record);

    List<NmplOutlinePcInfo> selectByExample(NmplOutlinePcInfoExample example);

    NmplOutlinePcInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplOutlinePcInfo record, @Param("example") NmplOutlinePcInfoExample example);

    int updateByExample(@Param("record") NmplOutlinePcInfo record, @Param("example") NmplOutlinePcInfoExample example);

    int updateByPrimaryKeySelective(NmplOutlinePcInfo record);

    int updateByPrimaryKey(NmplOutlinePcInfo record);
}