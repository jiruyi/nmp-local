package com.matrictime.network.netty.protocol.message;

import lombok.Data;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/23 0023 15:25
 * @desc
 */
@Data
public class ReqData {
    byte version = 1;
    byte resv = 34;
    short uMsgType = 999;
    int uTotalLen = 52;
    String dstRID = "1234567891234567";
    String srcRID = "1234567891234567";
    int uIndex = 0;
    byte uEncType = 1;
    byte uEncRate = 1;
    byte checkSum = 1;
    String reqData = "hello";
}
