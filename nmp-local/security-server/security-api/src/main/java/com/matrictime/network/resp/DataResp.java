package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;
@Data
public class DataResp implements Serializable {

    private static final long serialVersionUID = -8928876105652279205L;
    /**
     * 时刻
     */
    private String time;
    /**
     * 值
     */
    private String value;
}
