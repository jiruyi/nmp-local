package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplSignalVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddSignalReq implements Serializable {

    private static final long serialVersionUID = 5773604491924285572L;

    private String deviceId;

    /**
     * 上报信令列表
     */
    private List<NmplSignalVo> nmplSignalVos;
}
