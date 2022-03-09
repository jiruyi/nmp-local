package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplDeviceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SignalIoReq implements Serializable {

    private static final long serialVersionUID = -1865795745370035576L;

    /**
     * 1:开启追踪 0：关闭追踪
     */
    private String ioType;

    /**
     * 设备id列表
     */
    private List<NmplDeviceVo> deviceVos;

    /**
     * 用户id
     */
    private String userId;

}
