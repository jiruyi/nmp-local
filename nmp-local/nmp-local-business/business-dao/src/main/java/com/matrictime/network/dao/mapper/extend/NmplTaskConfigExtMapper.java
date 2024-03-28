package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.TaskConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplTaskConfigExtMapper {

    int batchUpdate(@Param("list") List<TaskConfigVo> taskConfigVos);
}
