package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.modelVo.ConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplConfigExtMapper {
    List<NmplConfig> selectByExample(NmplConfig record);

    int batchUpdate(@Param("list") List<ConfigVo> configVos);
}