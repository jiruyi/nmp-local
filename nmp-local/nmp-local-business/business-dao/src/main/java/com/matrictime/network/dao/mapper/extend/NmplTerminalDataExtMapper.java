package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.TerminalDataVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.TerminalDataReq;

import java.util.List;

public interface NmplTerminalDataExtMapper {


    List<TerminalDataVo> selectCurrentIpFlow(TerminalDataReq req);

}
