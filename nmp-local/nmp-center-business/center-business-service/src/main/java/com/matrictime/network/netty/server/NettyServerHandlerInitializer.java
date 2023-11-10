package com.matrictime.network.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 11:23
 * @desc
 */
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final int MAX_FRAME_LENGTH = 5 * 1024 * 1024 *1024;
    private static final int LENGTH_FIELD_OFFSET = 4;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_ADJUSTMENT = -8;
    private static final int INITIAL_BYTES_TO_STRIP = 48;
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new NettyCustomDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_OFFSET,
                        LENGTH_FIELD_LENGTH,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false))
                //.addLast(new ByteArrayDecoder())
                .addLast(new NettyServerHandler())
                .addLast(new ServerIdleStateHandler());

    }
}
