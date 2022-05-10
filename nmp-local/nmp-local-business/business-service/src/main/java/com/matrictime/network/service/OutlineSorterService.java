package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface OutlineSorterService {
    Result save(OutlineSorterReq outlineSorterReq);

    Result modify(OutlineSorterReq outlineSorterReq);

    Result delete(OutlineSorterReq outlineSorterReq);

    Result<PageInfo>queryByConditon(OutlineSorterReq outlineSorterReq);

    Result upload(MultipartFile file);

    Result auth(OutlineSorterReq outlineSorterReq);

    void download(HttpServletResponse response);
}
