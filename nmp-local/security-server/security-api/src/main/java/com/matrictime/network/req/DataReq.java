package com.matrictime.network.req;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class DataReq {

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 时间日期
     */
    private List<String> time = new ArrayList();

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 是否默认
     */
    private boolean flag = true;

    private Date startTime;

    private Date endTime;
}
