package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NetCardInfoVo;
import com.matrictime.network.service.NetCardService;
import com.matrictime.network.util.NetCardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.List;

@Service
@Slf4j
public class NetCardServiceImpl extends SystemBaseService implements NetCardService {

    @Override
    public Result getNetCardInfo() throws SocketException {
        List<NetCardInfoVo> netCardInfo = NetCardUtils.getNetCardInfo();
        return buildResult(netCardInfo);
    }
}
