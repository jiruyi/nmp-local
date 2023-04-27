package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/18.
 */
@Data
public class BaseStationBelongVo implements Serializable {

    private String belongSpace;

    private String belongName;

    private List<CommunityBaseStationVo> baseStationInfoVoList;

}
