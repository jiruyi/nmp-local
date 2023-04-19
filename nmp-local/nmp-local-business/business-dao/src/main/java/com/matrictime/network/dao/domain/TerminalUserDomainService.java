package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.request.TerminalUserResquest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface TerminalUserDomainService {
    int insertTerminalUser(TerminalUserResquest terminalUserResquest);

    int updateTerminalUser(TerminalUserResquest terminalUserResquest);

    List<TerminalUserVo> selectTerminalUser(TerminalUserResquest terminalUserResquest);

    int countTerminalUser(TerminalUserResquest terminalUserResquest);
}
