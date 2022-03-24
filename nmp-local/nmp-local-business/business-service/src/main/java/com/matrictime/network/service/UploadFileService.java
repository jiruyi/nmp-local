package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.FileIsExistReq;
import com.matrictime.network.request.UploadSingleFileReq;
import com.matrictime.network.response.FileIsExistResp;
import com.matrictime.network.response.UploadSingleFileResp;

public interface UploadFileService {

    Result<UploadSingleFileResp> uploadSingleFile(UploadSingleFileReq req);

    Result<FileIsExistResp> fileIsExist(FileIsExistReq req);
}
