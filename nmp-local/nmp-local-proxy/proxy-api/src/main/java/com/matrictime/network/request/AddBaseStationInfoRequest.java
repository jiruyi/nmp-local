package com.matrictime.network.request;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddBaseStationInfoRequest implements Serializable {


    private static final long serialVersionUID = 7275300056652874036L;

    /**
     *
     */
    private BaseStationInfoVo localBaseInfo;

    /**
     * 插入基站信息列表
     */
    private List<BaseStationInfoVo> infoVos;
}
