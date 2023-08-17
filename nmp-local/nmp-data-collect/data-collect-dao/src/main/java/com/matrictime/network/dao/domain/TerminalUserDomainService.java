package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.TerminalUserVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface TerminalUserDomainService {

    List<TerminalUserVo> selectTerminalUser();

}
