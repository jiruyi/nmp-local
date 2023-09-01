package com.matrictime.network.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/22 0022 14:18
 * @desc
 */
public class ClientHandlerInitilizer extends ChannelInitializer<Channel> {

    private  NettyClient nettyClient;

    public  ClientHandlerInitilizer( NettyClient nettyClient){
        this.nettyClient = nettyClient;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(0, 18000, 0))
                .addLast(new ByteArrayDecoder())
                .addLast(new ByteArrayEncoder())
                .addLast(new HeartbeatHandler(nettyClient))
                .addLast(new NettyClientHandler());
    }
}
