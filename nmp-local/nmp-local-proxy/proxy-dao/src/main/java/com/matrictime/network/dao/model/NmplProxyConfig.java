package com.matrictime.network.dao.model;

import lombok.Data;

/**
 * 
 * @author   xxxx
 * @date   2024-01-11
 */
@Data
public class NmplProxyConfig {
    /**
     * 主键
     */
    private Long id;

    /**
     * 配置编码
     */
    private String configCode;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置描述
     */
    private String configDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode == null ? null : configCode.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc == null ? null : configDesc.trim();
    }
}