package com.matrictime.network.request;

import com.matrictime.network.modelVo.CenterConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditConfigReq implements Serializable {
    private static final long serialVersionUID = 4132582105947140194L;

    /**
     * 批量编辑对象
     */
    private List<CenterConfigVo> configVos;

}
