package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplLoginDetailExample;
import com.matrictime.network.modelVo.LoginDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplLoginDetailMapper {
    long countByExample(NmplLoginDetailExample example);

    int deleteByExample(NmplLoginDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLoginDetail record);

    int insertSelective(NmplLoginDetail record);

    List<NmplLoginDetail> selectByExample(NmplLoginDetailExample example);

    NmplLoginDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLoginDetail record, @Param("example") NmplLoginDetailExample example);

    int updateByExample(@Param("record") NmplLoginDetail record, @Param("example") NmplLoginDetailExample example);

    int updateByPrimaryKeySelective(NmplLoginDetail record);

    int updateByPrimaryKey(NmplLoginDetail record);

    List<NmplLoginDetail> queryLoginDetailList(LoginDetail loginDetail);
}