package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.response.UploadImgResp;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Result<UploadImgResp> uploadImg(MultipartFile file, String imagePath, String imageDir);
}
