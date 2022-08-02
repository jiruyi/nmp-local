package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;

/**
 * websocket的处理类。
 * 作用相当于HTTP请求
 * 中的controller
 */
@Component
@Slf4j
@ServerEndpoint("/webSocket/{userId}")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收用户id*/
    private String userId = "";

    public static ConcurrentHashMap<String,WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }

    /**
     * 连接建立成
     * 功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //加入set中
            webSocketMap.put(userId,this);
        }else{
            //加入set中
            webSocketMap.put(userId,this);
            //在线数加1
            addOnlineCount();
        }
        log.info("连接:"+userId+",当前在线用户数为:" + getOnlineCount());
        for (String key : webSocketMap.keySet()) {
            log.info("当前在线用户:"+key);
        }
    }

    /**
     * 连接关闭
     * 调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            if (!ParamCheckUtil.checkVoStrBlank(userId)){
                int i = userId.lastIndexOf(KEY_SPLIT_UNDERLINE);
                if (i>0){
                    String delUserId = userId.substring(0,i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId",delUserId);
                    jsonObject.put("destination",UcConstants.DESTINATION_IN);
                    try {
                        log.info("websocket系统退出地址:{},信息:{}","http://127.0.0.1:8007"+ UcConstants.URL_SYSLOGOUT,jsonObject.toJSONString());
                        HttpClientUtil.post("http://127.0.0.1:8007"+ UcConstants.URL_SYSLOGOUT, jsonObject.toJSONString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log.info("用户退出:"+userId+",当前在线用户数为:" + getOnlineCount());
        for (String key : webSocketMap.keySet()) {
            log.info("当前在线用户:"+key);
        }
    }

    /**
     * 收到客户端消
     * 息后调用的方法
     * @param message
     * 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(message);
                }else{
                    //否则不在这个服务器上，发送到mysql或者redis
                    log.error("请求的userId:"+toUserId+"不在该服务器上");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }


    /**
     * 实现服务
     * 器主动推送
     */
    public void sendMessage(String message) {
        try {
            log.info("websocket推送消息:"+message);
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverClose(){
        if (this.session.isOpen()){
            try {
                CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"鉴权失败！");
                session.close(closeReason);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }



    /**
     * 获得此时的
     * 用户数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线用户数加1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线用户数减1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
