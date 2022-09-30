package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BusinessRouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.BusinessRouteResponse;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
public interface BusinessRouteDomainService {

    int insert(BusinessRouteRequest businessRouteRequest);

    int delete(BusinessRouteRequest businessRouteRequest);

    int update(BusinessRouteRequest businessRouteRequest);

    PageInfo<BusinessRouteVo> select(BusinessRouteRequest businessRouteRequest);

    BaseStationInfoResponse selectBaseStation(BaseStationInfoRequest baseStationInfoRequest);


}
