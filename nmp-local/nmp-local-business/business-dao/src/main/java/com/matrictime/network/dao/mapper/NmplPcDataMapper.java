package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplPcData;
import com.matrictime.network.dao.model.NmplPcDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplPcDataMapper {
    long countByExample(NmplPcDataExample example);

    int deleteByExample(NmplPcDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplPcData record);

    int insertSelective(NmplPcData record);

    List<NmplPcData> selectByExample(NmplPcDataExample example);

    NmplPcData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplPcData record, @Param("example") NmplPcDataExample example);

    int updateByExample(@Param("record") NmplPcData record, @Param("example") NmplPcDataExample example);

    int updateByPrimaryKeySelective(NmplPcData record);

    int updateByPrimaryKey(NmplPcData record);
}