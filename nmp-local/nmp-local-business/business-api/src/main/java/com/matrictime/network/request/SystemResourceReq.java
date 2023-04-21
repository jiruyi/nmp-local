package com.matrictime.network.request;

import com.matrictime.network.modelVo.SystemResourceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SystemResourceReq implements Serializable {

    private static final long serialVersionUID = 4176816596071224226L;

    private List<SystemResourceVo> srList;
}
