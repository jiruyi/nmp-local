package com.matrictime.network.response;

import com.matrictime.network.modelVo.InternetRouteVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Data
public class InternetRouteResponse extends BaseResponse{

    private List<InternetRouteVo> list;

}
