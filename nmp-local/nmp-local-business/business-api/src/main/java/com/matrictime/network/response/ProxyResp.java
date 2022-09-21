package com.matrictime.network.response;

import com.matrictime.network.modelVo.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProxyResp implements Serializable {
    private static final long serialVersionUID = -1977732357046758514L;

    private List<BaseStationInfoVo> baseStationInfoList=new ArrayList<>();

    private List<DeviceInfoVo> deviceInfoVos=new ArrayList<>();

    private List<RouteVo> roteVoList = new ArrayList<>();

    private List<LinkRelationVo> linkRelationVoList = new ArrayList<>();

    private boolean isExist=false;


    private BaseStationInfoVo localStation;

    private List<DeviceInfoVo> localDeviceInfoVos=new ArrayList<>();


    private List<NmplOutlinePcInfoVo> nmplOutlinePcInfoVos = new ArrayList<>();
}
