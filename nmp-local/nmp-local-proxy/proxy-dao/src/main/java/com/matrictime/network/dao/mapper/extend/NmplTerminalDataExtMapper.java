package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.TerminalDataVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/23.
 */
public interface NmplTerminalDataExtMapper {

    List<TerminalDataVo> collectTerminalData();
}
