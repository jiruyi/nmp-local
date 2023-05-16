package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.request.TerminalUserResquest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/5/16.
 */
public interface NmplTerminalUserExtMapper {

    List<TerminalUserVo> selectTerminalUser(TerminalUserResquest terminalUserResquest);
}
