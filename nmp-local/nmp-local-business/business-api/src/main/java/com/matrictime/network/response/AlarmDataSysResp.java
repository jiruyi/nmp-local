package com.matrictime.network.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/24 0024 17:29
 * @desc
 */
@Builder
@Data
public class AlarmDataSysResp extends BaseResponse implements Serializable {
    private static final long serialVersionUID = -8101225910330056880L;
    //接入基站
    private AlarmSysLevelCount accessCountResp;
    //边界基站
    private AlarmSysLevelCount boundaryCountResp;
    //密钥中心基站
    private AlarmSysLevelCount keyCenterCountResp;

}
