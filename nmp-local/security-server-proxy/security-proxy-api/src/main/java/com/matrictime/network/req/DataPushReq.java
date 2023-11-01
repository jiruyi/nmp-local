package com.matrictime.network.req;

import com.matrictime.network.modelVo.NmpsDataInfoProxyVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DataPushReq implements Serializable {
    String index;

    String key;

    List<NmpsDataInfoProxyVo>dataInfoVoList;
}
