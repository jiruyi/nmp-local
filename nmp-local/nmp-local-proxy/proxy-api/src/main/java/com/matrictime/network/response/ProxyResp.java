package com.matrictime.network.response;

import com.matrictime.network.modelVo.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProxyResp implements Serializable {

    private static final long serialVersionUID = -312301105936405455L;

    private List<CenterBaseStationInfoVo> baseStationInfoList=new ArrayList<>();

    private List<CenterDeviceInfoVo> deviceInfoVos=new ArrayList<>();

    private List<CenterRouteVo> routeVoList = new ArrayList<>();

    private List<CenterLinkRelationVo> linkRelationVoList = new ArrayList<>();

    // 是否有数据
    private boolean isExist;

    private CenterBaseStationInfoVo localStation;

    private List<CenterDeviceInfoVo> localDeviceInfoVos=new ArrayList<>();

    private List<CenterNmplOutlinePcInfoVo> nmplOutlinePcInfoVos = new ArrayList<>();
}
