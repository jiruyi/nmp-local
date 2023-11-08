package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonServiceImpl extends SystemBaseService implements CommonService {

    @Override
    public Result init() {
        return null;
    }
}
