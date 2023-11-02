package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsNetworkCard;
import com.matrictime.network.dao.model.NmpsNetworkCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsNetworkCardMapper {
    long countByExample(NmpsNetworkCardExample example);

    int deleteByExample(NmpsNetworkCardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsNetworkCard record);

    int insertSelective(NmpsNetworkCard record);

    List<NmpsNetworkCard> selectByExample(NmpsNetworkCardExample example);

    NmpsNetworkCard selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsNetworkCard record, @Param("example") NmpsNetworkCardExample example);

    int updateByExample(@Param("record") NmpsNetworkCard record, @Param("example") NmpsNetworkCardExample example);

    int updateByPrimaryKeySelective(NmpsNetworkCard record);

    int updateByPrimaryKey(NmpsNetworkCard record);
}