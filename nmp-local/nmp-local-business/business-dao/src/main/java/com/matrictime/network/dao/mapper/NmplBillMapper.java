package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplBill;
import com.matrictime.network.dao.model.NmplBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplBillMapper {
    long countByExample(NmplBillExample example);

    int deleteByExample(NmplBillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplBill record);

    int insertSelective(NmplBill record);

    List<NmplBill> selectByExample(NmplBillExample example);

    NmplBill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplBill record, @Param("example") NmplBillExample example);

    int updateByExample(@Param("record") NmplBill record, @Param("example") NmplBillExample example);

    int updateByPrimaryKeySelective(NmplBill record);

    int updateByPrimaryKey(NmplBill record);
}