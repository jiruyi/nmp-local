package com.matrictime.network.netty.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.modelVo.AlarmInfo;
import com.matrictime.network.modelVo.DataPushBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.List;

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

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }
//        if (in.readableBytes() < HEADER_SIZE) {
//            throw new Exception("可读信息段比头部信息都小，你在逗我？");
//        }
        //注意在读的过程中，readIndex的指针也在移动
        //版本
        byte version = in.readByte();
        //预留
        byte resv = in.readByte();
        //消息类型
        short uMsgType = in.readShort();
        //数据总长度
        int uTotalLen = in.readInt();
        //目标入网码
        ByteBuf dstRID = in.readBytes(16);
        //源入网码
        ByteBuf srcRID = in.readBytes(16);
        //消息编号
        int uIndex = in.readInt();
        //加密算法
        byte uEncType = in.readByte();
        //加密比例
        byte uEncRate = in.readByte();
        //checksum
        short checkSum = in.readShort();
//        if (in.readableBytes() < uTotalLen-HEADER_SIZE) {
//            throw new Exception("body字段你告诉我长度是"+(uTotalLen-HEADER_SIZE)+",但是真实情况是没有这么多，你又逗我？");
//        }
        ByteBuf buf = in.readBytes(uTotalLen-HEADER_SIZE);
        byte[] req = new byte[buf.readableBytes()];
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.readBytes(req);
        String reqData = new String(req, "UTF-8");
        DataPushBody dataPushBody = JSONObject.parseObject(reqData, DataPushBody.class);
        List<AlarmInfo> alarmInfos =  JSONArray.parseArray(dataPushBody.getBusiDataJsonStr(), AlarmInfo.class);
        log.info("alarmInfos is:{}",alarmInfos);
        return null;
    }
}
