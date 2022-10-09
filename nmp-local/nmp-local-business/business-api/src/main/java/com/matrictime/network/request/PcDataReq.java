package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PcDataReq extends BaseRequest{
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 基站设备id
     */
    private String stationId;

    /**
     * 一体机设备id
     */
    private String deviceId;

    /**
     * 一体机设备入网码
     */
    private String pcNetworkId;

    /**
     * 设备状态 1:接入  2:未接入
     */
    private Byte status;

    /**
     * 消耗密钥量(单位byte)
     */
    private Integer keyNum;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 批量导入
     */
    List<NmplPcDataVo> nmplPcDataVoList = new ArrayList<>();
}
