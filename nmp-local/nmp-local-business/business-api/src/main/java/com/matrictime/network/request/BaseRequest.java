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

    private static final long serialVersionUID = -2664440672733655871L;

    private int pageNo = 1;

    private int pageSize = 10;
}
