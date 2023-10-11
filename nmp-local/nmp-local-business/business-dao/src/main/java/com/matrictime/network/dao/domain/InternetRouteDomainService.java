package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.InternetRouteVo;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/29.
 */
public interface InternetRouteDomainService {
    int insert(InternetRouteRequest internetRouteRequest);

    int delete(InternetRouteRequest internetRouteRequest);

    int update(InternetRouteRequest internetRouteRequest);

    PageInfo<InternetRouteVo> select(InternetRouteRequest internetRouteRequest);

    List<DeviceInfoVo> selectDevice(InternetRouteRequest internetRouteRequest);
}
