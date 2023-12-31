package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalUserResquest;
import com.matrictime.network.response.TerminalUserCountResponse;
import com.matrictime.network.response.TerminalUserResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */

public interface TerminalUserService {
    Result<Integer> updateTerminalUser(TerminalUserResponse terminalUserResponse);

    Result<TerminalUserCountResponse> countTerminalUser(TerminalUserResquest terminalUserResquest);
}
