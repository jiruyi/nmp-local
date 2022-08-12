package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfoCache;
import com.matrictime.network.dao.model.NmplUpdateInfoCacheExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoCacheMapper {
    long countByExample(NmplUpdateInfoCacheExample example);

    int deleteByExample(NmplUpdateInfoCacheExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfoCache record);

    int insertSelective(NmplUpdateInfoCache record);

    List<NmplUpdateInfoCache> selectByExample(NmplUpdateInfoCacheExample example);

    NmplUpdateInfoCache selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfoCache record, @Param("example") NmplUpdateInfoCacheExample example);

    int updateByExample(@Param("record") NmplUpdateInfoCache record, @Param("example") NmplUpdateInfoCacheExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfoCache record);

    int updateByPrimaryKey(NmplUpdateInfoCache record);
}