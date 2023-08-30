package com.matrictime.network.netty.server;

import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 11:23
 * @desc
 */
@Slf4j
public class ServerIdleStateHandler extends IdleStateHandler {
    /**
     * 设置空闲检测时间为 0s
     */
    private static final int READER_IDLE_TIME = 0;
    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

//    @Override
//    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
//        log.info("{} 秒内没有读取到数据,关闭连接", READER_IDLE_TIME);
//        ctx.channel().close();
//    }
}
