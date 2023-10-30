package com.matrictime.network.resp;

import com.matrictime.network.modelVo.ParameterConfigurationVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/26.
 */
@Data
public class ParameterConfigurationResp {

    private List<ParameterConfigurationVo> list;
}
