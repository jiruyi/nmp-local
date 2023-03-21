package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;

public interface VersionService {

    Result load(VersionLoadReq request);

    Result<Integer> start(VersionStartReq request);

    Result<Integer> run(VersionRunReq request);

    Result<Integer> stop(VersionStopReq request);

    Result<Integer> uninstall(VersionUninstallReq request);
}
