package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据采集业务上报配置表
 * @author   hexu
 * @date   2023-07-13
 */
@Data
public class NmplReportBusinessVo implements Serializable {


    private static final long serialVersionUID = -7733674600232894353L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 业务配置值
     */
    private String businessValue;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

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
}