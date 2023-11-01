package com.matrictime.network.req;

import com.matrictime.network.modelVo.DataInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class DataInfoReq implements Serializable {

    private static final long serialVersionUID = -3073304780600818996L;

    private List<DataInfoVo> dataInfoVoList = new ArrayList();
    /**
     * 最大索引
     */
    private Integer index;
    /**
     * redis key
     */
    private String key;
}
