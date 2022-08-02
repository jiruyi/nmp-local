package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplVersionVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class QueryVersionResp implements Serializable {
    private static final long serialVersionUID = 6102697665521323356L;

    /**
     * 版本选项
     */
    Map<String,List<NmplVersionVo>> versionMap;
}
