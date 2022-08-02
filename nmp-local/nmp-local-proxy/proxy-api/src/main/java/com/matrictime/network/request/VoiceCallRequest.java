package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoiceCallRequest {
    //接收语音通知的手机号码
    private String calledNumber;
    //已通过审核的语音验证码模板ID
    private String ttsCode;
    //语速控制。取值范围为：-500~500。
    private Integer speed;
    //模板中的变量参数，JSON格式。
    private String ttsParam;
    //语音通知的播放音量。取值范围：0~100，默认取值100。
    private Integer volume;
    private List<String> calledNumberList;
}
