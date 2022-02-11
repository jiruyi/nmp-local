package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.VoiceCallRequest;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/11/22 0022 14:59
 * @desc
 */
public interface VoiceService {
    Result voiceCall(VoiceCallRequest voiceCallRequest);


    Result voiceCallBactch(VoiceCallRequest voiceCallRequest);
}
