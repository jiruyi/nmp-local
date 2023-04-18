package com.matrictime.network.request;

import com.matrictime.network.modelVo.DataCollectVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DataCollectReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -7806871783203989939L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名字
     */
    private String deviceName;

    /**
     * 设备ip
     */
    private String deviceIp;

    /**
     * 设备类别(01接入基站、02边界基站、11密钥中心、12生成机、13缓存机)
     */
    private String deviceType;

    /**
     * 统计项名
     */
    private String dataItemName;

    /**
     * 收集项编号
     */
    private String dataItemCode;

    /**
     * 值
     */
    private String dataItemValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 创建时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 设备状态
     */
    private String stationStatus;

    private List<DataCollectVo> dataCollectVoList;

    private List<DataCollectVo> dataCollectVoLoadList;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
