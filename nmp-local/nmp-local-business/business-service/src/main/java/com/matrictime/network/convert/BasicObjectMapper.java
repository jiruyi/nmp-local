package com.matrictime.network.convert;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author jiruyi@jzsg.com.cn
 * @project microservicecloud-jzsg
 * @date 2021/8/9 9:17
 * @desc
 */
public interface BasicObjectMapper<SOURCE, TARGET>  {
    @Mappings({})
    @InheritConfiguration
    TARGET to(SOURCE var1);

    @InheritConfiguration
    List<TARGET> to(List<SOURCE> var1);

    @Mappings({})
    @InheritInverseConfiguration
    SOURCE back(TARGET var1);

    @InheritInverseConfiguration
    List<SOURCE> back(List<TARGET> var1);
}
