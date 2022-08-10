package com.matrictime.network.request;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InsertBaseStationInfoRequest implements Serializable {

    /**
     * 插入基站信息列表
     */
    private List<BaseStationInfoVo> infoVos;
}
