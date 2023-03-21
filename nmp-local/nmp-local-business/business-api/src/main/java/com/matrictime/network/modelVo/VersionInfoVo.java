package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class VersionInfoVo implements Serializable {

    private static final long serialVersionUID = 2275302763147856435L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站
     */
    private String deviceType;


    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 运行版本文件id
     */
    private Long runVersionId;

    /**
     * 运行版本号
     */
    private String runVersionNo;

    /**
     * 运行版本文件名称
     */
    private String runFileName;

    /**
     * 运行状态 1:未运行 2:运行中 3:已停止
     */
    private String runVersionStatus;

    /**
     * 运行版本操作时间
     */
    private Date runVersionOperTime;

    /**
     * 加载版本号
     */
    private String loadVersionNo;

    /**
     * 加载版本文件id
     */
    private Long loadVersionId;

    /**
     * 加载版本操作时间
     */
    private Date loadVersionOperTime;
}
