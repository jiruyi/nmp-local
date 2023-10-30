package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.ParameterConfigurationVo;
import com.matrictime.network.request.ConfigurationValueRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
public interface ConfigurationValueExtMapper {

    List<ParameterConfigurationVo> selectConfiguration(ConfigurationValueRequest configurationValueRequest);

}
