package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jiruyi@jzsg.com.cn
 * @project microservicecloud-jzsg
 * @date 2021/8/6 15:23
 * @desc
 */
@Data
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = -7723032570086442855L;

    private Integer count;

    private Integer pages;
}
