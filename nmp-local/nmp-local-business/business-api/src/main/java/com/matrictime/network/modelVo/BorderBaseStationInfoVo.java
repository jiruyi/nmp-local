package com.matrictime.network.modelVo;

import com.matrictime.network.model.PublicNetworkIp;
import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/9/1.
 */
@Data
public class BorderBaseStationInfoVo {


    private String id;

    private String stationId;

    private String stationName;

    private String stationType;

    private Date enterNetworkTime;

    private String stationAdmain;

    private String remark;

    private PublicNetworkIp publicNetworkIp;

    private String publicNetworkPort;

    private String lanIp;

    private String lanPort;

    private String stationStatus;

    private String stationNetworkId;

    private String stationRandomSeed;

    private String relationOperatorId;

    private String companyName;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;

    private String isExist;

    /**
     * 设备入网码
     */
    private byte[] byteNetworkId;

    /**
     * 入网码前缀
     */
    private Long prefixNetworkId;

    /**
     * 入网码后缀
     */
    private Long suffixNetworkId;
}
