package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DailyDataResp implements Serializable {

    private static final long serialVersionUID = -6530620787554266502L;
    /**
     * 时间
     */
    private String date;

    private List<DataResp> dataRespList = new ArrayList();
}
