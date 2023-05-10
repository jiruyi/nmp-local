package com.matrictime.network.request;

import com.matrictime.network.modelVo.TerminalDataVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/5/9.
 */
@Data
public class TerminalDataListRequest {
    private List<TerminalDataVo> list;
}
