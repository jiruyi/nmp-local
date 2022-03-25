package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResp extends BaseResponse{
    List<NmplCompanyInfoVo> nmplCompanyInfoVoList;
}
