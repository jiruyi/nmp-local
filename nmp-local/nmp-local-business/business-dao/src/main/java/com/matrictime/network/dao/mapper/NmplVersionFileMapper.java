package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplVersionFile;
import com.matrictime.network.dao.model.NmplVersionFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplVersionFileMapper {
    long countByExample(NmplVersionFileExample example);

    int deleteByExample(NmplVersionFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplVersionFile record);

    int insertSelective(NmplVersionFile record);

    List<NmplVersionFile> selectByExample(NmplVersionFileExample example);

    NmplVersionFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplVersionFile record, @Param("example") NmplVersionFileExample example);

    int updateByExample(@Param("record") NmplVersionFile record, @Param("example") NmplVersionFileExample example);

    int updateByPrimaryKeySelective(NmplVersionFile record);

    int updateByPrimaryKey(NmplVersionFile record);
}