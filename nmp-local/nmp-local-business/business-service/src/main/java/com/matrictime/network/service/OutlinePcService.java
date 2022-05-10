package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface OutlinePcService {
    Result save(OutlinePcReq outlinePcReq);

    Result modify(OutlinePcReq outlinePcReq);

    Result delete(OutlinePcReq outlinePcReq);

    Result<PageInfo>queryByConditon(OutlinePcReq outlinePcReq);

    Result upload( MultipartFile file);
}
