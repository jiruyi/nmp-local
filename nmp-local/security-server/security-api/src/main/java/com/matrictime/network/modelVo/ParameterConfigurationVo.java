package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/10/26.
 */
@Data
public class ParameterConfigurationVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 配置名称 50:加密比例 51:扩展算法 52:加密方式 53:加密算法 54:上行密钥最大值
     * 55:上行密钥预警值 56:上行密钥最小值 57:下行密钥最大值 58:下行密钥预警值 59:下行密钥最小值
     */
    private String configurationCode;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 配置值
     */
    private String configurationValue;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

}
