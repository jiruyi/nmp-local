package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLink;
import com.matrictime.network.dao.model.NmplLinkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLinkMapper {
    long countByExample(NmplLinkExample example);

    int deleteByExample(NmplLinkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLink record);

    int insertSelective(NmplLink record);

    List<NmplLink> selectByExample(NmplLinkExample example);

    NmplLink selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLink record, @Param("example") NmplLinkExample example);

    int updateByExample(@Param("record") NmplLink record, @Param("example") NmplLinkExample example);

    int updateByPrimaryKeySelective(NmplLink record);

    int updateByPrimaryKey(NmplLink record);
}