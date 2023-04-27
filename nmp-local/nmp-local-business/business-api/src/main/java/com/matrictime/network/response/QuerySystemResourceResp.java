package com.matrictime.network.response;

import com.matrictime.network.modelVo.SystemResourceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class QuerySystemResourceResp implements Serializable {

    private static final long serialVersionUID = 3124378479703329762L;

    /**
     * 运行系统列表
     */
    private List<SystemResourceVo> resourceVos;

    /**
     * cpu折线数据(key:systemId  value:折线值)
     */
    private Map<String, List<String>> cpuInfos;

    /**
     * 内存折线数据(key:systemId  value:折线值)
     */
    private Map<String, List<String>> memInfos;

}
