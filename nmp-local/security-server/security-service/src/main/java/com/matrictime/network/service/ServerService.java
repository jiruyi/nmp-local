package com.matrictime.network.service;

import com.matrictime.network.model.Result;

public interface ServerService {

    Result getStatus();

    Result start();

    Result getStartStatus();
}
