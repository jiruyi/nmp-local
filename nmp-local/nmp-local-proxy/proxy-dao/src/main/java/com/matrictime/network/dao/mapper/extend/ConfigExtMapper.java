package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.CenterConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfigExtMapper {
    int batchInsert(@Param("list") List<CenterConfigVo> centerConfigVos);

    int batchUpdate(@Param("list") List<CenterConfigVo> centerConfigVos);
}