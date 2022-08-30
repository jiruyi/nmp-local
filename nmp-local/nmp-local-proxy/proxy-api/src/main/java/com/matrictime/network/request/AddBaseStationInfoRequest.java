package com.matrictime.network.request;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.modelVo.RouteVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddBaseStationInfoRequest implements Serializable {


    private static final long serialVersionUID = 7275300056652874036L;

    /**
     *  当前基站信息
     */
    private BaseStationInfoVo localBaseInfo;

    /**
     *  所有基站信息列表
     */
    private List<BaseStationInfoVo> stationInfoVos;

    /**
     * 同小区所有设备信息列表
     */
    private List<DeviceInfoVo> deviceInfoVos;

}
