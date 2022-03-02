package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplConfig;

import java.util.List;

public interface NmplConfigExtMapper {
    List<NmplConfig> selectByExample(NmplConfig record);
}