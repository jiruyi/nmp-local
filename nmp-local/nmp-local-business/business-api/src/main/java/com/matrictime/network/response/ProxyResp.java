package com.matrictime.network.response;

import com.matrictime.network.modelVo.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProxyResp implements Serializable {


    private static final long serialVersionUID = -1977732357046758514L;

    private List<ProxyBaseStationInfoVo> baseStationInfoList=new ArrayList<>();

    private List<ProxyDeviceInfoVo> deviceInfoVos=new ArrayList<>();

    private List<ProxyRouteInfoVo> roteVoList = new ArrayList<>();

    private List<LocalLinkVo> localLinkVos = new ArrayList<>();

    private boolean isExist=false;


    private ProxyBaseStationInfoVo localStation;

    private List<ProxyDeviceInfoVo> localDeviceInfoVos=new ArrayList<>();


    private List<NmplOutlinePcInfoVo> nmplOutlinePcInfoVos = new ArrayList<>();

    private List<ProxyBusinessRouteVo> businessRouteVoList = new ArrayList<>();

    private List<ProxyInternetRouteVo> internetRouteVoList = new ArrayList<>();

    private List<ProxyStaticRouteVo> staticRouteVoList = new ArrayList<>();

}
