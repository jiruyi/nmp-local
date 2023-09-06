package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.LinkVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapperExt {
    int batchInsert(@Param("list") List<LinkVo> linkVos);

    int batchUpdate(@Param("list") List<LinkVo> linkVos);
}