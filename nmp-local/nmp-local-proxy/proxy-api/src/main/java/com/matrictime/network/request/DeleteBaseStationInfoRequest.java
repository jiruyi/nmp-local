package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeleteBaseStationInfoRequest implements Serializable {

    /**
     * id列表
     */
    private List<Long> ids;
}
