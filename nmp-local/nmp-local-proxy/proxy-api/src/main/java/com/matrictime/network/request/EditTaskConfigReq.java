package com.matrictime.network.request;

import com.matrictime.network.modelVo.CenterTaskConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditTaskConfigReq implements Serializable {

    private static final long serialVersionUID = -2206096516394520764L;
    /**
     * 批量编辑对象
     */
    private List<CenterTaskConfigVo> taskConfigVos;
}
