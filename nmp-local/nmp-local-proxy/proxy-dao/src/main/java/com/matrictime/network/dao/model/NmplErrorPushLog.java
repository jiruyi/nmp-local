package com.matrictime.network.dao.model;

import lombok.Data;

/**
 * 代理异常推送日志
 * @author   xxxx
 * @date   2022-11-17
 */
@Data
public class NmplErrorPushLog {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 推送url
     */
    private String url;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 推送信息
     */
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}