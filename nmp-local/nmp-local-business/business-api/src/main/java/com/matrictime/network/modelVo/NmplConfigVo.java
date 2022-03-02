package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NmplConfigVo implements Serializable {
    private static final long serialVersionUID = 7952780693158402766L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 1:基站 2 分发机 3 生成机 4 缓存机
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
     * 默认值
     */
    private String defaultValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 状态 1正常 0 停用
     */
    private Boolean status;

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
     * 状态 1:存在  0:删除
     */
    private Boolean isExist;

    /**
     * 备注
     */
    private String remark;
}
