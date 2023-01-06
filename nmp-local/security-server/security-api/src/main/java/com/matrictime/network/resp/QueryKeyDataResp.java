package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryKeyDataResp implements Serializable {

    /**
     * 时间轴
     */
    private List<String> dateTime;

    /**
     * 列表数值
     */
    private List<Long> dataValue;

    /**
     * 左上角的数值
     */
    private String titleValue;
}
