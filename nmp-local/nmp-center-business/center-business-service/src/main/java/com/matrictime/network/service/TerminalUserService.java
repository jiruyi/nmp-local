package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.response.TerminalUserResponse;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface TerminalUserService {

    Result<Integer> receiveTerminalUser(TerminalUserResponse terminalUserResponse);

}
