package com.matrictime.network.response;

import com.matrictime.network.modelVo.DataCollectVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Data
public class DataCollectResponse {

    private List<DataCollectVo> list;
}
