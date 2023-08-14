package com.matrictime.network.response;

import com.matrictime.network.modelVo.StationConnectCountVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/9.
 */
@Data
public class StationConnectCountResponse {

    private List<StationConnectCountVo> list;
 }
