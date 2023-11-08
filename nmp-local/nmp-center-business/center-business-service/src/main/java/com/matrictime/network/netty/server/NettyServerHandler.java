package com.matrictime.network.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.util.SpringContextUtils;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.service.DataHandlerService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 11:21
 * @desc
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Executor taskExecutor = new ThreadPoolExecutor(
            // 核心线程数
            3,
            // 最大线程数
            5,
            // 空闲线程最大存活时间
            60L,
            // 空闲线程最大存活时间单位
            TimeUnit.SECONDS,
            // 等待队列及大小
            new ArrayBlockingQueue<>(100),
            // 创建新线程时使用的工厂
            Executors.defaultThreadFactory(),
            // 当线程池达到最大时的处理策略
            new ThreadPoolExecutor.CallerRunsPolicy()       // 交由调用者的线程执行
    );


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String jsonStr = (String) msg;
        if (ObjectUtils.isEmpty(msg)) {
            log.info("收到客户端的业务消息为空：{}", msg);
            return;
        }
        try {
            DataPushBody pushBody = JSONObject.parseObject(jsonStr, DataPushBody.class);
            ;
            taskExecutor.execute(() -> {
                handlerMapping(pushBody);
            });
        } catch (Exception e) {
            log.error("NettyServerHandler channelRead0 error:{}", e);
        }
    }

    /**
     * @param [dataPushBody]
     * @return void
     * @title handlerMapping
     * @description 业务分发
     * @author jiruyi
     * @create 2023/8/29 0029 15:34
     */
    public void handlerMapping(DataPushBody dataPushBody) {
        log.info("DataPushBody businessCode is:{},tableName is :{}",
                dataPushBody.getBusinessCode(), dataPushBody.getTableName());
        Map<String, DataHandlerService> map =
                SpringContextUtils.getBeansOfType(DataHandlerService.class);
        map.get(dataPushBody.getBusinessCode()).handlerData(dataPushBody);
    }

}
