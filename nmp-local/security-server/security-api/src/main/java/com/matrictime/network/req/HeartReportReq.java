package com.matrictime.network.req;

import com.matrictime.network.modelVo.HeartInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class HeartReportReq {

    private List<HeartInfoVo> heartInfoVos;

}
