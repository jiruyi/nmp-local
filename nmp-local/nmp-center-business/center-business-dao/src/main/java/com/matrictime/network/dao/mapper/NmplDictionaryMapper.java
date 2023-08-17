package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDictionary;
import com.matrictime.network.dao.model.NmplDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDictionaryMapper {
    long countByExample(NmplDictionaryExample example);

    int deleteByExample(NmplDictionaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDictionary record);

    int insertSelective(NmplDictionary record);

    List<NmplDictionary> selectByExample(NmplDictionaryExample example);

    NmplDictionary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDictionary record, @Param("example") NmplDictionaryExample example);

    int updateByExample(@Param("record") NmplDictionary record, @Param("example") NmplDictionaryExample example);

    int updateByPrimaryKeySelective(NmplDictionary record);

    int updateByPrimaryKey(NmplDictionary record);
}