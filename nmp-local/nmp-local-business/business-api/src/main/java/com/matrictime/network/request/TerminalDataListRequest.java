package com.matrictime.network.request;

import com.matrictime.network.modelVo.TerminalDataVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/23.
 */
@Data
public class TerminalDataListRequest implements Serializable {
    private List<TerminalDataVo> list;

}
