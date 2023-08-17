package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplDictionaryExtMapper {
    Integer batchInsert(@Param("list") List<NmplDictionary> nmplDictionaries);
}
