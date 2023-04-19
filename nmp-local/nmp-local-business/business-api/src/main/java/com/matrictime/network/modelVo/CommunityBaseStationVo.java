package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/4/18.
 */
@Data
public class CommunityBaseStationVo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String stationId;

    /**
     * 设备名称
     */
    private String stationName;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站
     */
    private String stationType;

    /**
     * 接入网时间
     */
    private Date enterNetworkTime;

    /**
     * 设备管理员
     */
    private String stationAdmain;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 公网Ip
     */
    private String publicNetworkIp;

    /**
     * 公网端口
     */
    private String publicNetworkPort;

    /**
     * 局域网ip
     */
    private String lanIp;

    /**
     * 局域网port
     */
    private String lanPort;

    /**
     * 设备状态 01:静态  02:激活  03:禁用 04:下线
     */
    private String stationStatus;

    /**
     * 设备入网码
     */
    private String stationNetworkId;

    /**
     * 加密随机数
     */
    private String stationRandomSeed;

    /**
     * 关联小区
     */
    private String relationOperatorId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 入网码前缀
     */
    private Long prefixNetworkId;

    /**
     * 入网码后缀
     */
    private Long suffixNetworkId;

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

    /**
     * 加载版本文件名称
     */
    private String loadFileName;

    /**
     * 当前用户数
     */
    private String currentConnectCount;
}
