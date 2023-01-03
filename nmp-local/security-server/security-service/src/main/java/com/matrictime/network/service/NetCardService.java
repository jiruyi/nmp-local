package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NetCardInfoVo;

import java.net.SocketException;
import java.util.List;

public interface NetCardService {

    Result getNetCardInfo() throws SocketException;

}
