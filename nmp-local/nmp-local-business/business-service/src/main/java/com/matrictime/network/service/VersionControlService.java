package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.VersionReq;
import com.matrictime.network.response.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;

public interface VersionControlService {

    Result loadVersionFile(VersionReq req);

    Result<PageInfo> queryLoadVersion(VersionReq req);

    Result runLoadVersionFile( VersionReq req);

    Result runVersion(VersionReq req);

    Result<PageInfo> queryRunVersion( VersionReq req);

    Result stopRunVersion(VersionReq req);

    Result uninstallRunVersion(VersionReq req);

}
