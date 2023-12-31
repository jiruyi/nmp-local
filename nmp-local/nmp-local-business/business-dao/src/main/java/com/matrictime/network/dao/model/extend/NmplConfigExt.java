package com.matrictime.network.dao.model.extend;

import lombok.Data;

import java.util.Date;

@Data
public class NmplConfigExt {
    /**
     * 主键
     */
    private Long id;

    /**
     * 1:基站 2 分发机 3 生成机 4 缓存机
     */
    private String deviceType;


    private String deviceTypeName;

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
     * 默认值
     */
    private String defaultValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 状态 1同步 0 未同步
     */
    private Byte status;

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

    /**
     * 备注
     */
    private String remark;
}
