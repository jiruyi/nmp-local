package com.matrictime.network.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;

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
     * @title decode
     * @param [ctx, in]
     * @return java.lang.Object
     * @description
     * @author jiruyi
     * @create 2023/8/31 0031 19:03
     */
//    @Override
//    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        if (in.readableBytes() < HEADER_SIZE) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于Encoder中我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
//            log.info("in.readableByte   < HEADER_SIZE(48byte) ");
//            return null;
//        }
//        log.info("NettyCustomDecoder decode start  client ip is:{}", ctx.channel().remoteAddress().toString());
//        // 数据包长度
//        int uTotalLen = in.getIntLE(4);
//        if(in.readableBytes() < uTotalLen){
//            log.info("in.readableBytes() < uTotalLen：{} 继续读取",uTotalLen);
//            return null;
//        }
//        log.info("收到客户端的业务消息长度：{}",uTotalLen);
//        log.info("收到客户端的组包后消息长度：{}",in.readableBytes());
//        String reqDataJsonStr = in.toString(HEADER_SIZE,uTotalLen-HEADER_SIZE, CharsetUtil.UTF_8);
//        in.readBytes(uTotalLen);
//        return  reqDataJsonStr;
//    }
    private  ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    private  int maxFrameLength;
    private  int lengthFieldOffset;
    private  int lengthFieldLength;
    private  int lengthFieldEndOffset;
    private  int lengthAdjustment;
    private  int initialBytesToStrip;
    private  boolean failFast;

    public NettyCustomDecoder(int maxFrameLength, int lengthFieldOffset,
                              int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super( ByteOrder.LITTLE_ENDIAN,  maxFrameLength,  lengthFieldOffset,
         lengthFieldLength,lengthAdjustment,  initialBytesToStrip,failFast);
        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
        this.initialBytesToStrip = initialBytesToStrip;
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        log.info("NettyCustomDecoder decode start  client ip is:{}", ctx.channel().remoteAddress().toString());
        ByteBuf decoded =  (ByteBuf)super.decode(ctx,in);
        log.info("NettyCustomDecoder decode end  client ip is:{}", ctx.channel().remoteAddress().toString());
        if (decoded != null) {
            String reqDataJsonStr = decoded.toString(CharsetUtil.UTF_8);
            log.info("ByteBuf decoded(reqDataJsonStr) is:{}",reqDataJsonStr);
            return  reqDataJsonStr;
        }
        return null;
    }
}
