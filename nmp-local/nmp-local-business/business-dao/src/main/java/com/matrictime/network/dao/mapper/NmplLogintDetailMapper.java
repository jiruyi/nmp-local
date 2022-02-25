package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLogintDetail;
import com.matrictime.network.dao.model.NmplLogintDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLogintDetailMapper {
    long countByExample(NmplLogintDetailExample example);

    int deleteByExample(NmplLogintDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLogintDetail record);

    int insertSelective(NmplLogintDetail record);

    List<NmplLogintDetail> selectByExample(NmplLogintDetailExample example);

    NmplLogintDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLogintDetail record, @Param("example") NmplLogintDetailExample example);

    int updateByExample(@Param("record") NmplLogintDetail record, @Param("example") NmplLogintDetailExample example);

    int updateByPrimaryKeySelective(NmplLogintDetail record);

    int updateByPrimaryKey(NmplLogintDetail record);
}