package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.request.InternetRouteRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/9.
 */
public interface NmplInternetRouteExtMapper {

    List<DeviceInfoVo> selectDevice(InternetRouteRequest internetRouteRequest);
}
