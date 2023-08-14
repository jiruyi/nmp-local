package com.matrictime.network.response;

import com.matrictime.network.modelVo.StationSummaryVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */
@Data
public class StationSummaryResponse {

    private List<StationSummaryVo> list;

}
