package com.matrictime.network.response;

import com.matrictime.network.modelVo.CompanyInfoVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Data
public class CompanyInfoResponse {

    private List<CompanyInfoVo> list;
}
