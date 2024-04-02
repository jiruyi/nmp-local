package com.matrictime.network.request;

import com.matrictime.network.modelVo.TaskConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncTaskReq implements Serializable {

    private static final long serialVersionUID = 4659766867792523739L;

    /**
     * 批量编辑对象
     */
    private List<TaskConfigVo> taskConfigVos;
}
