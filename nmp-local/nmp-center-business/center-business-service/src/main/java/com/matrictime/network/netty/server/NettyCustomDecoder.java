package com.matrictime.network.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/30 0030 13:54
 * @desc
 */
@Slf4j
public class NettyCustomDecoder extends LengthFieldBasedFrameDecoder {

    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 48
    private static final int HEADER_SIZE = 48;
    private static final int REQ_DATA_INDEX = 47;

    //版本
    byte version = 1;
    //预留
    byte resv = 1;
    //消息类型
    short uMsgType = 501;
    //数据总长度
    int uTotalLen = 0;
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


    /**
     *
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    public NettyCustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                              int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
    }

    /**
     * @title decode
     * @param [ctx, in]
     * @return java.lang.Object
     * @description
     * @author jiruyi
     * @create 2023/8/31 0031 19:03
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.readableBytes() < HEADER_SIZE) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于Encoder中我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            log.info("in.readableByte   < HEADER_SIZE(48byte) ");
            return null;
        }
        log.info("NettyCustomDecoder  decode start ");
        // 数据包长度
        in.markReaderIndex();
        int uTotalLen = in.getIntLE(4);
        if(in.readableBytes() < uTotalLen){
            log.info("in.readableBytes() < uTotalLen：{} 继续读取",uTotalLen);
            in.resetReaderIndex();
            return null;
        }
        log.info("收到客户端的业务消息长度：{}",uTotalLen);
        log.info("收到客户端的组包后消息长度：{}",in.readableBytes());
        String messageHeader =  in.toString(0,HEADER_SIZE, CharsetUtil.UTF_8);
        log.info("收到客户端的业务消息messageHeader:{}",messageHeader);
        String reqDataJsonStr = in.toString(HEADER_SIZE,uTotalLen-HEADER_SIZE, CharsetUtil.UTF_8);
        in.readBytes(uTotalLen);
        return  reqDataJsonStr;
    }
}
