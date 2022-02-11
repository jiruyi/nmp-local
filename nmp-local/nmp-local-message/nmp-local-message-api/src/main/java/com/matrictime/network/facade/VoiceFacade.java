package com.matrictime.network.facade;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.VoiceCallRequest;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/10 0010 16:20
 * @desc
 */
public interface VoiceFacade {

    Result voiceCall(VoiceCallRequest voiceCallRequest);


    Result voiceCallBactch(VoiceCallRequest voiceCallRequest);
}
