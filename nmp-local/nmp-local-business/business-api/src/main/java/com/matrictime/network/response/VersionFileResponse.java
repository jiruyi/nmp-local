package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplVersionFileVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/3/16.
 */
@Data
public class VersionFileResponse implements Serializable {
    private List<NmplVersionFileVo> list;
}
