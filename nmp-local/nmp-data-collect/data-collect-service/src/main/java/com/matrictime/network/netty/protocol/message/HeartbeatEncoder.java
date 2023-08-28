package com.matrictime.network.netty.protocol.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/22 0022 14:22
 * @desc
 */
public class HeartbeatEncoder extends MessageToByteEncoder<HeartbeatRequestPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HeartbeatRequestPacket msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getVersion());
        out.writeByte(msg.getCommand());
    }
}
