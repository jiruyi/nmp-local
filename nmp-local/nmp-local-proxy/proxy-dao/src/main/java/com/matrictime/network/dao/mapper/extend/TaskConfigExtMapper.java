package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.CenterTaskConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskConfigExtMapper {

    int batchInsert(@Param("list") List<CenterTaskConfigVo> centerTaskConfigVos);

    int batchUpdate(@Param("list") List<CenterTaskConfigVo> centerTaskConfigVos);
}