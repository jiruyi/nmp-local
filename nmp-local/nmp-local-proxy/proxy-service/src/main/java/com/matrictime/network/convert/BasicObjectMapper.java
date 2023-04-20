package com.matrictime.network.convert;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.List;

/**
  * @title 
  * @param 
  * @return 
  * @description 
  * @author jiruyi
  * @create 2023/4/19 0019 17:17
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
