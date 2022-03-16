package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplBill;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.request.BillRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplBillExtMapper {

    List<NmplBillVo> queryByCondition(BillRequest billRequest);

    Integer batchInsert(@Param("list") List<NmplBillVo> nmplBillVoList);
}
