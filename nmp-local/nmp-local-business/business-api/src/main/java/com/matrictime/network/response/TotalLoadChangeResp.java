package com.matrictime.network.response;

import com.matrictime.network.modelVo.TotalLoadVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class TotalLoadChangeResp implements Serializable {
    private static final long serialVersionUID = 6950055129995658188L;

    /**
     * 数据列表
     */
    private Map<String,List<TotalLoadVo>> dataMap;
}
