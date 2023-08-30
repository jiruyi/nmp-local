package com.matrictime.network.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 11:23
 * @desc
 */
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                //空闲检测
                //.addLast(new ServerIdleStateHandler())
                .addLast(new ByteArrayDecoder())
                .addLast(new ByteArrayEncoder())
                .addLast(new NettyServerHandler());
    }
}
