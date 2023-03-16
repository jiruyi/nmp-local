package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.NmplVersionFileVo;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.VersionFileResponse;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/3/15.
 */
public interface FileVersionDomainService {

    int insertFileVersion(UploadVersionFileReq uploadVersionFileReq);

    int updateFileVersion(UploadVersionFileReq uploadVersionFileReq);

    int deleteFileVersion(UploadVersionFileReq uploadVersionFileReq);

    VersionFileResponse selectVersionFile(UploadVersionFileReq uploadVersionFileReq);
}
