package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PortalSystemVo implements Serializable {

    private static final long serialVersionUID = 6098797869760562698L;

    /**
     * 系统ID
     */
    private Long id;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统类型（1：运营系统 2：运维系统 3:运控系统）
     */
    private String sysType;

    /**
     * 图片地址
     */
    private String sysLogo;

    /**
     * 链接地址
     */
    private String sysUrl;

    /**
     * 是否前端展示标志（1：展示 0：隐藏）
     */
    private Boolean isDisplay;

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
