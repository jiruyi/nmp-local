package com.matrictime.network.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.util.SpringContextUtils;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.service.DataHandlerService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 11:21
 * @desc
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<byte[]> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        try {
            if(ObjectUtils.isEmpty(msg)){
                log.info("收到客户端的业务消息为空：{}",msg);
            }
//            DataPushBody pushBody = parseMsg(msg);
//            Executor executor = (Executor) SpringContextUtils.getBean("taskExecutor");
//            executor.execute(() ->{
//                handlerMapping(pushBody);
//            });
        }catch (Exception e){
            log.error("NettyServerHandler channelRead0 error:{}",e);
        }
    }

    /**
     * @title parseMsg
     * @param [msg]
     * @return com.matrictime.network.modelVo.DataPushBody
     * @description  解析数据
     * @author jiruyi
     * @create 2023/8/29 0029 14:06
     */
    public DataPushBody parseMsg(byte[] msg){
        ByteBuffer byteBuffer = ByteBuffer.allocate(msg.length);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(msg);
        String reqDataJsonStr = new String(byteBuffer.array());
        log.info("收到客户端的业务消息：{}",reqDataJsonStr);
        return JSONObject.parseObject(reqDataJsonStr, DataPushBody.class);
    }

    /**
     * @title handlerMapping
     * @param [dataPushBody]
     * @return void
     * @description  业务分发
     * @author jiruyi
     * @create 2023/8/29 0029 15:34
     */
    public void handlerMapping(DataPushBody dataPushBody){
        log.info("DataPushBody businessCode is:{},tableName is :{}"
                ,dataPushBody.getBusinessCode(),dataPushBody.getTableName());
        Map<String,DataHandlerService> map =
                SpringContextUtils.getBeansOfType(DataHandlerService.class);
        map.get(dataPushBody.getBusinessCode()).handlerData(dataPushBody);
    }

}
