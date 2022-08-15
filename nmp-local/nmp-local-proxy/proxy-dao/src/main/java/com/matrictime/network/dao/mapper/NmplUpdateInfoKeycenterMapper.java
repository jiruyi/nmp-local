package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfoKeycenter;
import com.matrictime.network.dao.model.NmplUpdateInfoKeycenterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoKeycenterMapper {
    long countByExample(NmplUpdateInfoKeycenterExample example);

    int deleteByExample(NmplUpdateInfoKeycenterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfoKeycenter record);

    int insertSelective(NmplUpdateInfoKeycenter record);

    List<NmplUpdateInfoKeycenter> selectByExample(NmplUpdateInfoKeycenterExample example);

    NmplUpdateInfoKeycenter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfoKeycenter record, @Param("example") NmplUpdateInfoKeycenterExample example);

    int updateByExample(@Param("record") NmplUpdateInfoKeycenter record, @Param("example") NmplUpdateInfoKeycenterExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfoKeycenter record);

    int updateByPrimaryKey(NmplUpdateInfoKeycenter record);
}