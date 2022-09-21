package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.DeviceInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceInfoMapper {

    int batchInsert(@Param("list") List<DeviceInfoVo> deviceInfoVos);

    int localBatchInsert(@Param("list") List<DeviceInfoVo> deviceInfoVos);
}