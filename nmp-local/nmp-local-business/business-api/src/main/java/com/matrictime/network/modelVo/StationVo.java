package com.matrictime.network.modelVo;


import lombok.Data;

import java.io.Serializable;

@Data
public class StationVo implements Serializable {

    private String deviceId;

    private String stationNetworkId;

    private String  stationType;

    private String dataItemValue;

}
