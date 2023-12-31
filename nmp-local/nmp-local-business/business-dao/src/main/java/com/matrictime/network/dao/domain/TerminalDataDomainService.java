package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.TerminalDataVo;
import com.matrictime.network.request.TerminalDataListRequest;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.TerminalDataResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface TerminalDataDomainService {

    PageInfo<TerminalDataVo> selectTerminalData(TerminalDataRequest terminalDataRequest);

    int collectTerminalData(TerminalDataVo terminalDataVo);
}
