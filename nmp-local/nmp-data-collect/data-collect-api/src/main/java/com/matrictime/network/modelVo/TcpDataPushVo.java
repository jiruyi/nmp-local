package com.matrictime.network.modelVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/25 0025 9:52
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcpDataPushVo {

    //版本
    byte version = 1;
    //预留
    byte resv = 1;
    //消息类型
    short uMsgType = 501;
    //数据总长度
    int uTotalLen = 48;
    //目标入网码
    byte[] dstRID = new byte[16];
    //源入网码
    byte[] srcRID = new byte[16];
    //消息编号
    int uIndex = 0;
    //加密算法
    byte uEncType = 0;
    //加密比例
    byte uEncRate = 0;
    //checksum
    short checkSum = 0;
    //业务数据
    byte[] reqData ;
}
