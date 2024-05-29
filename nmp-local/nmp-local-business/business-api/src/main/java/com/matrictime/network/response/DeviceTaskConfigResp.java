package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplDeviceTaskConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class DeviceTaskConfigResp implements Serializable {

    private static final long serialVersionUID = 5275412428026517594L;

    List<NmplDeviceTaskConfigVo> nmplDeviceTaskConfigVoList = new ArrayList<>();

}
