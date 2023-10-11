package com.matrictime.network.netty.client;

import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.dao.model.NmplInternetRoute;
import com.matrictime.network.dao.model.NmplLink;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/22 0022 11:31
 * @desc
 */
@Component
@Slf4j
public class NettyClient {
    private EventLoopGroup group = new NioEventLoopGroup();
    private   SocketChannel socketChannel;

    @Autowired
    private DeviceDomainService deviceDomainService;



    /**
      * @title sendMsg
      * @param [msg]
      * @return void
      * @description 消息发送
      * @author jiruyi
      * @create 2023/8/24 0024 17:19
      */
    public ChannelFuture  sendMsg(Object msg) {
        return  socketChannel.writeAndFlush(msg);
    }

    /**
      * @title start
      * @param []
      * @return void
      * @description 创建netty客户端
      * @author jiruyi
      * @create 2023/8/24 0024 17:19
      */
    @PostConstruct
    public void start()  {
        Bootstrap bootstrap = new Bootstrap();
        //1.0 查询数据采集对应的指控中心入网码（业务服务）
        NmplBusinessRoute route = deviceDomainService.getBusinessRoute();
        log.info("查询本机数据采集系统配置的指控中心：{}",route);
        if(ObjectUtils.isEmpty(route)){
            log.info("查询本机数据采集系统配置的指控中心为空,作返回处理");
            return;
        }
        //2.0 用指控中心入网码作为目标查询路由
        NmplInternetRoute internetRoute = deviceDomainService.getInternetRoute(route.getNetworkId());
        log.info("查询本机数据采集系统路由配置为空：{}",internetRoute);
        if(ObjectUtils.isEmpty(internetRoute)){
            log.info("查询本机数据采集系统路由配置为空,作返回处理");
            return;
        }
        //查询本机数据采集系统链路信息对应的边界基站
        NmplLink link = deviceDomainService.getDataCollectLink(internetRoute.getNextNetworkId());
        log.info("查询本机数据采集系统链路信息是：{}",link);
        if(ObjectUtils.isEmpty(link)){
            log.info("查询本机数据采集系统链路信息为空,作返回处理");
            return;
        }
        //查询数据采集到边界(指控中心)的链路
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(link.getFollowIp(), Integer.valueOf(link.getFollowPort()))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientHandlerInitilizer(this));
        ChannelFuture future = bootstrap.connect();
        //客户端断线重连逻辑
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                log.info("连接Netty服务端成功");
            } else {
                log.info("连接失败，进行断线重连");
                future1.channel().eventLoop().schedule(() -> start(), 20, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();
    }
}
