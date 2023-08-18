package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.modelVo.TerminalUserVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface TerminalUserDomainService {

    int insertTerminalUser(TerminalUserVo terminalUserVo);

    int updateTerminalUser(TerminalUserVo terminalUserVo);

    List<NmplTerminalUser> selectTerminalUser(TerminalUserVo terminalUserVo);

}
