package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CenterConfigVo implements Serializable {

    private static final long serialVersionUID = -2288406664669678585L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String deviceType;

    /**
     * 配置项名称
     */
    private String configName;

    /**
     * 配置编码
     */
    private String configCode;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 状态 1同步 0 未同步
     */
    private Byte status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 状态 true:存在(1)  false:删除(0)
     */
    private Boolean isExist;

    /**
     * 备注
     */
    private String remark;
}
