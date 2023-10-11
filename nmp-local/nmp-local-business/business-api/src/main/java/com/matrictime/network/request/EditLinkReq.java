package com.matrictime.network.request;

import com.matrictime.network.modelVo.LocalLinkVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditLinkReq implements Serializable {

    private static final long serialVersionUID = 886416625159131982L;

    /**
     * 操作类型（1：新增 2：修改 3：删除）-必输
     *
     */
    private String editType;

    /**
     * 批量编辑对象-修改必输
     */
    private List<LocalLinkVo> localLinkVos;

    /**
     * 批量删除id列表（单条删除亦可）-删除必输
     * 备注：物理删除，逻辑删除请使用修改方式修改isExist字段为0
     */
    private List<Long> delIds;
}
