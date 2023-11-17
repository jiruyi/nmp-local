package com.matrictime.network.service.impl;

import com.matrictime.network.util.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommonServiceimpl{

    @Async
    public void startServerShell(List<String> param) {
        ShellUtil.runShell(param,null);
    }
}
