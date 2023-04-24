package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.TerminalDataVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/23.
 */
public interface TerminalDataDomainService {

    List<TerminalDataVo> collectTerminalData();
}
