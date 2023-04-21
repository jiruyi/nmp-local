package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.request.TerminalDataRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface NmplTerminalDataExtMapper {

    List<NmplTerminalData> distinctTerminalData(TerminalDataRequest terminalDataRequest);
}
