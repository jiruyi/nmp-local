package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfo;
import com.matrictime.network.dao.model.NmplUpdateInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoMapper {
    long countByExample(NmplUpdateInfoExample example);

    int deleteByExample(NmplUpdateInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfo record);

    int insertSelective(NmplUpdateInfo record);

    List<NmplUpdateInfo> selectByExample(NmplUpdateInfoExample example);

    NmplUpdateInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfo record, @Param("example") NmplUpdateInfoExample example);

    int updateByExample(@Param("record") NmplUpdateInfo record, @Param("example") NmplUpdateInfoExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfo record);

    int updateByPrimaryKey(NmplUpdateInfo record);
}