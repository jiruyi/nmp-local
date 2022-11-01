package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Authth ruyi.ji
 * @Desc:  输入基类
 * @Date: 2020-10-22 11:10
 * @VERSION：
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -815218864983283742L;

    /**
     * 分页页数（不传默认第一页）
     */
    private int pageNo = 1;

    /**
     * 分页展示条数（不传默认10条）
     */
    private int pageSize = 10;
}
