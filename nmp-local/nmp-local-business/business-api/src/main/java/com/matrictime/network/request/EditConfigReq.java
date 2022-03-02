package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditConfigReq implements Serializable {
    private static final long serialVersionUID = 4132582105947140194L;

    /**
     * 操作类型（1：新增 2：修改 3：删除）-必输
     *
     */
    private String EditType;

    /**
     * 批量编辑对象-修改必输
     */
    private List<NmplConfigVo> nmplConfigVos;

    /**
     * 批量删除id列表（单条删除亦可）-删除必输
     */
    private List<Integer> delIds;
}
