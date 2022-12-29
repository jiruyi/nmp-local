package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UpdEncryptConfReq implements Serializable {

    private static final long serialVersionUID = -5167468795863517159L;

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 上行加密比例（1：1:1 2：1:8 3：1:16 4：1:32 5：1:64 6：1:128）
     */
    private String upEncryptRatio;

    /**
     * 上行加密扩展算法（1：1:1 2：1:8 3：1:16 4：1:32 5：1:64 6：1:128）
     */
    private String upExtendAlgorithm;

    /**
     * 上行加密方式（1：全加密 2：负载加密 3：不加密）
     */
    private String upEncryptType;

    /**
     * 上行加密算法（1：算法A）
     */
    private String upEncryptAlgorithm;

    /**
     * 上行密钥最大值
     */
    private Long upMaxValue;

    /**
     * 上行密钥预警值
     */
    private Long upWarnValue;

    /**
     * 上行密钥最小值
     */
    private Long upMinValue;

    /**
     * 下行密钥最大值
     */
    private Long downMaxValue;

    /**
     * 下行密钥预警值
     */
    private Long downWarnValue;

    /**
     * 下行密钥最小值
     */
    private Long downMinValue;

    /**
     * 随机数最大值
     */
    private Long randomMaxValue;

    /**
     * 随机数预警值
     */
    private Long randomWarnValue;

    /**
     * 删除标志（1代表存在 0代表删除）
     */
    private Boolean isExist;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}
